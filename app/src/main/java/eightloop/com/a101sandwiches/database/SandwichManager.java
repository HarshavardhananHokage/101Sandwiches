package eightloop.com.a101sandwiches.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eightloop.com.a101sandwiches.models.Sandwich;
import eightloop.com.a101sandwiches.database.SandwichDBSchema.SandwichTable;

/**
 * Created by Harshavardhan on 5/17/2016.
 */
public class SandwichManager {

    public static final String TAG = "SandwichManager";

    private Context mContext;
    private SQLiteDatabase mSandwichDatabase;

    List<Sandwich> list_allSandwiches;

    public SandwichManager(Context context)
    {
        this.mContext = context;
        mSandwichDatabase = SandwichDBHelper.getInstance(mContext).getWritableDatabase();
    }

    public SandwichCursorWrapper querySandwiches(String where, String[] whereArgs)
    {
        Cursor cursor = mSandwichDatabase.query(SandwichTable.NAME, null, where, whereArgs, null, null, null);
        return new SandwichCursorWrapper(cursor);
    }

    public List<Sandwich> getAllSandwiches()
    {
        SandwichCursorWrapper sandCursor = querySandwiches(null, null);
        list_allSandwiches = new ArrayList<>();

        try {
            sandCursor.moveToFirst();
            while(!sandCursor.isAfterLast())
            {
                list_allSandwiches.add(sandCursor.getSandwich());
                sandCursor.moveToNext();
            }
        }finally {
            sandCursor.close();
        }

        return list_allSandwiches;
    }

    public List<Sandwich> getFavSandwiches()
    {
        list_allSandwiches = new ArrayList<>();
        String whereClause = SandwichTable.Cols.IS_FAV + "=?";
        String[] whereArgs = {"1"};
        SandwichCursorWrapper sandCursor = querySandwiches(whereClause, whereArgs);

        try
        {
            sandCursor.moveToFirst();
            while(!sandCursor.isAfterLast())
            {
                list_allSandwiches.add(sandCursor.getSandwich());
                sandCursor.moveToNext();
            }
        }finally {
            sandCursor.close();
        }

        return list_allSandwiches;
    }

    public List<Sandwich> getClassifiedSandwiches(String type)
    {
        list_allSandwiches = new ArrayList<>();
        String whereClause = SandwichTable.Cols.EXTRA3 + "=?";;
        String[] whereArgs = new String[]{type};
        SandwichCursorWrapper sandCursor = querySandwiches(whereClause, whereArgs);

        try
        {
            sandCursor.moveToFirst();
            while(!sandCursor.isAfterLast())
            {
                list_allSandwiches.add(sandCursor.getSandwich());
                sandCursor.moveToNext();
            }
        }finally {
            sandCursor.close();
        }

        return list_allSandwiches;
    }

    public void updateFavourite(int sandwichID, int isFavourite)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SandwichTable.Cols.IS_FAV, isFavourite);
        String where = SandwichTable.Cols.ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(sandwichID)};
        mSandwichDatabase.update(SandwichTable.NAME, contentValues, where, whereArgs);
    }
}
