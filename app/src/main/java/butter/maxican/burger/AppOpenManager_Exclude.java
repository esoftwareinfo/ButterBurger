package butter.maxican.burger;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;


public class AppOpenManager_Exclude implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private static final String LOG_TAG = "AppOpenManager";
    private AppOpenAd appOpenAd = null;
    private Activity currentActivity;
    private static boolean isShowingAd = false;
    String App_Open_ID = "";
    int Check = 1;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private final Application myApplication;
    String AC1 = "", AC2 = "", AC3 = "", AC4 = "", AC5 = "";


    public AppOpenManager_Exclude(Application myApplication, String Ac1, String Ac2, String Ac3, String Ac4, String Ac5) {
        this.myApplication = myApplication;

        this.AC1 = Ac1;
        this.AC2 = Ac2;
        this.AC3 = Ac3;
        this.AC4 = Ac4;
        this.AC5 = Ac5;

        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);


    }


    public void showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable()) {


            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            AppOpenManager_Exclude.this.appOpenAd = null;
                            isShowingAd = false;
                            Check = 1;
                            fetchAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };

            appOpenAd.show(currentActivity, fullScreenContentCallback);

        } else {

            fetchAd();
        }
    }


    public void fetchAd() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Pizza.Server_Yes_No == 1 || Pizza.Server_Yes_No == 0) {

                    if (Pizza.Exit_Menu_Decided == 100) {

                        return;
                    }

                    // Have unused ad, no need to fetch another.
                    if (isAdAvailable()) {
                        return;
                    }

                    if ((Butter.getSetup(myApplication)).equals("1")) {

                        if (Check == 1) {

                            App_Open_ID = Butter.getAO11(myApplication);
                        } else if (Check == 2) {

                            App_Open_ID = Butter.getAO111(myApplication);
                        } else if (Check == 3) {

                            App_Open_ID = Butter.getAO22(myApplication);
                        } else if (Check == 4) {

                            App_Open_ID = Butter.getAO222(myApplication);
                        } else if (Check == 5) {

                            App_Open_ID = Butter.getAO33(myApplication);
                        } else if (Check == 6) {

                            App_Open_ID = Butter.getAO333(myApplication);
                            Check = 0;
                        } else {

                        }


                    } else if ((Butter.getSetup(myApplication)).equals("0")) {

                        App_Open_ID = Butter.getapp_open(myApplication);


                    }

                    loadCallback =
                            new AppOpenAd.AppOpenAdLoadCallback() {


                                @Override
                                public void onAppOpenAdLoaded(AppOpenAd ad) {
                                    AppOpenManager_Exclude.this.appOpenAd = ad;
                                }


                                @Override
                                public void onAppOpenAdFailedToLoad(LoadAdError loadAdError) {
                                    // Handle the error.


                                    if ((Butter.getSetup(myApplication)).equals("1")) {
                                        Check = Check + 1;
                                        fetchAd();
                                    }

                                }


                            };
                    AdRequest request = getAdRequest();
                    AppOpenAd.load(
                            myApplication, App_Open_ID, request,
                            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

                } else {

                    handler.postDelayed(this, 1000);

                }

            }

        }, 1000);


    }


    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }


    public boolean isAdAvailable() {
        return appOpenAd != null;
    }


    @OnLifecycleEvent(ON_START)
    public void onStart() {

        if ((currentActivity.getLocalClassName()).equals(AC1) ||
                (currentActivity.getLocalClassName()).equals(AC2) ||
                (currentActivity.getLocalClassName()).equals(AC3) ||
                (currentActivity.getLocalClassName()).equals(AC4) ||
                (currentActivity.getLocalClassName()).equals(AC5)) {


        } else {
            showAdIfAvailable();
        }
        //showAdIfAvailable();

    }


    @Override
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }

}

