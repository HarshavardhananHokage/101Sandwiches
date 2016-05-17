package eightloop.com.a101sandwiches.helpers;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import eightloop.com.a101sandwiches.database.SandwichDBHelper;

/**
 * Created by Harshavardhan on 5/17/2016.
 */
public class CopyDBFromAssets {

    public static final String TAG = "CopyDBFromAssets";

    public static String DB_PATH = "";
    public static final String DB_NAME = "sandwiches.db";

    Context mContext;

    public CopyDBFromAssets(Context context)
    {
        this.mContext = context;
        DB_PATH = "/data/data/"+context.getPackageName()+"/"+"databases/";
    }


    public void copyDBFromAsset()
    {
        try {
            InputStream myInput = mContext.getAssets().open(SandwichDBHelper.DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        }catch (IOException ioe)
        {
            Log.e(TAG, "copyDBFromAsset: Database Copy Error", ioe);
        }
    }
}
