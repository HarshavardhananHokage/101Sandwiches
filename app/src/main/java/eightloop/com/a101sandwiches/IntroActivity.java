package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Harshavardhan on 5/13/2016.
 */
public class IntroActivity extends AppCompatActivity {

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
        ft.replace(R.id.intro_fragment_container, fragment, TAG);
        ft.commit();
    }
}
