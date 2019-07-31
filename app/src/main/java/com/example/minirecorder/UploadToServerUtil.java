package com.example.minirecorder;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadToServerUtil {
    OkHttpClient client = new OkHttpClient();
    private void uploadFile(String url, File file) {
        // 创建一个RequestBody，文件的类型是image/png
        RequestBody requestBody = RequestBody.create(MediaType.parse("audio/mp3"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                // 设置type为"multipart/form-data"，不然无法上传参数
                .setType(MultipartBody.FORM)
                .addFormDataPart("photo", "xxx.mp3", requestBody)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(multipartBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("上传返回：\n" + response.body().string());
            }
        });
    }

}