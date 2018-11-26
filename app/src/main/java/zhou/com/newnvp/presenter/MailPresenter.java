package zhou.com.newnvp.presenter;

import okhttp3.OkHttpClient;
import zhou.com.newnvp.activity.MailActivity;
import zhou.com.newnvp.api.VpnApi;
import zhou.com.newnvp.base.RxPresenter;
import zhou.com.newnvp.contract.MailContract;

/**
 * Created by zhou
 * on 2018/8/2.
 */

public class MailPresenter extends RxPresenter<MailContract.View> implements MailContract.Presenter<MailContract.View>{

    VpnApi vpnApi;
    MailActivity mailActivity;

    public MailPresenter(MailActivity mailActivity){
        this.mailActivity = mailActivity;
        this.vpnApi = new VpnApi(new OkHttpClient());
    }

    @Override
    public void getMailList(String param) {

    }
}
