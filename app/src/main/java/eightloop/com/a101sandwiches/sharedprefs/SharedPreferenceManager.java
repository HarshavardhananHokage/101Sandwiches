package eightloop.com.a101sandwiches.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created on 6/19/2016.
 */
public class SharedPreferenceManager {
    
    public static final String TAG = "SharedPreferenceManager";

    private static SharedPreferenceManager sharedPreferenceManager = null;
    private static SharedPreferences sharedPreferences = null;

    private SharedPreferenceManager(SharedPreferences sharedPrefs)
    {
        sharedPreferences = sharedPrefs;
    }

    public static SharedPreferenceManager getInstance(Context context)
    {
        if(sharedPreferenceManager == null)
        {
            sharedPreferences = context.getSharedPreferences(SharedPrefConstants.SANDWICH_PREFS, Context.MODE_PRIVATE);
            sharedPreferenceManager = new SharedPreferenceManager(sharedPreferences);
        }
        return sharedPreferenceManager;
    }

    public void removePreference(String key)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void setBoolPreference(String key, boolean value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void setStringPreference(String key, String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setIntPreference(String key, int value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setLongPreference(String key, long value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void setFloatPreference(String key, float value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public boolean getBoolPreference(String key)
    {
        return sharedPreferences.getBoolean(key, false);
    }

    public int getIntPreference(String key)
    {
        return sharedPreferences.getInt(key, 0);
    }

    public long getLongPreference(String key)
    {
        return sharedPreferences.getLong(key, 0);
    }

    public float getFloatPreference(String key)
    {
        return sharedPreferences.getFloat(key, 0f);
    }

    public String getStringPreference(String key)
    {
        return sharedPreferences.getString(key, "");
    }
}
