package com.demo.data;

import android.os.Handler;
import android.os.Looper;

import com.demo.model.CommentModel;
import com.demo.model.NoteModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuhuiliang on 16/10/14.
 */

public class RedBookData {

    private static final String LIST_URL = "http://www.xiaohongshu.com/api/sns/v2/note/5787b2f5d1d3b95bcc277086/related?page=1&tag_oid=&platform=Android&deviceId=604fae2e-8a44-3b43-8297-ec5b00f7bf66&versionName=4.10.100&channel=Xiaomi&sid=session.1160904632837462555&lang=zh-CN&t=1477039748&sign=9f71b5df21235ed4def87098cff60d9c";
    private static final String COMMENT_URL = "http://www.xiaohongshu.com/api/sns/v5/note/5787b2f5d1d3b95bcc277086?platform=Android&deviceId=604fae2e-8a44-3b43-8297-ec5b00f7bf66&versionName=4.10.100&channel=Xiaomi&sid=session.1160904632837462555&lang=zh-CN&t=1477039747&sign=9159b250c3ed4592cf8e13f7b8792a4b";

    public static void getRedBookDataAsy(final RedBookCallback callback) {
        if (callback == null) return;
        final List<NoteModel> models = new ArrayList<>();
        final List<CommentModel> commentModels = new ArrayList<>();
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                try {
                    Response listResponse = client.newCall(new Request.Builder().url(LIST_URL).build()).execute();
                    Response commentResponse = client.newCall(new Request.Builder().url(COMMENT_URL).build()).execute();
                    JSONObject listJson = new JSONObject(listResponse.body().string());
                    JSONObject commentJson = new JSONObject(commentResponse.body().string());
                    JSONArray jsonArray = listJson.getJSONArray("data");
                    //列表
                    for (int j = 0; j < jsonArray.length(); j++) {
                        NoteModel model = new Gson().fromJson(jsonArray.get(j).toString(), NoteModel.class);
                        models.add(model);
                    }
                    //评论
                    JSONArray commentJsonJSONArray = commentJson.getJSONObject("data").getJSONArray("comments_list");
                    for (int i = 0; i < commentJsonJSONArray.length(); i++) {
                        CommentModel model = new Gson().fromJson(commentJsonJSONArray.get(i).toString(), CommentModel.class);
                        commentModels.add(model);
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(models, commentModels);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public interface RedBookCallback<T, M> {

        void onFailure(Call call, IOException e);

        void onResponse(List<T> datas, List<M> models);
    }
}
