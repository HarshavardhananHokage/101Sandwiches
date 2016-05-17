package eightloop.com.a101sandwiches.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import eightloop.com.a101sandwiches.models.Sandwich;
import eightloop.com.a101sandwiches.database.SandwichDBSchema.SandwichTable;

/**
 * Created by Harshavardhan on 5/17/2016.
 */
public class SandwichCursorWrapper extends CursorWrapper {

    public static final String TAG = "SandwichCursorWrapper";

    public SandwichCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Sandwich getSandwich()
    {
        int id = getInt(getColumnIndex(SandwichTable.Cols.ID));
        int isFav = getInt(getColumnIndex(SandwichTable.Cols.IS_FAV));

        String name = getString(getColumnIndex(SandwichTable.Cols.NAME));
        String imageName = getString(getColumnIndex(SandwichTable.Cols.IMAGE_NAME));
        String type = getString(getColumnIndex(SandwichTable.Cols.TYPE));
        String cookTime = getString(getColumnIndex(SandwichTable.Cols.COOK_TIME));
        String calories = getString(getColumnIndex(SandwichTable.Cols.CALORIES));
        String ingredients = getString(getColumnIndex(SandwichTable.Cols.INGREDIENTS));
        String directions = getString(getColumnIndex(SandwichTable.Cols.DIRECTIONS));
        String subheading = getString(getColumnIndex(SandwichTable.Cols.SUBHEADING));
        String description = getString(getColumnIndex(SandwichTable.Cols.DESCRIPTION));

        Sandwich sandwich = new Sandwich();
        sandwich.setId(id);
        sandwich.setIsFavourite(isFav);
        sandwich.setName(name);
        sandwich.setImageName(imageName);
        sandwich.setType(type);
        sandwich.setCookingTime(cookTime);
        sandwich.setCalorieCount(calories);
        sandwich.setIngredients(ingredients);
        sandwich.setDirections(directions);
        sandwich.setSubheading(subheading);
        sandwich.setDescription(description);

        return sandwich;

    }
}
