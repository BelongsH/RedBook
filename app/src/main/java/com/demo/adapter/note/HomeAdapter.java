package com.demo.adapter.note;

import com.demo.model.HomeModel;
import com.hannesdorfmann.adapterdelegates3.AbsDelegationAdapter;

import java.util.List;

/**
 * Created by liuhuiliang on 16/10/14.
 */

public class HomeAdapter extends AbsDelegationAdapter<List<HomeModel>> {

    private List<HomeModel> mDatas;

    public HomeAdapter(List<HomeModel> datas) {
        super();
        setItems(datas);
        this.mDatas = datas;
        this.delegatesManager.addDelegate(new HeadDelegate());
        this.delegatesManager.addDelegate(new ContentDelegate());
        this.delegatesManager.addDelegate(new CommentDelegate());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


}
