package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.w3c.dom.Text;

/**
 * Created by Harshavardhan on 5/13/2016.
 */
public class IntroFragment extends Fragment {

    public static final String TAG = "IntroFragment";

    View introFragView;
    Toolbar introToolbar;
    ViewStub vs_ingDirStub;

    AdView adView_introPage;
    AdRequest adRequest_introPage;

    Button bt_ingredients;
    Button bt_directions;
    Button bt_lid_ingredients_list;
    Button bt_lid_directions_list;

    RelativeLayout rl_detailsPane;
    RelativeLayout rl_contentLayout;
    LinearLayout ll_ing_directions_lists;

    String[] ingredients;
    String[] directions;

    ImageButton ib_closeList;
    boolean isStubInflated = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        introFragView = inflater.inflate(R.layout.fragment_sandwich_intro, container, false);
        introToolbar = (Toolbar) introFragView.findViewById(R.id.fsi_toolbar_custom);
        rl_contentLayout = (RelativeLayout) introFragView.findViewById(R.id.fsi_rl_content_layout);
        vs_ingDirStub = (ViewStub) introFragView.findViewById(R.id.fsi_vs_ing_directions_stub);

        bt_ingredients = (Button) introFragView.findViewById(R.id.fsi_bt_ingredients);
        bt_directions = (Button) introFragView.findViewById(R.id.fsi_bt_directions);

        //rl_detailsPane.setVisibility(View.GONE);
        introToolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(introToolbar);
        try
        {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        }catch (NullPointerException npe)
        {
            Log.e(TAG, "Toolbar is returned as null");
        }


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

        bt_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflateViewStub();
                startListDisplayAnimation(true);
            }
        });

        bt_directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflateViewStub();
                startListDisplayAnimation(false);
            }
        });



        return introFragView;
    }

    public void inflateViewStub()
    {
        if(!isStubInflated)
        {
            vs_ingDirStub.inflate();
            isStubInflated = true;
            rl_detailsPane = (RelativeLayout) introFragView.findViewById(R.id.fsi_lid_rl_layout);
            ll_ing_directions_lists = (LinearLayout) introFragView.findViewById(R.id.fsi_lid_ll_lists);
            bt_lid_directions_list = (Button) introFragView.findViewById(R.id.fsi_lid_bt_directions);
            bt_lid_ingredients_list = (Button) introFragView.findViewById(R.id.fsi_lid_bt_ingredients);
            ib_closeList = (ImageButton) introFragView.findViewById(R.id.fsi_lid_ib_close_list);
        }

        bt_lid_ingredients_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillIngredients();
            }
        });

        bt_lid_directions_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDirections();
            }
        });

        ib_closeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntroDisplayAnimation();
            }
        });
    }

    public void startListDisplayAnimation(final boolean isIngredient) {
        final Animation fadein = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        final Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_contentLayout.setVisibility(View.GONE);
                rl_detailsPane.setVisibility(View.VISIBLE);
                rl_detailsPane.startAnimation(fadein);
                if (isIngredient) {
                    fillIngredients();
                } else {
                    fillDirections();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rl_contentLayout.startAnimation(fadeOut);
    }

    public void startIntroDisplayAnimation() {
        final Animation fadein = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        final Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_detailsPane.setVisibility(View.GONE);
                rl_contentLayout.startAnimation(fadein);
                rl_contentLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rl_detailsPane.startAnimation(fadeOut);
    }

    public void fillIngredients()
    {
        if(ll_ing_directions_lists.getChildCount() > 0)
        {
            ll_ing_directions_lists.removeAllViews();
        }
        ingredients = getResources().getStringArray(R.array.classic_blt_sandwich_ing);
        //recipes = getResources().getStringArray(R.array.classic_blt_sandwich_rec);
        /*LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getActivity().getResources().getDisplayMetrics());
        layoutParams.leftMargin = layoutParams.rightMargin = margin;*/
        for(String ingredient: ingredients)
        {
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(ingredient);
            checkBox.setTextColor(ContextCompat.getColor(getActivity(), R.color.greyishBlack));
            //checkBox.setLayoutParams(layoutParams);
            ll_ing_directions_lists.addView(checkBox);
        }
    }

    public void fillDirections()
    {
        if(ll_ing_directions_lists.getChildCount() > 0)
        {
            ll_ing_directions_lists.removeAllViews();
        }
        directions = getResources().getStringArray(R.array.classic_blt_sandwich_dir);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getActivity().getResources().getDisplayMetrics());
        layoutParams.bottomMargin = margin;

        for(String direction: directions)
        {
            TextView textView = new TextView(getActivity());
            textView.setText(direction);
            textView.setTypeface(null, Typeface.ITALIC);
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.greyishBlack));
            textView.setLayoutParams(layoutParams);
            ll_ing_directions_lists.addView(textView);

            /*RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(direction);
            radioButton.setTypeface(null, Typeface.ITALIC);
            radioButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.greyishBlack));
            radioButton.setLayoutParams(layoutParams);
            ll_ing_directions_lists.addView(radioButton);*/

            /*CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(direction);
            checkBox.setTextColor(ContextCompat.getColor(getActivity(), R.color.greyishBlack));
            checkBox.setTypeface(null, Typeface.ITALIC);
            checkBox.setLayoutParams(layoutParams);
            ll_ing_directions_lists.addView(checkBox);*/
        }
    }
}
