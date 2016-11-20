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
 * Created on 9/17/2016.
 */
public class SplashScreenFragment extends Fragment {
    
    public static final String TAG = "SplashScreenFragment";

    View splashView;

    TextView tv_appName;

    Typeface tf_pacifico;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        splashView = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        tv_appName = (TextView) splashView.findViewById(R.id.fss_tv_appName);

        tf_pacifico = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pacifico.ttf");

        tv_appName.setTypeface(tf_pacifico);

        return splashView;
    }
}
