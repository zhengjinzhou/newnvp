package zhou.com.newnvp.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

import zhou.com.newnvp.base.Constant;


/**
 * Created by zhou
 * on 2018/10/19.
 */

public class WebSerUpload {
    private static String methodName = "UploadFile";

    /**
     * 解锁文件
     * @param GUID
     */
    @SuppressLint("HandlerLeak")
    public static void UnlockFile(String GUID){
        HashMap<String, String> data = new HashMap<>();
        data.put("GUID", GUID);

        WebServiceUtil.callWebService(Constant.UPLOAD_URL, "UnlockFile", data, new WebServiceUtil.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                Log.d("解锁文件成功？", "callBack: " + result.toString());
            }
        });
    }

    @SuppressLint("HandlerLeak")
    public static void uploadWebService(int offset,int total, final String uploadBuffer,String guid) {

        HashMap<String, String> data = new HashMap<>();
        data.put("input", uploadBuffer);
        data.put("offset", offset + "");
        data.put("total", total+"");
        data.put("GUID", guid);
        WebServiceUtil.callWebService(Constant.UPLOAD_URL, methodName, data, new WebServiceUtil.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                Log.d("小文件上传成功吗？", "callBack: " + result.toString());
            }
        });
    }
}
