package com.example.RestoranZomato.test;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.RestoranZomato.BuildConfig;
import com.example.RestoranZomato.R;
import com.example.RestoranZomato.ui.imageviewer.ImageViewerActivity;
import com.example.RestoranZomato.ui.imageviewer.ImageViewerMvpPresenter;
import com.example.RestoranZomato.ui.imageviewer.ImageViewerMvpView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import butterknife.BindView;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class ImageViewerTest {

    private Bundle extras;

    public static Intent getStartIntent(Context context, String url, String restaurantName,
                                        String restaurantThumb) {
        Intent intent = new Intent(context, ImageViewerActivity.class);
        Bundle extras = new Bundle();
        extras.putString(IMAGE_URL, url);
        extras.putString(IMAGE_RESTAURANT_THUMB, restaurantThumb);
        extras.putString(NAME_RESTAURANT, restaurantName);
        intent.putExtras(extras);
        return intent;
    }

    private static final String IMAGE_URL = "image_url";
    private static final String IMAGE_RESTAURANT_THUMB = "image_restaurant_thumb";
    private static final String NAME_RESTAURANT = "name_restaurant";
    ImageViewerActivity activity;

    @Inject
    ImageViewerMvpPresenter<ImageViewerMvpView> mPresenter;
    @BindView(R.id.restaurantThumb)
    ImageView restaurantThumb;
    @BindView(R.id.restaurantName)
    TextView restaurantName;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.toolbar)
    View toolbar;
    @BindView(R.id.back)
    View back;

    @Test
    public void setUp(){
        Bundle extras = getExtras();

        if (extras == null) {
//            showShortToast("Some Error");
            activity.finish();
        } else {
            Glide.with(activity)
                    .load(extras.getString(IMAGE_RESTAURANT_THUMB));

            Glide.with(activity)
                    .load(extras.getString(IMAGE_URL));

            restaurantName.setText(extras.getString(NAME_RESTAURANT));
        }
    }

    public Bundle getExtras() {
        return extras;
    }
}
