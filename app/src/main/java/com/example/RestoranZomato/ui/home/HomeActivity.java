package com.example.RestoranZomato.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RestoranZomato.data.network.model.RestaurantsResponse;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.RestoranZomato.R;
import com.example.RestoranZomato.data.prefs.PreferencesHelper;
import com.example.RestoranZomato.ui.base.BaseActivity;
import com.example.RestoranZomato.ui.home.restaurantlist.RestaurantAdapter;
import com.example.RestoranZomato.ui.home.restaurantlist.RestaurantListInterface;
import com.example.RestoranZomato.ui.imageviewer.ImageViewerActivity;
import com.example.RestoranZomato.ui.restaurantpage.RestaurantDetailActivity;
import com.example.RestoranZomato.util.AppConstants.Params;
import com.example.RestoranZomato.util.CommonUtils;
import rx.android.schedulers.AndroidSchedulers;

import static com.example.RestoranZomato.service.LocationService.ACTION_LOCATION_BROADCAST;

public class HomeActivity extends BaseActivity
        implements HomeMvpView, RestaurantListInterface {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.restaurantList)
    RecyclerView restaurantList;
    @BindView(R.id.contentLayout)
    View contentLayout;
    @BindView(R.id.nothingFound)
    View nothingFound;
    @BindView(R.id.addressHeader)
    TextView addressHeader;
    @BindView(R.id.locationType)
    TextView locationType;
    @BindView(R.id.addressSubHeader)
    TextView addressSubHeader;
    @BindView(R.id.search_query)
    EditText etSearchRestaurants;
    @BindView(R.id.button_return_to_top)
    View returnToTop;

    @Inject
    HomeMvpPresenter<HomeMvpView> mPresenter;

    @Inject
    PreferencesHelper preferencesHelper;

    @Inject
    RestaurantAdapter restaurantsAdapter;
    @Inject
    LinearLayoutManager restaurantLayoutManager;

    private BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            mPresenter.saveMyLocation(intent.getDoubleExtra(Params.LATITUDE, 0.0f),
                    intent.getDoubleExtra(Params.LONGITUDE, 0.0f),
                    intent.getStringExtra(Params.CITY),
                    intent.getStringExtra(Params.STREET));
            showRestaurants();

            //Stop service and broadcast
            stopLocationService();
            LocalBroadcastManager.getInstance(HomeActivity.this).unregisterReceiver(this);
        }
    };

    //Scroll listener
    private RecyclerView.OnScrollListener restaurantListScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (linearLayoutManager == null) return;

            if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                returnToTop.setVisibility(View.GONE);
            } else if (linearLayoutManager.findFirstVisibleItemPosition() > 2) {
                returnToTop.setVisibility(View.VISIBLE);
            }

            //Skip take
            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == restaurantsAdapter.getItemCount() - 1) {
                restaurantsAdapter.loadMoreRestaurants();
            }
        }
    };

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        Bundle extras = new Bundle();
        intent.putExtras(extras);
        return intent;
    }

    public static Intent getLocationBroadcastIntent(Double lat, Double lng, String cityName, String streetName) {
        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(Params.CITY, cityName);
        intent.putExtra(Params.STREET, streetName);
        intent.putExtra(Params.LATITUDE, lat);
        intent.putExtra(Params.LONGITUDE, lng);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUnBinder(ButterKnife.bind(this));

        getActivityComponent().inject(HomeActivity.this);

        mPresenter.onAttach(HomeActivity.this);

        setUp();
    }

    @Override
    public void startLocationBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
                locationReceiver, new IntentFilter(ACTION_LOCATION_BROADCAST)
        );
    }

    @Override
    public void stopLocationBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(locationReceiver);
    }

    @Override
    public void locationPermissionGranted() {
        CommonUtils.showShortToast(this, getString(R.string.showing_restaurants_around_you));
        startLocationService();
    }

    @Override
    public void locationPermissionNotGranted() {
        mPresenter.clearLocationPreference();
        CommonUtils.showShortToast(this, getString(R.string.showing_restaurants_over_the_world));
        showRestaurants();
    }

    @Override
    public void showRestaurants() {
        locationType.setText(getString(preferencesHelper.isPreferenceMyLocation() ?
                R.string.your_location : R.string.custom_location));

        if (preferencesHelper.getLatitude() != 0 && preferencesHelper.getLongitude() != 0) {
            addressHeader.setText(preferencesHelper.getCity());
            addressSubHeader.setText(preferencesHelper.getLocality());
            mPresenter.fetchRestaurants(etSearchRestaurants.getText().toString(),
                    preferencesHelper.getLatitude(),
                    preferencesHelper.getLongitude(), 0);
        } else {
            addressHeader.setText(getString(R.string.choose_location));
            addressSubHeader.setText(getString(R.string.choose_location_for_handpicked_restaurants));
            mPresenter.fetchRestaurants(etSearchRestaurants.getText().toString(),
                    null, null, 0);
        }
    }

    @Override
    protected void setUp() {
        etSearchRestaurants.setHint(getString(R.string.search_restaurants));
        //Debounce search
        RxTextView.textChanges(etSearchRestaurants)
                .filter(charSequence -> charSequence.length() > 3)
                .debounce(1, TimeUnit.SECONDS)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> showRestaurants());

        restaurantList.setLayoutManager(restaurantLayoutManager);
        restaurantList.setAdapter(restaurantsAdapter);

        //API call
        if (preferencesHelper.isPreferenceMyLocation()) {
            showRestaurantsNearMe();
        } else {
            showRestaurants();
        }

        returnToTop.setVisibility(View.GONE);
        returnToTop.setOnClickListener(v -> {
            restaurantList.smoothScrollToPosition(0);
            returnToTop.setVisibility(View.GONE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationBroadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationBroadcastReceiver();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        hideKeyboard();
        showRestaurants();
    }

    @Override
    public void onOpenRestaurantDetail(String restaurantId) {
        startActivity(RestaurantDetailActivity.getStartIntent(this, restaurantId));
    }

    @Override
    public void onImagePreviewClicked(String restaurantId, String imageUrl, String restaurantName, String restaurantThumb) {
        startActivity(ImageViewerActivity.getStartIntent(this,
                imageUrl,
                restaurantName,
                restaurantThumb));
    }

    @Override
    public void onSeeAllPreview(String imagesUrl) {
        CustomTabsIntent customTabsIntent = CommonUtils.getChromeCustomTab(R.color.colorPrimary);
        customTabsIntent.launchUrl(this, Uri.parse(imagesUrl));
    }

    @Override
    public void onLoadMoreRestaurants(int skip) {
        mPresenter.fetchRestaurants(etSearchRestaurants.getText().toString(),
                preferencesHelper.getLatitude(),
                preferencesHelper.getLongitude(),
                skip);
    }


    @Override
    public void onRestaurantResponse(RestaurantsResponse restaurantsResponse) {
        restaurantsAdapter.clearRestaurants();
        if (restaurantsResponse.getRestaurants().size() == 0) {
            nothingFound.setVisibility(View.VISIBLE);
            contentLayout.setVisibility(View.GONE);
        } else {
            nothingFound.setVisibility(View.GONE);
            contentLayout.setVisibility(View.VISIBLE);
            restaurantsAdapter.addRestaurants(restaurantsResponse.getRestaurants());
        }
    }

    @Override
    public void showLoading() {
        restaurantsAdapter.clearRestaurants();
        restaurantList.addOnScrollListener(restaurantListScrollListener);
        contentLayout.setVisibility(View.GONE);
        nothingFound.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        contentLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMoreRestaurantResponse(RestaurantsResponse restaurantsResponse) {
        if (restaurantsResponse.getRestaurants().size() == 0) {
            restaurantList.removeOnScrollListener(restaurantListScrollListener);
            restaurantsAdapter.addMoreRestaurants(null);
        } else {
            restaurantsAdapter.addMoreRestaurants(restaurantsResponse.getRestaurants());
            restaurantsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        stopLocationService();
        super.onDestroy();
    }
}
