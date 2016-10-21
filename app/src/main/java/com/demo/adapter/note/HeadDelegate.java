package com.demo.adapter.note;

import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

public class HeadDelegate extends AdapterDelegate<List<HomeModel>> {

    private SimpleArrayMap<String, PictureSize> mPictureSizes = new SimpleArrayMap<>();
    private NoteModel mNoteModel;
    float screenWidth;

    @Override
    protected boolean isForViewType(@NonNull List<HomeModel> items, int position) {
        HomeModel model = items.get(0);
        if (model instanceof NoteModel) {
            mNoteModel = (NoteModel) model;
        }
        return position == 0;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        HeadViewHolder headViewHolder = new HeadViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_head, parent, false));
        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setFullSpan(true);
        headViewHolder.itemView.setLayoutParams(layoutParams);
        screenWidth = (ScreenUtils.getScreenWidth(parent.getContext()) + 0f);
        return headViewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull final List<HomeModel> items, int position, @NonNull final RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final HeadViewHolder holder1 = (HeadViewHolder) holder;
        holder1.vpHeadDelegate.setOffscreenPageLimit(mNoteModel.pictures.size());
        holder1.vpHeadDelegate.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mNoteModel.pictures.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(holder1.vpHeadDelegate.getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
                container.addView(imageView);
                PictureSize pictureSize = mPictureSizes.get(String.valueOf(position));
                if (pictureSize == null) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) container.getLayoutParams();
                    PictureModel pictureModel = mNoteModel.pictures.get(position);
                    pictureSize = PictureSize.caculatePictureSize(pictureModel.height, pictureModel.width, (int) screenWidth);
                    mPictureSizes.put(String.valueOf(position), pictureSize);
                    params.height = pictureSize.getScaleHeight();
                    params.width = pictureSize.getScaleWidth();
                    container.setLayoutParams(params);
                    imageView.requestLayout();
                }
                Glide.with(holder1.vpHeadDelegate.getContext()).load(mNoteModel.pictures.get(position).original).override(pictureSize.getScaleWidth(), pictureSize.getScaleHeight()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeAllViews();
            }
        });
        holder1.vpHeadDelegate.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                if (position > 0 && position <= 1) {
                    int badgePosition = (int) (page.getX() / screenWidth) - 1;
                    System.out.println(badgePosition);
                    PictureSize offsetModel = mPictureSizes.get(String.valueOf(badgePosition + 1));
                    if (offsetModel == null) return;
                    PictureSize nowModel = mPictureSizes.get(String.valueOf(badgePosition));
                    float disHeight = (offsetModel.getScaleHeight() - nowModel.getScaleHeight()) * (1 - position);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder1.vpHeadDelegate.getLayoutParams();
                    params.width = nowModel.getScaleWidth();
                    params.height = (int) (nowModel.getScaleHeight() + disHeight);
                    holder1.vpHeadDelegate.setLayoutParams(params);
                    holder1.vpHeadDelegate.requestLayout();
                }
            }
        });
        holder1.tvDes.setText(mNoteModel.desc);
        holder1.tvUserName.setText(mNoteModel.user.nickname);
        Glide.with(holder1.vpHeadDelegate.getContext()).load(mNoteModel.user.images).diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CropCircleTransformation(holder1.vpHeadDelegate.getContext())).placeholder(R.drawable.ic_default_head).into(holder1.ivHeadPicture);
    }


    class HeadViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.vpHeadDelegate)
        ViewPager vpHeadDelegate;

        @Bind(R.id.tvDes)
        TextView tvDes;

        @Bind(R.id.tvUserName)
        TextView tvUserName;


        @Bind(R.id.ivHeadPicture)
        ImageView ivHeadPicture;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}