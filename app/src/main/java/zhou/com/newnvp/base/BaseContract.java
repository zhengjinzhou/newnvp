package zhou.com.newnvp.base;

/**
 * Created by zhou
 * on 2018/8/1.
 */

public interface BaseContract {
    interface BasePresenter<T>{
        void attachView(T view);

        void detachView();
    }

    interface BaseView{
        void showError();

        void complete();
    }
}
