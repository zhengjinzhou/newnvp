package zhou.com.newnvp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhou.com.newnvp.R;
import zhou.com.newnvp.base.BaseActivity;
import zhou.com.newnvp.base.Constant;
import zhou.com.newnvp.bean.LoginBean;
import zhou.com.newnvp.bean.SelectBean;
import zhou.com.newnvp.bean.VpnSelectBean;
import zhou.com.newnvp.contract.loginContract;
import zhou.com.newnvp.presenter.LoginPresenter;
import zhou.com.newnvp.service.MyServpce;
import zhou.com.newnvp.utils.AppManager;
import zhou.com.newnvp.utils.SpUtil;
import zhou.com.newnvp.utils.ToastUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener,loginContract.View {

    public final static int REQUEST_READ_PHONE_STATE = 1;
    public final static int REQUEST_WRITE_EXTERNAL_STATE = 2;
    private static final String TAG = "LoginActivity";
    private EditText user;
    private EditText etPsd;@BindView(R.id.tvTip)
    TextView tvTip;
    private LoginPresenter presenter = new LoginPresenter(this);

    private boolean REM = false;
    private boolean AUTO = false;
    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
//权限申请
        initPermission();

        initView();

        presenter.attachView(this);
    }

    /**
     * 获取控件id
     */
    private void initView() {

        AppManager.getAppManager().addActivity(LoginActivity.this);

        findViewById(R.id.llVpn).setOnClickListener(this);
        findViewById(R.id.btLogin).setOnClickListener(this);
        findViewById(R.id.ivShow).setOnClickListener(this);
        findViewById(R.id.ivClear).setOnClickListener(this);

        final CheckBox isRem = findViewById(R.id.isRem);
        final CheckBox isAuto = findViewById(R.id.isAuto);

        user = findViewById(R.id.editText3);
        etPsd = findViewById(R.id.etPsd);

        /**
         * 将保存的密码显示
         */
        SelectBean selectBean = (SelectBean) SpUtil.getObject(getApplicationContext(), Constant.Account, SelectBean.class);

        if (selectBean != null) {
            if (selectBean.isRem()) {
                user.setText(selectBean.getUser());
                etPsd.setText(selectBean.getPsd());
                isRem.setChecked(selectBean.isRem());
            }
        }

        //记住密码
        isRem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                REM = isChecked;
            }
        });

        //自动登录
        isAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtil.putBoolean(getApplicationContext(),Constant.VPN_AUTO,isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivClear:
                user.setText("");
                break;
            case R.id.ivShow:
                if (etPsd.getInputType() == 129) {
                    etPsd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    etPsd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                etPsd.setSelection(etPsd.getText().toString().length());
                break;
            case R.id.llVpn:
                startActivity(new Intent(getApplicationContext(), VpnLoginActivity.class));
                break;
            case R.id.btLogin:
                if (TextUtils.isEmpty(user.getText().toString())) {
                    ToastUtil.show(getApplicationContext(), "账号不能为空");
                    //tvTip.setText("账号不能为空");
                    return;
                }

                if (TextUtils.isEmpty(etPsd.getText().toString())) {
                    ToastUtil.show(getApplicationContext(), "密码不能为空");
                    //tvTip.setText("密码不能为空");
                    return;
                }
                //tvTip.setText("");
                VpnSelectBean vpnSelectBean = (VpnSelectBean) SpUtil.getObject(getApplicationContext(), Constant.vpnAccount, VpnSelectBean.class);

                if (vpnSelectBean == null){
                    ToastUtil.show(getApplicationContext(), "注意！！VPN账号为空。");
                    //tvTip.setText("注意！！VPN账号为空。");
                }

                dialog.show();
                //记住账号密码的状态
                SelectBean selectBean = new SelectBean(user.getText().toString(), etPsd.getText().toString(), REM, AUTO);
                Log.d(TAG, "onClick:----------------------- "+selectBean.toString());
                SpUtil.putObject(this, Constant.Account, selectBean);

                if (vpnSelectBean != null) {
                    //vpn被记住的时候

                    if (vpnSelectBean.isSelect()) {
                        //使用vpn登录的时候
                        Intent intent = new Intent(getApplicationContext(), MyServpce.class);
                        intent.putExtra(Constant.ACCOUNT_USER, user.getText().toString());
                        intent.putExtra(Constant.ACCOUNT_PSD, etPsd.getText().toString());
                        startService(intent);

                    } else {
                        Log.d(TAG, "onClick: 不使用vpn登录的时候");
                        //不使用vpn登录的时候
                        presenter.login();
                    }
                } else {
                    Log.d(TAG, "onClick: vpn不被记住的时候");
                    //vpn不被记住的时候
                    presenter.login();
                }
                break;
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        dialog.dismiss();
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        if (loginBean.isResult()){
            Intent intent = new Intent(getApplicationContext(),WebActivity.class);
            intent.putExtra(Constant.ACCOUNT_USER,user.getText().toString());
            intent.putExtra(Constant.ACCOUNT_PSD,etPsd.getText().toString());
            startActivity(intent);
            finish();
        }else {
            //tvTip.setText(loginBean.getMessage());
            ToastUtil.show(getApplicationContext(),loginBean.getMessage());
        }
        dialog.dismiss();
    }

    @Override
    public void vpnLoginSuccess(LoginBean loginBean) {

    }

    @Override
    public String setUser() {
        return user.getText().toString();
    }

    @Override
    public String setPsd() {
        return etPsd.getText().toString();
    }















    /**
     * 动态权限申请
     */
    private void initPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){  //申请的集合不为空时，表示有需要申请的权限
            ActivityCompat.requestPermissions(this,permissionList.toArray(new String[permissionList.size()]),1);
        }else { //所有的权限都已经授权过了

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if (grantResults.length > 0){ //安全写法，如果小于0，肯定会出错了
                    for (int i = 0; i < grantResults.length; i++) {

                        int grantResult = grantResults[i];
                        if (grantResult == PackageManager.PERMISSION_DENIED){ //这个是权限拒绝
                            String s = permissions[i];
                            ToastUtil.show(this,s+"权限被拒绝了,请自行到应用管理权限开启");
                        }else{ //授权成功了
                            //do Something
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
