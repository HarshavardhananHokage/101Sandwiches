package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import eightloop.com.a101sandwiches.constants.AppConstants;
import eightloop.com.a101sandwiches.models.Sandwich;

/**
 * Created on 6/24/2016.
 */
public class SearchActivity extends AppCompatActivity implements SearchViewFragment.LoadSelectedSandwichFromSearch{

    public static final String TAG = "SearchActivity";

    FragmentManager fm;
    FragmentTransaction ft;
    IntroFragment introFragment;
    SearchViewFragment searchViewFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_search);
        searchViewFragment = new SearchViewFragment();
        replaceFragment(searchViewFragment, SearchViewFragment.TAG);
    }

    public void loadSandwich(Sandwich sandwich) {
        introFragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.SANDWICH, sandwich);
        introFragment.setArguments(args);
        replaceFragment(introFragment, IntroFragment.TAG);
    }

    public void replaceFragment(Fragment fragment, String TAG)
    {
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ft.replace(R.id.as_rl_search_item_container, fragment, TAG);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
