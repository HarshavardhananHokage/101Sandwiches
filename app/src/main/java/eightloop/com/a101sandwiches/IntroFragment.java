package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Locale;

import eightloop.com.a101sandwiches.constants.AppConstants;
import eightloop.com.a101sandwiches.database.SandwichManager;
import eightloop.com.a101sandwiches.models.Sandwich;

/**
 * Created by Harshavardhan
 */
public class IntroFragment extends Fragment {

    public static final String TAG = "IntroFragment";

    View introFragView;
    Toolbar introToolbar;
    ViewStub vs_ingDirStub;

    SandwichManager sandwichManager;

    AdView adView_introPage;
    AdRequest adRequest_introPage;

    Button bt_ingredients;
    Button bt_directions;
    Button bt_lid_ingredients_list;
    Button bt_lid_directions_list;


    TextView tv_sandwichName;
    TextView tv_sandwichSubheading;
    TextView tv_sandwichDescription;
    TextView tv_sandwichCookingTime;
    TextView tv_sandwichCalorieCount;
    TextView tv_toolbarTitle;

    ImageView iv_sandwichImage;
    ImageView iv_sandwichFavourite;

    RelativeLayout rl_detailsPane;
    RelativeLayout rl_contentLayout;
    LinearLayout ll_ing_directions_lists;

    String[] ingredients;
    String[] directions;

    ImageButton ib_closeList;
    boolean isStubInflated = false;
    boolean isLastViewIngredients = false;
    boolean isSandwichFav = false;

    Sandwich sandwich;

    Typeface tf_quicksandbold;

    ArrayList<Integer> selectedCheckBoxes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        introFragView = inflater.inflate(R.layout.fragment_sandwich_intro, container, false);

        sandwich = (Sandwich) getArguments().getSerializable(AppConstants.SANDWICH);
        introToolbar = (Toolbar) introFragView.findViewById(R.id.fsi_toolbar_custom);
        rl_contentLayout = (RelativeLayout) introFragView.findViewById(R.id.fsi_rl_content_layout);
        vs_ingDirStub = (ViewStub) introFragView.findViewById(R.id.fsi_vs_ing_directions_stub);

        bt_ingredients = (Button) introFragView.findViewById(R.id.fsi_bt_ingredients);
        bt_directions = (Button) introFragView.findViewById(R.id.fsi_bt_directions);

        iv_sandwichImage = (ImageView) introFragView.findViewById(R.id.fsi_iv_sandwich_image);
        tv_sandwichName = (TextView) introFragView.findViewById(R.id.fsi_tv_sandwich_name);
        tv_sandwichSubheading = (TextView) introFragView.findViewById(R.id.fsi_tv_sandwich_subheading);
        tv_sandwichDescription = (TextView) introFragView.findViewById(R.id.fsi_tv_sandwich_desc);
        tv_sandwichCookingTime = (TextView) introFragView.findViewById(R.id.fsi_tv_cooking_time);
        tv_sandwichCalorieCount = (TextView) introFragView.findViewById(R.id.fsi_tv_calories_count);

        iv_sandwichFavourite = (ImageView) introToolbar.findViewById(R.id.fsi_iv_sandwich_favourite);

        selectedCheckBoxes = new ArrayList<>();
        sandwichManager = new SandwichManager(getActivity());

        setBasicDetails();

