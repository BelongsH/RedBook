package com.demo.adapter.note;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.model.CommentModel;
import com.demo.model.HomeModel;
import com.demo.redbook.R;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by liuhuiliang on 16/10/21.
 */

public class CommentDelegate extends AdapterDelegate<List<HomeModel>> {
    @Override
    protected boolean isForViewType(@NonNull List<HomeModel> items, int position) {
        return items.get(position) instanceof CommentModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_comment, parent, false);
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setFullSpan(true);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<HomeModel> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        CommentModel model = (CommentModel) items.get(position);
        ViewHolder commentHolder = (ViewHolder) holder;
        commentHolder.tvCommentContent.setText(model.content);
        commentHolder.tvCommentTime.setText(model.time);
        commentHolder.tvUserName.setText(model.user.nickname);
        Glide.with(commentHolder.itemView.getContext()).load(model.user.images).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(commentHolder.itemView.getContext())).placeholder(R.drawable.ic_default_head).into(commentHolder.ivUserHead);

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvCommentContent)
        TextView tvCommentContent;

        @Bind(R.id.tvCommentTime)
        TextView tvCommentTime;

        @Bind(R.id.tvUserName)
        TextView tvUserName;

        @Bind(R.id.ivUserHead)
        ImageView ivUserHead;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
