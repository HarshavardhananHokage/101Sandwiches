package eightloop.com.a101sandwiches;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    LinearLayout linearLayout;
    String[] ingredients;
    String[] recipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ingredients = getResources().getStringArray(R.array.classic_blt_sandwich_ing);
        recipes = getResources().getStringArray(R.array.classic_blt_sandwich_dir);

        linearLayout = (LinearLayout) findViewById(R.id.am_ll_main);

        for(String ingredient: ingredients)
        {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(ingredient);
            linearLayout.addView(checkBox);
        }

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setText("STEPS");
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        linearLayout.addView(textView);

        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layoutParams1 =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(margin, margin, margin, margin);

        for(String recipe: recipes)
        {
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(layoutParams1);
            textView1.setText(recipe);
            textView1.setTypeface(null, Typeface.ITALIC);
            linearLayout.addView(textView1);
        }
    }
}
