package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import eightloop.com.a101sandwiches.database.SandwichManager;
import eightloop.com.a101sandwiches.models.Sandwich;

/**
 * Created by Harshavardhan on 5/16/2016.
 */
public class SandwichListFragment extends Fragment {

    public static final String TAG = "SandwichListFragment";

    View sandwichListFragView;
    Toolbar toolbar_sandwichList;

    Button bt_tryNow;

    TextView tv_sandwichNumber;
    TextView tv_SandwichName;
    TextView tv_cookingTime;
    ImageView iv_sandwichImage;

    SandwichManager sandwichManager;
    List<Sandwich> allSandwiches;

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
        }

        bt_tryNow = (Button) sandwichListFragView.findViewById(R.id.fsl_cv_bt_try_now);
        tv_SandwichName = (TextView) sandwichListFragView.findViewById(R.id.fsl_cv_tv_sandwich_name);
        tv_cookingTime = (TextView) sandwichListFragView.findViewById(R.id.fsl_cv_tv_cooking_time);
        tv_sandwichNumber = (TextView) sandwichListFragView.findViewById(R.id.fsl_cv_tv_sandwich_number);
        iv_sandwichImage = (ImageView) sandwichListFragView.findViewById(R.id.fsl_cv_iv_sandwich_image);

        sandwichManager = new SandwichManager(getActivity());
        allSandwiches = sandwichManager.getAllSandwiches();

        Sandwich sandwich = allSandwiches.get(0);

        String sCookTime = String.format("%s minutes", sandwich.getCookingTime());
        String sSandNumber = String.format(Locale.ENGLISH, "%d/101", sandwich.getId());

        tv_SandwichName.setText(sandwich.getName());
        tv_cookingTime.setText(sCookTime);
        tv_sandwichNumber.setText(sSandNumber);

        int imgID = getResources().getIdentifier(sandwich.getImageName(), "drawable", getActivity().getPackageName());

        iv_sandwichImage.setImageDrawable(ContextCompat.getDrawable(getActivity(), imgID));

        return sandwichListFragView;
    }
}
