package zhou.com.newnvp.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;


import net.arraynetworks.vpn.Common;
import net.arraynetworks.vpn.VPNManager;

import zhou.com.newnvp.activity.LoginActivity;
import zhou.com.newnvp.activity.WebActivity;
import zhou.com.newnvp.base.Constant;
import zhou.com.newnvp.bean.VpnSelectBean;
import zhou.com.newnvp.utils.ProxySettings;
import zhou.com.newnvp.utils.SpUtil;
import zhou.com.newnvp.utils.ToastUtil;


/**
 * Created by zhou
 * on 2018/8/1.
 */

public class MyServpce extends Service {

    private PowerManager.WakeLock wakeLock = null;

    String host = "mobile.dg.cn";
    int port = 443;
    String username = "";
    String password = "";
    private static VPNManager.AAAMethod[] mMethods = null;

    public IBinder onBind(Intent intent) {
        Log.d("2", "onCreate: ");
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("", "onCreate: ");

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, MyServpce.class.getName());
        wakeLock.acquire();

        VpnSelectBean vpnSelectBean = (VpnSelectBean) SpUtil.getObject(this, Constant.vpnAccount, VpnSelectBean.class);
        username = vpnSelectBean.getVpnUser();
        password = vpnSelectBean.getVpnPsd();

        VPNManager.initialize(this);

        VPNManager.getInstance().setHandler(mHandler);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                VPNManager.getInstance().startVPN(host, port, username,
                        password);
            }
        });
        t.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private String Tag = "Service-----";
    /**
     * Handle the messages of VPN life cycle.
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(Tag, "handleMessage " + msg.what);
            switch (msg.what) {
                case Common.VpnMsg.MSG_VPN_CONNECTING:
                    Log.i(Tag, "vpn connecting  正在连接服务器");
                    //mStatus.setText("VPN is connecting...");
                    //mButtonLaunch.setText("Disconnect");
                    break;
                case Common.VpnMsg.MSG_VPN_CONNECTED:
                    Log.i(Tag, "vpn connected 连接成功，隧道已建立");
                    //mStatus.setText("VPN connected.");
                    //mButtonLaunch.setText("Disconnect");

                    ProxySettings.setProxy(getApplicationContext(), ProxySettings.DefaultHost, VPNManager.getInstance().getHttpProxyPort());
                    Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//android6.0需要这样写，像下面那样写会奔溃，不兼容
                    startActivity(intent);
                   // startActivity(new Intent(getApplicationContext(), WebActivity.class));

                    break;
                case Common.VpnMsg.MSG_VPN_DISCONNECTING:
                    Log.i(Tag, "vpn disconnecting 正在登出");
                   // mStatus.setText("VPN is disconnecting...");

                    break;
                case Common.VpnMsg.MSG_VPN_DISCONNECTED:
                    Log.i(Tag, "vpn disconnected  连接断开，Session结束");
                   // mStatus.setText("VPN is not running.");
                   // mButtonLaunch.setText("Connect");

                    break;
                case Common.VpnMsg.MSG_VPN_CONNECT_FAILED:
                    Log.i(Tag, "vpn connect failed  登录失败");
                    ToastUtil.show(getApplicationContext(),"VPN有误，请重新输入");

                    Log.i(Tag, "需要用户输入认证凭证");
                    //SpUtil.clear();
                    //SpUtil.remove(getApplicationContext(),Constant.Account);
                    SpUtil.remove(getApplicationContext(),Constant.VPN_AUTO);

                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                    break;
                case Common.VpnMsg.MSG_VPN_RECONNECTING:
                    Log.i(Tag, "vpn reconnecting 连接断开，正在进行重连");
                  //  mStatus.setText("VPN is reconnecting...");
                    break;

                case Common.VpnMsg.MSG_VPN_LOGIN:
                    int error = msg.getData().getInt(Common.VpnMsg.MSG_VPN_ERROR_CODE);
                    mMethods = (VPNManager.AAAMethod[]) (msg.obj);
                    if (0 == mMethods.length) {
                        VPNManager.getInstance().cancelLogin();
                    }
                    //handleLogin(error);
                    ToastUtil.show(getApplicationContext(),"VPN有误，请重新输入");
                    Log.i(Tag, "需要用户输入认证凭证");

                    //SpUtil.clear();
                    //SpUtil.remove(getApplicationContext(),Constant.Account);
                    SpUtil.remove(getApplicationContext(),Constant.VPN_AUTO);

                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    break;

                case Common.VpnMsg.MSG_VPN_DEVREG:
                    mMethods = (VPNManager.AAAMethod[]) (msg.obj);
                    //handleDevReg();
                    Log.i(Tag, "需要用户输入认证凭证及设备名称。");
                    ToastUtil.show(getApplicationContext(),"VPN有误，请重新输入");

                    //SpUtil.clear();
                    //SpUtil.remove(getApplicationContext(),Constant.Account);
                    SpUtil.remove(getApplicationContext(),Constant.VPN_AUTO);
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    public void onDestroy() {
        if (wakeLock != null) {
            wakeLock.release();
            wakeLock = null;
        }
        super.onDestroy();
    }
}
