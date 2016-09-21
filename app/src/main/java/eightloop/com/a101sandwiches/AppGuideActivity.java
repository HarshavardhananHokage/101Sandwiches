package eightloop.com.a101sandwiches;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created on 9/21/2016.
 */

public class AppGuideActivity extends Activity {
    
    public static final String TAG = "AppGuideActivity";

    FragmentManager fm;
    FragmentTransaction ft;

    AppGuideIntroFragment appGuideIntroFragment;
    AppGuideGroupsFragment appGuideGroupsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_guide);
        /*appGuideIntroFragment = new AppGuideIntroFragment();
        loadFragment(appGuideIntroFragment, AppGuideIntroFragment.TAG);*/

        appGuideGroupsFragment = new AppGuideGroupsFragment();
        loadFragment(appGuideGroupsFragment, AppGuideGroupsFragment.TAG);
    }

    public void loadFragment(Fragment fragment, String fragTag)
    {
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.aag_fragmentContainer, fragment, fragTag);
        ft.commit();
    }
}
