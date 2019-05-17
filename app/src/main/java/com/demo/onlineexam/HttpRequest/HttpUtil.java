package com.demo.onlineexam.HttpRequest;

import okhttp3.OkHttpClient;
import okhttp3.Request;

//网络操作实现类
public class HttpUtil {
    //url进行访问，callback对于操作进行解析
    public static void sendExamRequest(String url, okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        //无body，get方法
        Request request=new Request.Builder()
                .url(url)
                .build();
        //获取响应信息，回调操作
        client.newCall(request).enqueue(callback);
    }
}
