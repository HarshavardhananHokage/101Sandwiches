package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created on 9/21/2016.
 */

public class AppGuideIntroFragment extends Fragment {

    public static final String TAG = "AppGuideIntroFragment";

    View view;

    TextView tv_mainText;
    TextView tv_subText;

    Typeface type_bariol;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_app_guide_1_intro, container, false);

        tv_mainText = (TextView) view.findViewById(R.id.fag1_tv_mainText);
        tv_subText = (TextView) view.findViewById(R.id.fag1_tv_subText);

        type_bariol = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bariol.otf");

        tv_mainText.setTypeface(type_bariol);
        tv_subText.setTypeface(type_bariol);

        return view;
    }
}
