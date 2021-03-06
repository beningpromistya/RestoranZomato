package com.example.RestoranZomato.di.module;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.example.RestoranZomato.BuildConfig;
import com.example.RestoranZomato.RestoranZomato;
import com.example.RestoranZomato.R;
import com.example.RestoranZomato.data.AppDataManager;
import com.example.RestoranZomato.data.DataManager;
import com.example.RestoranZomato.data.db.AppDbHelper;
import com.example.RestoranZomato.data.db.DbHelper;
import com.example.RestoranZomato.data.network.ApiClient;
import com.example.RestoranZomato.data.network.ApiHeader;
import com.example.RestoranZomato.data.prefs.AppPreferencesHelper;
import com.example.RestoranZomato.data.prefs.PreferencesHelper;
import com.example.RestoranZomato.di.ApplicationContext;
import com.example.RestoranZomato.di.DatabaseInfo;
import com.example.RestoranZomato.di.PreferenceInfo;
import com.example.RestoranZomato.util.AppConstants;
import com.example.RestoranZomato.util.AppConstants.Params;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.example.RestoranZomato.data.network.ApiHeader.ProtectedApiHeader;
import static com.example.RestoranZomato.data.network.ApiHeader.PublicApiHeader;

@Module
public class ApplicationModule {

    private final RestoranZomato mApplication;

    public ApplicationModule(RestoranZomato application) {
        this.mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseInfo() {
        return AppConstants.DB_NAME;
    }

    @Provides
    RestoranZomato provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Cache cache) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    // Customize the request
                    Request request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .removeHeader("Pragma")
                            .header("Cache-Control", String.format("max-age=%s", BuildConfig.CACHETIME))
                            .build();

                    Response response = chain.proceed(request);
                    response.cacheResponse();
                    // Customize or return the response
                    Log.d("API", "HomePresenter: " + response.toString());
                    return response;
                })
                .cache(cache)
                .build();


        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(provideGSonConverterFactory())
                .addCallAdapterFactory(provideRxJavaCallAdapterFactory())
                .build();
    }

    @Provides
    @Singleton
    Converter.Factory provideGSonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    CallAdapter.Factory provideRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }


    @Provides
    @Singleton
    ApiClient provideApiClient(
            Retrofit retrofit) {
        return retrofit.create(ApiClient.class);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(
            @ApplicationContext Context context, DbHelper dbHelper, PreferencesHelper prefHelper, ApiClient apiClient) {
        return new AppDataManager(context, dbHelper, prefHelper, apiClient);
    }

    @Provides
    @Singleton
    Cache provideHttpCache(RestoranZomato application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    ApiHeader providerDefaultHeaders(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader) {
        return new ApiHeader(publicApiHeader, protectedApiHeader);
    }

    @Provides
    @Singleton
    PublicApiHeader providerPublicHeaders() {
        Map<String, String> publicHeaders = new HashMap<>();
        return new PublicApiHeader(publicHeaders);
    }

    @Provides
    @Singleton
    ProtectedApiHeader providerProtectedHeaders() {
        Map<String, String> protectedHeaders = new HashMap<>();
        protectedHeaders.put(Params.USER_KEY, BuildConfig.API_KEY);
        return new ProtectedApiHeader(protectedHeaders);
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }


    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }
}
