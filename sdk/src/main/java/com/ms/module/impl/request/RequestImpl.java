package com.ms.module.impl.request;

import com.ms.module.impl.utils.okhttp.OkHttpUtils;
import com.ms.module.supers.inter.request.IRequestAdapter;
import com.ms.module.supers.inter.request.RequestCallBack;
import com.ms.module.supers.inter.request.Response;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestImpl extends IRequestAdapter {

    @Override
    public Response get(Map<String, String> headers, String url) {
        Request.Builder request = new Request.Builder();
        if (headers != null) {
            for (String k : headers.keySet()) {
                request.addHeader(k, headers.get(k));
            }
        }
        //创建Request
        request.url(url);
        Call call = OkHttpUtils.getInstance().newCall(request.build());
        try {
            okhttp3.Response execute = call.execute();
            String string = execute.body().string();
            return new Response(execute.code(), string, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(-1, null, e.getMessage());
        }
    }

    @Override
    public void get(Map<String, String> headers, String url, final RequestCallBack callBack) {
        OkHttpUtils.doGet(headers, url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (callBack != null) {
                    callBack.onSuccess(new Response(response.code(), response.body().string(), null));
                }
            }
        });
    }

    @Override
    public Response post(Map<String, String> headers, String url, Map<String, String> params) {
        Request.Builder request = new Request.Builder();
        if (headers != null) {
            for (String k : headers.keySet()) {
                request.addHeader(k, headers.get(k));
            }
        }
        //创建Request
        request.url(url);
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        request.post(builder.build());
        Call call = OkHttpUtils.getInstance().newCall(request.build());
        try {
            okhttp3.Response execute = call.execute();
            String string = execute.body().string();
            return new Response(execute.code(), string, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(-1, null, e.getMessage());
        }
    }

    @Override
    public void post(Map<String, String> headers, String url, Map<String, String> params, final RequestCallBack callBack) {
        OkHttpUtils.doPost(headers, url, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (callBack != null) {
                    callBack.onSuccess(new Response(response.code(), response.body().string(), null));
                }
            }
        });
    }

    @Override
    public void requestBody(Map<String, String> headers, String url, String body, final RequestCallBack callBack) {
        OkHttpUtils.doRequestBody(headers, url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {

                    callBack.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (callBack != null) {
                    callBack.onSuccess(new Response(response.code(), response.body().string(), null));
                }
            }
        });
    }

    @Override
    public Response requestBody(Map<String, String> headers, String url, String body) {
        okhttp3.Request.Builder request = new okhttp3.Request.Builder();
        if (headers != null) {
            Iterator var5 = headers.keySet().iterator();

            while (var5.hasNext()) {
                String k = (String) var5.next();
                request.addHeader(k, (String) headers.get(k));
            }
        }
        request.url(url);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
        request.post(requestBody);
        try {
            okhttp3.Response execute = OkHttpUtils.getInstance().newCall(request.build()).execute();

            return new Response(execute.code(), execute.body().string(), null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
