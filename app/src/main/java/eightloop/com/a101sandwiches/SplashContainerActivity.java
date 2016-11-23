package eightloop.com.a101sandwiches;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.os.Handler;

import eightloop.com.a101sandwiches.appguide.AppGuideIntroFragment;
import eightloop.com.a101sandwiches.sharedprefs.AppPreferenceHandler;

/**
 * Created on 9/17/2016.
 */
public class SplashContainerActivity extends Activity {
    
    public static final String TAG = "SplashContainerActivity";

    FragmentManager fm;
    FragmentTransaction ft;

    Handler handler;

    SplashScreenFragment splashScreenFragment;
    AppGuideIntroFragment appGuideFragment;
    AppPreferenceHandler appPreferenceHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_container);

        appPreferenceHandler = new AppPreferenceHandler(this);

        splashScreenFragment = new SplashScreenFragment();
        loadFragment(splashScreenFragment, SplashScreenFragment.TAG);

        /*handler =  new Handler();
        if(appPreferenceHandler.getIsAppLaunchedFirstTime() == 0)
        {
            appPreferenceHandler.setIsAppLaunchedFirstTime(1);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = AppGuideActivity.newIntent(SplashContainerActivity.this);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
        else
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = IntroActivity.newIntent(SplashContainerActivity.this);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }*/
    }

    public void loadFragment(Fragment fragment, String fragTag)
    {
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.asc_fragmentContainer, fragment, fragTag);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //handler.removeCallbacksAndMessages(null);
    }
}
