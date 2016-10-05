package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import eightloop.com.a101sandwiches.adapters.SandwichListAdapter;
import eightloop.com.a101sandwiches.constants.AppConstants;
import eightloop.com.a101sandwiches.database.SandwichManager;
import eightloop.com.a101sandwiches.helpers.GeneralHelperMethods;
import eightloop.com.a101sandwiches.models.Sandwich;
import eightloop.com.a101sandwiches.sharedprefs.AppPreferenceHandler;

/**
 * Created by Harshavardhan
 */
public class SandwichListFragment extends Fragment implements SandwichListAdapter.MoveSandwichListener{

    public static final String TAG = "SandwichListFragment";

    View sandwichListFragView;
    Toolbar toolbar_sandwichList;
    RecyclerView recView_sandwichListItem;
    ImageView iv_loadSearch;

    Button bt_tryNow;

    SandwichManager sandwichManager;
    SandwichListAdapter sandwichListAdapter;
    List<Sandwich> allSandwiches;

    TextView tv_currListDisplayed;

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
        tv_currListDisplayed = (TextView) sandwichListFragView.findViewById(R.id.fsl_tv_header_text);
        iv_loadSearch = (ImageView) toolbar_sandwichList.findViewById(R.id.fsl_iv_action_search);

        sandwichManager = new SandwichManager(getActivity());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext()
                , LinearLayoutManager.HORIZONTAL, false);
        recView_sandwichListItem.setLayoutManager(layoutManager);
        recView_sandwichListItem.setItemAnimator(new DefaultItemAnimator());
        recView_sandwichListItem.setHasFixedSize(true);

        Bundle bundle = getArguments();
        if(bundle == null)
        {
            getAllSandwiches();
        }
        else
        {
            Log.e(TAG, "Came Here");
            manageArguments(bundle.getString(AppConstants.FROM_INTRO_FRAG));
        }

        iv_loadSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return sandwichListFragView;
    }

    @Override
    public void moveToPosition(int postion) {
        recView_sandwichListItem.smoothScrollToPosition(postion);
    }

    public void getFavSandwiches()
    {
        List<Sandwich> favSandwich = sandwichManager.getFavSandwiches();
        sandwichListAdapter = new SandwichListAdapter(getActivity(), favSandwich, this);
        recView_sandwichListItem.setAdapter(sandwichListAdapter);
        tv_currListDisplayed.setText(getString(R.string.favourites));
        AppPreferenceHandler appPreferenceHandler = new AppPreferenceHandler(getActivity());
        appPreferenceHandler.setCurrentListFavourite(true);
    }

    public void getAllSandwiches()
    {
        allSandwiches = sandwichManager.getAllSandwiches();
        sandwichListAdapter = new SandwichListAdapter(getActivity(), allSandwiches, this);
        recView_sandwichListItem.setAdapter(sandwichListAdapter);
        tv_currListDisplayed.setText(getString(R.string.all_sandwiches));
        AppPreferenceHandler appPreferenceHandler = new AppPreferenceHandler(getActivity());
        appPreferenceHandler.setCurrentListFavourite(false);
    }

    public void getClassifiedSandwiches(String type)
    {
        allSandwiches = sandwichManager.getClassifiedSandwiches(type);
        sandwichListAdapter = new SandwichListAdapter(getActivity(), allSandwiches, this);
        recView_sandwichListItem.setAdapter(sandwichListAdapter);
        tv_currListDisplayed.setText(type);
        AppPreferenceHandler appPreferenceHandler = new AppPreferenceHandler(getActivity());
        appPreferenceHandler.setCurrentListFavourite(true);
    }

    public int getCount()
    {
        return sandwichListAdapter.getItemCount();
    }

    public void manageArguments(String item)
    {
        Log.e(TAG, "Item: " +item);
        switch (item)
        {
            case "Home":
                Log.e(TAG, "Came to home");
                getAllSandwiches();
                break;
            case "Surprise Me":
                getAllSandwiches();
                moveToPosition(new Random().nextInt(101));
                break;
            case "Favourites":
                getFavSandwiches();
                break;
            case "Classic":
                getClassifiedSandwiches("Classic");
                break;
            case "Luxe":
                getClassifiedSandwiches("Luxe");
                break;
            case "Spice":
                getClassifiedSandwiches("Spice");
                break;
            case "Guilty":
                getClassifiedSandwiches("Guilty");
                break;
            case "Sweet":
                getClassifiedSandwiches("Sweet");
                break;
            case "Veggies":
                getClassifiedSandwiches("Veggies");
                break;
        }
    }
}
