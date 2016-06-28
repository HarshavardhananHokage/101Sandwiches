package eightloop.com.a101sandwiches.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eightloop.com.a101sandwiches.R;
import eightloop.com.a101sandwiches.models.Sandwich;

/**
 * Created on 6/24/2016.
 */
public class SearchItemAdapter extends BaseAdapter implements Filterable{

    public static final String TAG = "SearchItemAdapter";

    Activity activity;
    List<Sandwich> sandwiches;
    List<Sandwich> originalData;
    LayoutInflater layoutInflater;
    SandwichFilter sandwichFilter;

    public SearchItemAdapter(Activity activity, List<Sandwich> sandwichList)
    {
        this.activity = activity;
        this.sandwiches = sandwichList;
        this.originalData = sandwichList;
        sandwichFilter = new SandwichFilter();
    }

    @Override
    public int getCount() {
        return sandwiches.size();
    }

    @Override
    public Sandwich getItem(int position) {
        return sandwiches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater == null)
        {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.layout_search_item, parent, false);
        }

        TextView tv_sandwichName = (TextView) convertView.findViewById(R.id.lsi_tv_sandwich_name);
        TextView tv_cookingTime = (TextView) convertView.findViewById(R.id.lsi_tv_cooking_time);

        Sandwich sandwich = getItem(position);

        tv_sandwichName.setText(sandwich.getName());
        String cookingTime = String.format("Cooking time: %s min", sandwich.getCookingTime());
        tv_cookingTime.setText(cookingTime);
        return convertView;
    }

    private class SandwichFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String filterString = constraint.toString().toLowerCase();
            List<Sandwich> searchList = new ArrayList<>();
            List<Sandwich> originalList = originalData;

            int count = originalList.size();

            if(constraint != null && constraint.length() > 0)
            {
                for (int i = 0; i < count; i++) {
                    String name = originalList.get(i).getName();
                    if (name.toLowerCase().contains(filterString)) {
                        searchList.add(originalList.get(i));
                    }
                }
                results.count = searchList.size();
                results.values = searchList;
            }
            else
            {
                results.count = originalList.size();
                results.values = originalList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            sandwiches = (List<Sandwich>) results.values;
            notifyDataSetChanged();
        }
    }

    @Override
    public Filter getFilter() {
        return sandwichFilter;
    }
}
