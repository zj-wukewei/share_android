package com.github.wkw.share.ui.binds;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageBinds {
    @BindingAdapter("url")
    public static void loadImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
