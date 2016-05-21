package eightloop.com.a101sandwiches.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import eightloop.com.a101sandwiches.R;
import eightloop.com.a101sandwiches.models.Sandwich;

/**
 * Created by Harshavardhan on 5/21/2016.
 */
public class SandwichListAdapter extends RecyclerView.Adapter<SandwichListAdapter.SandwichViewHolder> {

    public static final String TAG = "SandwichListAdapter";

    List<Sandwich> sandwiches;
    Context mContext;

    public SandwichListAdapter(Context context, List<Sandwich> sandwichList)
    {
        this.mContext = context;
        this.sandwiches = sandwichList;
    }

    public static class SandwichViewHolder extends RecyclerView.ViewHolder
    {
        CardView card_sandwichView;

        TextView tv_sandwichNumber;
        TextView tv_sandwichName;
        TextView tv_cookingTime;

        ImageView iv_sandwichImage;

        SandwichViewHolder(View view)
        {
            super(view);
            card_sandwichView = (CardView) view.findViewById(R.id.fsl_cv_sandwich_list);
            tv_sandwichNumber = (TextView) view.findViewById(R.id.fsl_cv_tv_sandwich_number);
            tv_sandwichName = (TextView) view.findViewById(R.id.fsl_cv_tv_sandwich_name);
            tv_cookingTime = (TextView) view.findViewById(R.id.fsl_cv_tv_cooking_time);
            iv_sandwichImage = (ImageView) view.findViewById(R.id.fsl_cv_iv_sandwich_image);
        }
    }


    @Override
    public SandwichViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sandwich_item, parent, false);
        return new SandwichViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SandwichViewHolder holder, int position) {
        Sandwich sandwich = sandwiches.get(position);

        String format = String.format(Locale.ENGLISH, "%d/%d", position, getItemCount());
        String cookingTime = String.format("%s minutes", sandwich.getCookingTime());
        int id = mContext.getResources().getIdentifier(sandwich.getImageName(), "drawable", mContext.getPackageName());
        holder.tv_sandwichNumber.setText(format);
        holder.tv_sandwichName.setText(sandwich.getName());
        holder.tv_cookingTime.setText(cookingTime);
    }

    @Override
    public int getItemCount() {
        return sandwiches.size();
    }
}
