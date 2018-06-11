package com.xiaoxing.salesclient.mvp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.xiaoxing.salesclient.mvp.ui.entity.BannerItem;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setImageResource(((BannerItem) path).pic);
    }
}