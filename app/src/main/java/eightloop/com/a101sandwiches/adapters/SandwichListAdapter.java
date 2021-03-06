package eightloop.com.a101sandwiches.adapters;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import eightloop.com.a101sandwiches.IntroActivity;
import eightloop.com.a101sandwiches.R;
import eightloop.com.a101sandwiches.models.Sandwich;

/**
 * Created on 5/21/2016.
 */
public class SandwichListAdapter extends RecyclerView.Adapter<SandwichListAdapter.SandwichViewHolder> {

    public static final String TAG = "SandwichListAdapter";

    List<Sandwich> sandwiches;
    Context mContext;
    MoveSandwichListener moveSandwichListener;
    static Typeface tf_quicksandbold;
    static Resources r;

    public interface OnClickLoadSandwichDetails
    {
        void loadSandwichDetails(Sandwich sandwich);
    }

    public interface MoveSandwichListener
    {
        void moveToPosition(int postion, boolean smoothScroll);
    }

    public SandwichListAdapter(Context context, List<Sandwich> sandwichList, MoveSandwichListener moveSandwichListener)
    {
        this.mContext = context;
        r = mContext.getResources();
        this.sandwiches = sandwichList;
        this.moveSandwichListener = moveSandwichListener;
        tf_quicksandbold = Typeface.createFromAsset(context.getAssets(), "fonts/quicksandbold.otf");
    }

    public static class SandwichViewHolder extends RecyclerView.ViewHolder
    {
        CardView card_sandwichView;

        TextView tv_sandwichNumber;
        TextView tv_sandwichName;
        TextView tv_cookingTime;

        ImageView iv_sandwichImage;
        Button bt_tryItNow;

        ImageButton ibt_moveRight;
        ImageButton ibt_moveLeft;


        public SandwichViewHolder(View view)
        {
            super(view);
            card_sandwichView = (CardView) view.findViewById(R.id.fsl_cv_sandwich_list);
            tv_sandwichNumber = (TextView) view.findViewById(R.id.fsl_cv_tv_sandwich_number);
            tv_sandwichName = (TextView) view.findViewById(R.id.fsl_cv_tv_sandwich_name);
            tv_sandwichName.setTypeface(tf_quicksandbold);
            tv_cookingTime = (TextView) view.findViewById(R.id.fsl_cv_tv_cooking_time);
            iv_sandwichImage = (ImageView) view.findViewById(R.id.fsl_cv_iv_sandwich_image);
            bt_tryItNow = (Button) view.findViewById(R.id.fsl_cv_bt_try_now);
            ibt_moveLeft = (ImageButton) view.findViewById(R.id.fsl_cv_ibt_go_left);
            ibt_moveRight = (ImageButton) view.findViewById(R.id.fsl_cv_ibt_go_right);

            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
            {
                if(r.getDisplayMetrics().density <= 3.0)
                {
                    RecyclerView.LayoutParams clp = (RecyclerView.LayoutParams) card_sandwichView.getLayoutParams();
                    clp.bottomMargin = 0;
                    clp.height = RecyclerView.LayoutParams.MATCH_PARENT;

                    card_sandwichView.setLayoutParams(clp);

                    tv_sandwichName.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, r.getDisplayMetrics()));

                    Log.e(TAG, "Came here");

                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) bt_tryItNow.getLayoutParams();
                    lp.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
                    lp.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
                    bt_tryItNow.setLayoutParams(lp);
                }
            }
            /*bt_tryItNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(context instanceof IntroActivity)
                    {
                        (OnClickLoadSandwichDetails)((IntroActivity) context).loadSandwichDetails();
                    }
                }
            });*/
        }
    }

    @Override
    public SandwichViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sandwich_item, parent, false);
        return new SandwichViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SandwichViewHolder holder, final int position) {
        final Sandwich sandwich = sandwiches.get(position);

        String format = String.format(Locale.ENGLISH, "%d/%d", position + 1, getItemCount());
        String cookingTime = String.format("%s minutes", sandwich.getCookingTime());
        int id = mContext.getResources().getIdentifier(sandwich.getImageName(), "drawable", mContext.getPackageName());
        //Log.e(TAG, "Sandwich Name: " +sandwich.getName());
        Drawable sandwichImage = ContextCompat.getDrawable(mContext, id);
        holder.tv_sandwichNumber.setText(format);
        holder.tv_sandwichName.setText(sandwich.getName());
        holder.tv_cookingTime.setText(cookingTime);
        //holder.iv_sandwichImage.setImageDrawable(sandwichImage);
        Picasso.with(mContext).load(id).into(holder.iv_sandwichImage);
        holder.bt_tryItNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof OnClickLoadSandwichDetails)
                {
                    ((OnClickLoadSandwichDetails) mContext).loadSandwichDetails(sandwich);
                }
            }
        });

        holder.ibt_moveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currPositon = holder.getAdapterPosition();
                if(currPositon == 0)
                {
                    currPositon = getItemCount() - 1;
                    moveSandwichListener.moveToPosition(currPositon, false);
                }
                else
                {
                    currPositon = currPositon - 1;
                    moveSandwichListener.moveToPosition(currPositon, true);
                }

            }
        });

        holder.ibt_moveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currPositon = holder.getAdapterPosition();
                if(currPositon == getItemCount() - 1)
                {
                    currPositon = 0;
                    moveSandwichListener.moveToPosition(currPositon, false);
                }
                else
                {
                    currPositon = currPositon + 1;
                    moveSandwichListener.moveToPosition(currPositon, true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sandwiches.size();
    }
}
