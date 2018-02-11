package tkachuk.com.weatherapp.util;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;

public class JSONParser {

    public static <T>Object getFromJSONtoObject (String jsonString, Type type) {
        if(!isValid(jsonString))
            return null;
        return new Gson().fromJson(jsonString, type);
    }

    private static boolean isValid(String jsonString) {
        try{
            new JsonParser().parse(jsonString);
            return true;
        }catch (JsonSyntaxException e){
            return false;
        }
    }
}
