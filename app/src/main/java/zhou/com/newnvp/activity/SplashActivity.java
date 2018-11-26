package zhou.com.newnvp.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import okhttp3.OkHttpClient;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zhou.com.newnvp.R;
import zhou.com.newnvp.api.VpnApi;
import zhou.com.newnvp.base.Constant;
import zhou.com.newnvp.bean.LoginBean;
import zhou.com.newnvp.bean.SelectBean;
import zhou.com.newnvp.bean.VpnSelectBean;
import zhou.com.newnvp.service.MyServpce;
import zhou.com.newnvp.utils.AppManager;
import zhou.com.newnvp.utils.SpUtil;
import zhou.com.newnvp.utils.ToastUtil;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppManager.getAppManager().addActivity(SplashActivity.this);

        final boolean aBoolean = SpUtil.getBoolean(getApplicationContext(), Constant.VPN_AUTO, false);//是否自动登录
        VpnSelectBean vpnSelectBean = (VpnSelectBean) SpUtil.getObject(getApplicationContext(), Constant.vpnAccount, VpnSelectBean.class);//vpn 的账号密码
        SelectBean selectBean = (SelectBean) SpUtil.getObject(getApplicationContext(), Constant.Account, SelectBean.class);//账号密码

        Log.d(TAG, "onCreate: 123");
        if (aBoolean) {
            Log.d(TAG, "onCreate: 1234");
            //vpn自动登录
            if (vpnSelectBean != null && selectBean != null) {
                //点击过vpn登录
                if (vpnSelectBean.isSelect()) {
                    startService(new Intent(getApplicationContext(), MyServpce.class));
                } else {
                    //没有点击过vpn登录
                    login(selectBean.getUser(), selectBean.getPsd());
                }
            } else {
                if (selectBean != null){
                    //没有vpn 的自动登录
                    login(selectBean.getUser(), selectBean.getPsd());
                }else {
                    Log.d(TAG, "onCreate: 123456");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                    }, 2000);
                }

            }
        } else {
            Log.d(TAG, "onCreate: 123456");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }, 2000);
        }
    }

    /**
     * 没有使用vpn的自动登录
     *
     * @param user
     * @param psd
     */
    private void login(final String user, final String psd) {
        new VpnApi(new OkHttpClient()).vpnLogin("Login", "{UserID:'" + user + "',UserPsw:'" + psd + "'}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(getApplicationContext(),"请检查网络");
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.isResult()){
                            Intent intent = new Intent(getApplicationContext(),WebActivity.class);
                            intent.putExtra(Constant.ACCOUNT_USER,user);
                            intent.putExtra(Constant.ACCOUNT_PSD,psd);
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtil.show(getApplicationContext(),loginBean.getMessage());
                        }
                    }
                });
    }
}
