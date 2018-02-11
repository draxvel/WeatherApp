package tkachuk.com.weatherapp.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

        @GET("weather?lang=ua&units=metric&APPID=1aae2de56172990cf3344707b3e0aa8b")
        Call<JsonObject> getWeather (@Query("q") String city);

        @GET("forecast?lang=ua&units=metric&APPID=1aae2de56172990cf3344707b3e0aa8b")
        Call<JsonObject> getForecast (@Query("q") String city);

}
