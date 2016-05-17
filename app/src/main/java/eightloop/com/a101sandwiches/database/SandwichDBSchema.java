package eightloop.com.a101sandwiches.database;

/**
 * Created by Harshavardhan on 5/17/2016.
 */
public class SandwichDBSchema {

    public static final class SandwichTable
    {
        public static final String NAME = "Sandwich";

        public static final class Cols
        {
            public static final String ID = "_id";
            public static final String NAME = "name";
            public static final String IMAGE_NAME = "image_name";
            public static final String TYPE = "type";
            public static final String COOK_TIME = "cooking_time";
            public static final String CALORIES = "calories";
            public static final String IS_FAV = "is_favourite";
            public static final String INGREDIENTS = "ingredients";
            public static final String DIRECTIONS = "directions";
            public static final String SUBHEADING = "subheading";
            public static final String DESCRIPTION = "description";
            public static final String EXTRA1 = "extra1";
            public static final String EXTRA2 = "extra2";
            public static final String EXTRA3 = "extra3";
            public static final String EXTRA4 = "extra4";
            public static final String EXTRA5 = "extra5";

        }
    }
}
