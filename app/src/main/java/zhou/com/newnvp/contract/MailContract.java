package zhou.com.newnvp.contract;

import java.util.List;

import zhou.com.newnvp.base.BaseContract;
import zhou.com.newnvp.bean.MailBean;


/**
 * Created by zhou
 * on 2018/8/2.
 */

public interface MailContract {

    interface View extends BaseContract.BaseView {

        void onSuccess(List<MailBean> list);

        void onFailure(String errorMsg);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        public abstract void getMailList(String param);
    }

}
