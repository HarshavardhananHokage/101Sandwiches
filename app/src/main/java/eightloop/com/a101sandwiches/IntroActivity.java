package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Random;

import eightloop.com.a101sandwiches.adapters.SandwichListAdapter;
import eightloop.com.a101sandwiches.constants.AppConstants;
import eightloop.com.a101sandwiches.helpers.GeneralHelperMethods;
import eightloop.com.a101sandwiches.models.Sandwich;
import eightloop.com.a101sandwiches.sharedprefs.AppPreferenceHandler;

/**
 * Created by Harshavardhan
 */
public class IntroActivity extends AppCompatActivity implements SandwichListAdapter.OnClickLoadSandwichDetails, NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = "IntroActivity";

    FragmentManager fm;
    FragmentTransaction ft;
    IntroFragment introFragment;
    SandwichListFragment sandwichListFragment;

    NavigationView navView_sandwichDrawer;
    DrawerLayout drawLay_introDrawer;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        drawLay_introDrawer = (DrawerLayout) findViewById(R.id.ai_drawer_layout);
        navView_sandwichDrawer = (NavigationView) findViewById(R.id.ai_drawer_navigation_view);
        navView_sandwichDrawer.setItemIconTintList(null);
        navView_sandwichDrawer.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawLay_introDrawer, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawLay_introDrawer.setDrawerListener(actionBarDrawerToggle);

        sandwichListFragment = new SandwichListFragment();
        replaceFragment(sandwichListFragment, SandwichListFragment.TAG);
    }

    public void replaceFragment(Fragment fragment, String TAG)
    {
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.intro_fragment_container, fragment, TAG);
        if(fragment instanceof IntroFragment)
        {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    @Override
    public void loadSandwichDetails(Sandwich sandwich) {
        introFragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.SANDWICH, sandwich);
        introFragment.setArguments(args);
        replaceFragment(introFragment, IntroFragment.TAG);
    }

    public static Intent newIntent(Context context)
    {
        return new Intent(context, IntroActivity.class);
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.e(TAG, "Item Selected: " +item.getTitle());
        drawLay_introDrawer.closeDrawer(GravityCompat.START);
        navItemSelected(item.getTitle().toString());
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void navItemSelected(String item)
    {
        AppPreferenceHandler appPreferenceHandler = new AppPreferenceHandler(this);
        Fragment currVisibleFrag = fm.findFragmentByTag(SandwichListFragment.TAG);
        if(currVisibleFrag != null && currVisibleFrag.isVisible())
        {
            switch (item)
            {
                case "Home":
                /*currVisibleFrag = fm.findFragmentByTag(IntroFragment.TAG);
                if(currVisibleFrag != null && currVisibleFrag.isVisible())
                {
                    Log.e(TAG, "Came into moving to home");
                    onBackPressed();
                }*/
                    if(appPreferenceHandler.isCurrentListFavourite())
                    {
                        Log.e(TAG, "Came into reload full list");
                        ((SandwichListFragment) currVisibleFrag).getAllSandwiches();
                    }
                    break;
                case "Surprise Me":
                    int count = ((SandwichListFragment) currVisibleFrag).getCount();
                    ((SandwichListFragment) currVisibleFrag).moveToPosition(new Random().nextInt(count));
                    Toast.makeText(this, "Surprise!", Toast.LENGTH_SHORT).show();
                    break;
                case "Favourites":
                    ((SandwichListFragment) currVisibleFrag).getFavSandwiches();
                    break;
                case "Classic":
                    ((SandwichListFragment) currVisibleFrag).getClassifiedSandwiches("Classic");
                    break;
                case "Luxe":
                    ((SandwichListFragment) currVisibleFrag).getClassifiedSandwiches("Luxe");
                    break;
                case "Spice":
                    ((SandwichListFragment) currVisibleFrag).getClassifiedSandwiches("Spice");
                    break;
                case "Guilty":
                    ((SandwichListFragment) currVisibleFrag).getClassifiedSandwiches("Guilty");
                    break;
                case "Sweet":
                    ((SandwichListFragment) currVisibleFrag).getClassifiedSandwiches("Sweet");
                    break;
                case "Veggies":
                    ((SandwichListFragment) currVisibleFrag).getClassifiedSandwiches("Veggies");
                    break;
                case "Rate App":
                    GeneralHelperMethods.rateAppAtPlayStore(this);
                    break;
                case "Remove Ads":
                    Toast.makeText(this, "Ads Removed", Toast.LENGTH_SHORT).show();
                    break;
                case "Other Apps":
                    GeneralHelperMethods.otherAppsByDeveloper(this);
                    break;
            }
        }
        else
        {
            boolean shouldLoad = true;
            SandwichListFragment sandwichListFragmentNew = new SandwichListFragment();
            Bundle bundle = new Bundle();
            switch (item)
            {
                case "Home":
                    bundle.putString(AppConstants.FROM_INTRO_FRAG, "Home");
                    break;
                case "Surprise Me":
                    bundle.putString(AppConstants.FROM_INTRO_FRAG, "Surprise Me");
                    Toast.makeText(this, "Surprise!", Toast.LENGTH_SHORT).show();
                    break;
                case "Favourites":
                    bundle.putString(AppConstants.FROM_INTRO_FRAG, "Favourites");
                    break;
                case "Classic":
                    bundle.putString(AppConstants.FROM_INTRO_FRAG, "Classic");
                    break;
                case "Luxe":
                    bundle.putString(AppConstants.FROM_INTRO_FRAG, "Luxe");
                    break;
                case "Spice":
                    bundle.putString(AppConstants.FROM_INTRO_FRAG, "Spice");
                    break;
                case "Guilty":
                    bundle.putString(AppConstants.FROM_INTRO_FRAG, "Guilty");
                    break;
                case "Sweet":
                    bundle.putString(AppConstants.FROM_INTRO_FRAG, "Sweet");
                    break;
                case "Veggies":
                    bundle.putString(AppConstants.FROM_INTRO_FRAG, "Veggies");
                    break;
                case "Rate App":
                    GeneralHelperMethods.rateAppAtPlayStore(this);
                    shouldLoad = false;
                    break;
                case "Remove Ads":
                    Toast.makeText(this, "Ads Removed", Toast.LENGTH_SHORT).show();
                    shouldLoad = false;
                    break;
                case "Other Apps":
                    GeneralHelperMethods.otherAppsByDeveloper(this);
                    shouldLoad = false;
                    break;
            }

            if(shouldLoad)
            {
                onBackPressed();
                sandwichListFragmentNew.setArguments(bundle);
                replaceFragment(sandwichListFragmentNew, SandwichListFragment.TAG);
            }
        }
    }
}
