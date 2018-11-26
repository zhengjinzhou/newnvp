package zhou.com.newnvp.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;
import zhou.com.newnvp.bean.LoginBean;

/**
 * Created by zhou
 * on 2018/8/1.
 */

public interface VpnApiApiService {
    @GET("/DMS_Phone/Login/LoginHandler.ashx")
    Observable<LoginBean> vpnLogin(@Query("Action") String action, @Query("cmd") String cmd);

    @GET
    Call<String> requestPost(@Url() String url);

    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
