package eightloop.com.a101sandwiches;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.os.Handler;

/**
 * Created on 9/17/2016.
 */
public class SplashContainerActivity extends Activity {
    
    public static final String TAG = "SplashContainerActivity";

    FragmentManager fm;
    FragmentTransaction ft;

    Handler handler;

    SplashScreenFragment splashScreenFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_container);
        splashScreenFragment = new SplashScreenFragment();
        loadFragment(splashScreenFragment, SplashScreenFragment.TAG);
        handler =  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = IntroActivity.newIntent(SplashContainerActivity.this);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    public void loadFragment(Fragment fragment, String fragTag)
    {
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.asc_fragmentContainer, fragment, fragTag);
        ft.commit();
    }
}
