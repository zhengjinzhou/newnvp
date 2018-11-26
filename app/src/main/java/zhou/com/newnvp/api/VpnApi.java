package zhou.com.newnvp.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import zhou.com.newnvp.base.Constant;
import zhou.com.newnvp.bean.LoginBean;

/**
 * Created by zhou
 * on 2018/11/21.
 */

public class VpnApi {

    public static VpnApi bookApi;
    private VpnApiApiService service;

    public VpnApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
        service = retrofit.create(VpnApiApiService.class);
    }

    public Observable<LoginBean> vpnLogin(String action, String cmd){
        return service.vpnLogin(action,cmd);
    }


}