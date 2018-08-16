package com.github.wkw.share.ui.binds;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageBinds {
    @BindingAdapter("url")
    public static void loadImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter("visibleGone")
    public static void showHide(View view, Boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
