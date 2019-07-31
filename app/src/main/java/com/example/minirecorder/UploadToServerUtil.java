package com.example.minirecorder;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
    public static String TAG = "UploadToServerUtil";
    OkHttpClient client = new OkHttpClient();

    public void uploadFile(String url, File file, String fileName, final Context context) {
        // 创建一个RequestBody，文件的类型是image/png
        RequestBody requestBody = RequestBody.create(MediaType.parse("audio/mp3"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                // 设置type为"multipart/form-data"，不然无法上传参数
                .setType(MultipartBody.FORM)
                .addFormDataPart("photo", fileName + ".mp3", requestBody)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(multipartBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.toString());
                FragmentUtils.backgroundThreadShortToast(context.getApplicationContext(), "Cannot upload to server!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                Log.d(TAG, "onResponse: " + responseString);
                FragmentUtils.backgroundThreadShortToast(context.getApplicationContext(), responseString);
            }
        });
    }

}