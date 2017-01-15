package eightloop.com.a101sandwiches;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import eightloop.com.a101sandwiches.adapters.SearchItemAdapter;
import eightloop.com.a101sandwiches.database.SandwichManager;
import eightloop.com.a101sandwiches.models.Sandwich;

/**
 * Created on 6/24/2016.
 */
public class SearchViewFragment extends Fragment {

    public static final String TAG = "SearchViewFragment";

    View view;

    ListView lv_sandwichList;
    SearchItemAdapter searchItemAdapter;
    SandwichManager sandwichManager;

    EditText et_searchText;
    ImageView iv_closeSearch;

    LoadSelectedSandwichFromSearch selectedSandwichFromSearch;

    public interface LoadSelectedSandwichFromSearch
    {
        void loadSandwich(Sandwich sandwich);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_view, container, false);

        lv_sandwichList = (ListView) view.findViewById(R.id.as_lv_searched_items);
        et_searchText = (EditText) view.findViewById(R.id.as_et_search_text);
        iv_closeSearch = (ImageView) view.findViewById(R.id.as_iv_close_search);

        sandwichManager = new SandwichManager(getActivity());
        searchItemAdapter = new SearchItemAdapter(getActivity(), sandwichManager.getAllSandwiches());

        lv_sandwichList.setAdapter(searchItemAdapter);

        et_searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchItemAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        iv_closeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        lv_sandwichList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), 0);

                Sandwich selectedSandwich = searchItemAdapter.getItem(position);
                ((LoadSelectedSandwichFromSearch) getActivity()).loadSandwich(selectedSandwich);
            }
        });

        return view;
    }
}
