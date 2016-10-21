package com.demo.adapter.note;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.model.HomeModel;
import com.demo.model.NoteModel;
import com.demo.model.PictureModel;
import com.demo.redbook.R;
import com.demo.utils.PictureSize;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import common.utils.ScreenUtils;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by liuhuiliang on 16/10/14.
 */

public class ContentDelegate extends AdapterDelegate<List<HomeModel>> {


    @Override
    protected boolean isForViewType(@NonNull List<HomeModel> items, int position) {
        return items.get(position) instanceof NoteModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<HomeModel> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        NoteModel itemModel = (NoteModel) items.get(position);
        PictureModel model = itemModel.pictures.get(0);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.ivContentImage.getLayoutParams();
        float halfWidth = (ScreenUtils.getScreenWidth(holder.itemView.getContext()) - 60) / 2;
        layoutParams.width = (int) halfWidth;
        PictureSize pictureSize = PictureSize.caculatePictureSize(model.height, model.width, (int) halfWidth);
        layoutParams.height = pictureSize.getScaleHeight();
        viewHolder.ivContentImage.setLayoutParams(layoutParams);
        viewHolder.tvNoteDes.setText(itemModel.desc);
        viewHolder.tvNoteTitle.setText(itemModel.title);
        viewHolder.tvUserName.setText(itemModel.user.nickname);
        viewHolder.tvNoteTitle.setText(itemModel.title);
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
        params.setMargins(15, 15, 15, 15);
        holder.itemView.setLayoutParams(params);
        Glide.with(holder.itemView.getContext()).load(model.url).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.ivContentImage);
        Glide.with(viewHolder.ivContentImage.getContext()).load(itemModel.user.images).override(pictureSize.getScaleWidth(), pictureSize.getScaleHeight()).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(viewHolder.ivUserHead.getContext())).placeholder(R.drawable.ic_default_head).into(viewHolder.ivUserHead);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivContentImage)
        ImageView ivContentImage;

        @Bind(R.id.ivUserHead)
        ImageView ivUserHead;

        @Bind(R.id.tvNoteTitle)
        TextView tvNoteTitle;

        @Bind(R.id.tvUserName)
        TextView tvUserName;

        @Bind(R.id.tvNoteDes)
        TextView tvNoteDes;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
