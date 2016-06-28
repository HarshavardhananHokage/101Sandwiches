package eightloop.com.a101sandwiches.sharedprefs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created on 6/19/2016.
 */
public class AppPreferenceHandler {
    
    public static final String TAG = "AppPreferenceHandler";
    private SharedPreferenceManager sharedPreferenceManager;

    public AppPreferenceHandler(Context context)
    {
        sharedPreferenceManager = SharedPreferenceManager.getInstance(context);
    }

    public boolean isCurrentListFavourite()
    {
        return sharedPreferenceManager.getBoolPreference(SharedPrefConstants.IS_LIST_FAVOURITE);
    }

    public void setCurrentListFavourite(boolean value)
    {
        sharedPreferenceManager.setBoolPreference(SharedPrefConstants.IS_LIST_FAVOURITE, value);
    }
}
