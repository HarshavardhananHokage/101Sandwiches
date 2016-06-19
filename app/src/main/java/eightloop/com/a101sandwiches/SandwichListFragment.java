package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import eightloop.com.a101sandwiches.adapters.SandwichListAdapter;
import eightloop.com.a101sandwiches.database.SandwichManager;
import eightloop.com.a101sandwiches.models.Sandwich;

/**
 * Created by Harshavardhan
 */
public class SandwichListFragment extends Fragment implements SandwichListAdapter.MoveSandwichListener{

    public static final String TAG = "SandwichListFragment";

    View sandwichListFragView;
    Toolbar toolbar_sandwichList;
    RecyclerView recView_sandwichListItem;

    Button bt_tryNow;

    SandwichManager sandwichManager;
    SandwichListAdapter sandwichListAdapter;
    List<Sandwich> allSandwiches;

    AdView adView_listPage;
    AdRequest adRequest_listPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sandwichListFragView = inflater.inflate(R.layout.fragment_sandwich_list, container, false);
        toolbar_sandwichList = (Toolbar) sandwichListFragView.findViewById(R.id.fsl_toolbar_sandwich_list);
        toolbar_sandwichList.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_sandwichList);

        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null)
        {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_bars));
        }

        adView_listPage = (AdView) sandwichListFragView.findViewById(R.id.fsl_adv_googleAds);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected())
        {

            adRequest_listPage = new AdRequest.Builder().build();
            adView_listPage.loadAd(adRequest_listPage);
        }
        else
        {
            adView_listPage.setVisibility(View.GONE);
        }

        recView_sandwichListItem = (RecyclerView) sandwichListFragView.findViewById(R.id.fsl_recv_sandwich_list);

        bt_tryNow = (Button) sandwichListFragView.findViewById(R.id.fsl_cv_bt_try_now);

        sandwichManager = new SandwichManager(getActivity());
        allSandwiches = sandwichManager.getAllSandwiches();

        sandwichListAdapter = new SandwichListAdapter(getActivity(), allSandwiches, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext()
                , LinearLayoutManager.HORIZONTAL, false);
        recView_sandwichListItem.setLayoutManager(layoutManager);
        recView_sandwichListItem.setItemAnimator(new DefaultItemAnimator());
        recView_sandwichListItem.setHasFixedSize(true);

        recView_sandwichListItem.setAdapter(sandwichListAdapter);

        return sandwichListFragView;
    }

    @Override
    public void moveToPosition(int postion) {
        recView_sandwichListItem.smoothScrollToPosition(postion);
    }

    public void getFavSandwiches()
    {
        Log.e(TAG, "Came into sandwiches");
        List<Sandwich> favSandwich = sandwichManager.getFavSandwiches();
        sandwichListAdapter = new SandwichListAdapter(getActivity(), favSandwich, this);
        recView_sandwichListItem.setAdapter(sandwichListAdapter);
    }
}
