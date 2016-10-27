package com.demo.redbook;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.demo.adapter.note.HomeAdapter;
import com.demo.data.RedBookData;
import com.demo.model.CommentModel;
import com.demo.model.HomeModel;
import com.demo.model.NoteModel;
import com.demo.utils.StatusBarUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements RedBookData.RedBookCallback<NoteModel, CommentModel> {


    @Bind(R.id.rvMain)
    RecyclerView rvMain;

    List<HomeModel> mPictureModels = new ArrayList<>();
    HomeAdapter mHomeAdapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView.ItemDecoration itemDecoration=new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        };
        rvMain.setLayoutManager(layoutManager);
        mHomeAdapter = new HomeAdapter(mPictureModels);
        rvMain.setAdapter(mHomeAdapter);
        RedBookData.getRedBookDataAsy(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        StatusBarUtils.MIUISetStatusBarLightMode(getWindow(), true);
    }


    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(List<NoteModel> datas, List<CommentModel> models) {
        mPictureModels.clear();
        mPictureModels.add(datas.get(0));
        mPictureModels.addAll(models);
        mPictureModels.addAll(datas);
        mHomeAdapter.notifyDataSetChanged();
    }


}