        //rl_detailsPane.setVisibility(View.GONE);
        introToolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(introToolbar);
        try
        {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_bars));
        }catch (NullPointerException npe)
        {
            Log.e(TAG, "Toolbar is returned as null");
        }

        if(getActivity() instanceof SearchActivity)
        {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ImageView iv_closeActivity = (ImageView) introToolbar.findViewById(R.id.fsi_iv_cancel_searched);
            iv_closeActivity.setVisibility(View.VISIBLE);
            iv_closeActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });

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
                startListDisplayAnimation(true);
            }
        });

        bt_directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListDisplayAnimation(false);
            }
        });

        iv_sandwichFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSandwichFav)
                {
                    isSandwichFav = false;
                    iv_sandwichFavourite.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_heart));
                    sandwichManager.updateFavourite(sandwich.getId(), 0);
                }
                else
                {
                    isSandwichFav = true;
                    iv_sandwichFavourite.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_hearts_red));
                    sandwichManager.updateFavourite(sandwich.getId(), 1);
                }
            }
        });

        tv_toolbarTitle = (TextView) introToolbar.findViewById(R.id.fsi_toolbar_text);

        tf_quicksandbold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/quicksandbold.otf");

        tv_toolbarTitle.setTypeface(tf_quicksandbold);

        return introFragView;
    }

    public void setBasicDetails()
    {
        String packageName = getActivity().getPackageName();
        tv_sandwichName.setText(sandwich.getName());

        int id = getResources().getIdentifier(sandwich.getImageName(), "drawable", packageName);
        iv_sandwichImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), id));

        String subHeading = getString(getResources().getIdentifier(sandwich.getSubheading(), "string", packageName));
        tv_sandwichSubheading.setText(subHeading);

        String description = getString(getResources().getIdentifier(sandwich.getDescription(), "string", packageName));
        tv_sandwichDescription.setText(description);

        String cookingTime = String.format("%s minutes", sandwich.getCookingTime());
        String calorieCount = String.format("%s calories", sandwich.getCalorieCount());

        tv_sandwichCookingTime.setText(cookingTime);
        tv_sandwichCalorieCount.setText(calorieCount);

        if(sandwich.getIsFavourite() == 1)
        {
            isSandwichFav = true;
            iv_sandwichFavourite.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_action_hearts_red));
        }
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
                if(isLastViewIngredients)
                {
                    selectedCheckBoxes.clear();
                    for (int i = 0; i <ll_ing_directions_lists.getChildCount(); i++)
                    {
                        CheckBox checkBox = (CheckBox) ll_ing_directions_lists.getChildAt(i);
                        if(checkBox.isChecked())
                        {
                            selectedCheckBoxes.add(i);
                        }
                    }
                }
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
                inflateViewStub();
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

        int ingArrayId = getResources().getIdentifier(sandwich.getIngredients(), "array", getActivity().getPackageName());
        ingredients = getResources().getStringArray(ingArrayId);
        //recipes = getResources().getStringArray(R.array.classic_blt_sandwich_rec);
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int checkBoxMargBottom = getActivity().getResources().getInteger(R.integer.checkboxMarginBottom);
        Log.e(TAG, ""+checkBoxMargBottom);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, checkBoxMargBottom, getActivity().getResources().getDisplayMetrics());
        layoutParams.bottomMargin = margin;
        int count = 0;
        for(String ingredient: ingredients)
        {
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(ingredient);
            checkBox.setTextColor(ContextCompat.getColor(getActivity(), R.color.greyishBlack));
            TextViewCompat.setTextAppearance(checkBox, R.style.INgAndDIrTextSize320XHDPI);
            checkBox.setLayoutParams(layoutParams);
            if(selectedCheckBoxes.size() > 0 && selectedCheckBoxes.contains(count))
            {
                checkBox.setChecked(true);
            }
            ll_ing_directions_lists.addView(checkBox);
            count++;
        }
        isLastViewIngredients = true;
    }

    public void fillDirections()
    {
        if(ll_ing_directions_lists.getChildCount() > 0)
        {
            if(isLastViewIngredients)
            {
                selectedCheckBoxes.clear();
                for (int i = 0; i <ll_ing_directions_lists.getChildCount(); i++)
                {
                    CheckBox checkBox = (CheckBox) ll_ing_directions_lists.getChildAt(i);
                    if(checkBox.isChecked())
                    {
                        selectedCheckBoxes.add(i);
                    }
                }
            }

            ll_ing_directions_lists.removeAllViews();
        }

        int dirArrayId = getResources().getIdentifier(sandwich.getDirections(), "array", getActivity().getPackageName());
        directions = getResources().getStringArray(dirArrayId);

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getActivity().getResources().getDisplayMetrics());;

        int count = 1;
        for(String direction: directions)
        {
            String formattedDirection = String.format(Locale.ENGLISH, "%d.) %s", count, direction);
            count++;
            TextView textView = new TextView(getActivity());
            textView.setText(formattedDirection);
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.greyishBlack));
            TextViewCompat.setTextAppearance(textView, R.style.INgAndDIrTextSize320XHDPI);
            textView.setTypeface(null, Typeface.ITALIC);
            textView.setLayoutParams(layoutParams);
            ll_ing_directions_lists.addView(textView);
        }
        isLastViewIngredients = false;
    }
}
