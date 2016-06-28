package eightloop.com.a101sandwiches.helpers;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import eightloop.com.a101sandwiches.constants.AppConstants;

/**
 * Created on 6/24/2016.
 */
public class GeneralHelperMethods {
    
    public static final String TAG = "GeneralHelperMethods";

    public static void rateAppAtPlayStore(Context context)
    {
        try {
            Uri uri = Uri.parse("market://details?id="+ AppConstants.PACKAGE_NAME);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }catch (ActivityNotFoundException anfe)
        {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+ AppConstants.PACKAGE_NAME);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    public static void otherAppsByDeveloper(Context context)
    {
        try {
            Uri uri = Uri.parse("market://dev?id="+ AppConstants.DEVELOPER_NAME);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }catch (ActivityNotFoundException anfe)
        {
            Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id="+ AppConstants.DEVELOPER_NAME);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }
}
