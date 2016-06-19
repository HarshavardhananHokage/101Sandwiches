package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
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

import java.util.Random;

import eightloop.com.a101sandwiches.adapters.SandwichListAdapter;
import eightloop.com.a101sandwiches.constants.AppConstants;
import eightloop.com.a101sandwiches.models.Sandwich;

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
                    break;
                case "Suprise Me":
                    ((SandwichListFragment) currVisibleFrag).moveToPosition(new Random().nextInt(4));
                    break;
                case "Favourites":
                    ((SandwichListFragment) currVisibleFrag).getFavSandwiches();
                    break;
            }
        }

    }
}
