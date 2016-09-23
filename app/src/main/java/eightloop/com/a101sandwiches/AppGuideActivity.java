package eightloop.com.a101sandwiches;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import eightloop.com.a101sandwiches.interfaces.AppGuideCallbackInterface;

/**
 * Created on 9/21/2016.
 */

public class AppGuideActivity extends Activity implements AppGuideCallbackInterface {
    
    public static final String TAG = "AppGuideActivity";

    FragmentManager fm;
    FragmentTransaction ft;

    AppGuideIntroFragment appGuideIntroFragment;
    AppGuideGroupsFragment appGuideGroupsFragment;
    AppGuideMainFragment appGuideMainFragment;
    AppGuideDetailsFragment appGuideDetailsFragment;
    AppGuideSurpriseFragment appGuideSurpriseFragment;
    AppGuideRateFragment appGuideRateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_guide);

        appGuideIntroFragment = new AppGuideIntroFragment();
        loadFragment(appGuideIntroFragment, AppGuideIntroFragment.TAG);

    }


    public void loadFragment(Fragment fragment, String fragTag)
    {
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.aag_fragmentContainer, fragment, fragTag);
        ft.commit();
    }

    @Override
    public void nextCalled(int guideID) {
        switch (guideID)
        {
            case 1:
                appGuideGroupsFragment = new AppGuideGroupsFragment();
                loadFragment(appGuideGroupsFragment, AppGuideGroupsFragment.TAG);
                break;
            case 2:
                appGuideMainFragment = new AppGuideMainFragment();
                loadFragment(appGuideMainFragment, AppGuideMainFragment.TAG);
                break;
            case 3:
                appGuideDetailsFragment = new AppGuideDetailsFragment();
                loadFragment(appGuideDetailsFragment, AppGuideDetailsFragment.TAG);
                break;
            case 4:
                appGuideSurpriseFragment = new AppGuideSurpriseFragment();
                loadFragment(appGuideSurpriseFragment, AppGuideSurpriseFragment.TAG);
                break;
            case 5:
                appGuideRateFragment = new AppGuideRateFragment();
                loadFragment(appGuideRateFragment, AppGuideRateFragment.TAG);
                break;
            case 6:
                loadSandwichList();
                break;
        }
    }

    @Override
    public void skipCalled(int guideID) {
        loadSandwichList();
    }

    public void loadSandwichList(){

        Intent sandwichListIntent = IntroActivity.newIntent(this);
        startActivity(sandwichListIntent);
        finish();
    }
}
