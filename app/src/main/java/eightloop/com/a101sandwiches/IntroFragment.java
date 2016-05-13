package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Harshavardhan on 5/13/2016.
 */
public class IntroFragment extends Fragment {

    public static final String TAG = "IntroFragment";

    View introFragView;
    Toolbar introToolbar;

    AdView adView_introPage;
    AdRequest adRequest_introPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        introFragView = inflater.inflate(R.layout.fragment_sandwich_intro, container, false);
        introToolbar = (Toolbar) introFragView.findViewById(R.id.fsi_toolbar_custom);
        introToolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(introToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        /*((AppCompatActivity) getActivity()).getSupportActionBar().
                setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_list));*/

        adView_introPage = (AdView) introFragView.findViewById(R.id.fsi_adv_googleAds);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected())
        {

            adRequest_introPage = new AdRequest.Builder().build();
            adView_introPage.loadAd(adRequest_introPage);
        }
        else
        {
            adView_introPage.setVisibility(View.GONE);
        }
        return introFragView;
    }
}
