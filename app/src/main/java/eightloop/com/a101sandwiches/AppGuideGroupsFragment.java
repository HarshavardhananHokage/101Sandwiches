package eightloop.com.a101sandwiches;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import eightloop.com.a101sandwiches.interfaces.AppGuideCallbackInterface;

/**
 * Created on 9/21/2016.
 */

public class AppGuideGroupsFragment extends Fragment {

    public static final String TAG = "AppGuideGroupsFragment";

    View view;

    TextView tv_mainText;
    TextView tv_subText;

    Typeface type_bariol;

    Button bt_next;
    Button bt_skip;

    AppGuideCallbackInterface appGuideCallbackInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_app_guide_2_groups, container, false);

        tv_mainText = (TextView) view.findViewById(R.id.fag2_tv_mainText);
        tv_subText = (TextView) view.findViewById(R.id.fag2_tv_subText);

        type_bariol = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bariol.otf");

        tv_mainText.setTypeface(type_bariol);
        tv_subText.setTypeface(type_bariol);


        bt_next = (Button) view.findViewById(R.id.fag2_ll_bt_next);
        bt_skip = (Button) view.findViewById(R.id.fag2_ll_bt_skip);

        bt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appGuideCallbackInterface.skipCalled(2);
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appGuideCallbackInterface.nextCalled(2);
            }
        });


        return view;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof AppGuideActivity)
        {
            try {
                appGuideCallbackInterface = (AppGuideCallbackInterface) activity;
            }catch (ClassCastException cce)
            {
                Log.e(TAG, "Cannot cast activity", cce);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "Came Here");

        if(context instanceof AppGuideActivity)
        {
            try {
                appGuideCallbackInterface = (AppGuideCallbackInterface) context;
            }catch (ClassCastException cce)
            {
                Log.e(TAG, "Cannot cast activity", cce);
            }
        }
    }
}
