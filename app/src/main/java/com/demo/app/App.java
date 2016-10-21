package com.demo.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.demo.redbook.BuildConfig;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by liuhuiliang on 16/10/14.
 */

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 4);
        Cache cache = new Cache(new File(Environment.getExternalStorageDirectory().getPath() + "/RedBook"), 100 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .cache(cache)
                .build();

        Picasso picasso = new Picasso.Builder(getApplicationContext())
                .memoryCache(new LruCache(maxMemory))
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
        picasso.setIndicatorsEnabled(BuildConfig.DEBUG); // For debugging
        Picasso.setSingletonInstance(picasso);
    }
}
