package eightloop.com.a101sandwiches.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Harshavardhan on 5/17/2016.
 */
public class SandwichDBHelper extends SQLiteAssetHelper {

    public static final String TAG = "SandwichDBHelper";

    public static SandwichDBHelper sandwichDBHelperInstance;

    public static final String DB_NAME = "sandwiches.db";
    public static final int DB_VERSION = 1;

    public static synchronized SandwichDBHelper getInstance(Context context)
    {
        if(sandwichDBHelperInstance == null)
        {
            sandwichDBHelperInstance = new SandwichDBHelper(context);
        }
        return sandwichDBHelperInstance;
    }

    private SandwichDBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
