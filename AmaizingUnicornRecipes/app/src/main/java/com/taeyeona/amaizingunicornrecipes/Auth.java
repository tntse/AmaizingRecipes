package com.taeyeona.amaizingunicornrecipes;

/**
 * Created by Chau on 9/23/2015.
 */
public class Auth {

    //http://food2fork.com/api/get?key=50ad55b48d8dbd791d8b69af229adeca
    // key numero dos: f2f a5b90f9d11f5453c04fc97ec2789ab79
    // thomas's f2f key: 87c3746156e104d5055e4cca1a6e9042
    // thomas f2f key numero dos: 672caa0de2a18eb25bbb3ecccfccd0df
    public static final String DEVELOPER_KEY = "AIzaSyAgluXYn35S2cVNEhiT07qGwN6B2uz7kyk";
    public static final String F2F_Key = "87c3746156e104d5055e4cca1a6e9042";
    //"50ad55b48d8dbd791d8b69af229adeca";
    //"a5b90f9d11f5453c04fc97ec2789ab79";
    //"672caa0de2a18eb25bbb3ecccfccd0df";

    public static final String MAPS_KEY = "AIzaSyC9ZRlRXJVAxq8GjSHT2lMgVGwgcHPtmx4";
    public static final String YOUTUBE_DEV_KEY = "AIzaSyA6Gt5_Mxs9U9GZ3jo0m3HZdzdW4dmDafI";
    public static final String GET_URL = "http://food2fork.com/api/get";
    public static final String URL = "http://food2fork.com/api/search";
    public static final String EDAMAM_ID =
    //"a34c5609";
    //"8486dac9";
    "998bb871";

    public static final String EDAMAM_KEY =
    //"a7e44254cc97399430022e739322e13c";
    "ec083b120d2a28bc363bba3eb5b85880";
    public static final String EDAMAM_URL = "https://api.edamam.com/search";
    public static final String CHAR_QUESTION = "?";
    public static final String CHAR_EQUALS = "=";
    public static final String STRING_KEY = "key";
    public static final String CHAR_AND = "&";
    public static final String CHAR_Q = "q";
    public static final String URL_MAPS = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    public static final String LOCATION = "location";
    public static final String LAT_LNG = "37.7, -122.44";
    public static final String RADIUS = "2816"; // roughly 1.75 miles
    public static final String SENSOR = "true";
    public static final String TYPES ="grocery_or_supermarket";
    public static final String PART_SNIPPET = "part=snippet";

    public static final String SHARED_PREFS_KEY = "AmaizingPrefs";

    public interface endpointRecipe{
        public static final String KEY_RECIPES = "recipes";
        public static final String KEY_RECIPE = "recipe";
        public static final String KEY_TITLE = "title";
        public static final String KEY_PUBLISHER = "publisher";
        public static final String KEY_F2F_URL = "f2f_url";
        public static final String KEY_F2FID= "recipe_id";
        public static final String KEY_SOURCE_URL= "source_url";
        public static final String KEY_SOCIAL_RANK= "social_rank";
        public static final String KEY_PUBLISHER_URL= "publisher_url";
        public static final String KEY_id = "id";
        public static final String KEY_items = "items";
        public static final String KEY_VideoId = "videoId";
        public static final String KEY_IMAGE_URL = "image_url";
        public static final String KEY_INGREDIENTS = "ingredients";
        public static final String HITS = "hits";

    }

}
