package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import eightloop.com.a101sandwiches.adapters.SandwichListAdapter;
import eightloop.com.a101sandwiches.constants.AppConstants;
import eightloop.com.a101sandwiches.database.SandwichDBHelper;
import eightloop.com.a101sandwiches.helpers.CopyDBFromAssets;
import eightloop.com.a101sandwiches.models.Sandwich;

/**
 * Created by Harshavardhan on 5/13/2016.
 */
public class IntroActivity extends AppCompatActivity implements SandwichListAdapter.OnClickLoadSandwichDetails{

    public static final String TAG = "IntroActivity";

    FragmentManager fm;
    FragmentTransaction ft;
    IntroFragment introFragment;
    SandwichListFragment sandwichListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //introFragment = new IntroFragment();
        //replaceFragment(introFragment, IntroFragment.TAG);

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
}
