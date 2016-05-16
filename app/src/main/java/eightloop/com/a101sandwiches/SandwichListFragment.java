package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Harshavardhan on 5/16/2016.
 */
public class SandwichListFragment extends Fragment {

    public static final String TAG = "SandwichListFragment";

    View sandwichListFragView;
    Toolbar toolbar_sandwichList;

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

        return sandwichListFragView;
    }
}
