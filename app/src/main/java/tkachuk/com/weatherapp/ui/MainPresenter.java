package tkachuk.com.weatherapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tkachuk.com.weatherapp.api.ApiService;
import tkachuk.com.weatherapp.api.RetroClient;
import tkachuk.com.weatherapp.model.Day;
import tkachuk.com.weatherapp.model.DayList;
import tkachuk.com.weatherapp.util.InternetConnection;
import tkachuk.com.weatherapp.util.JSONParser;

public class MainPresenter implements IMainPresenter{

    private Context context;
    private Activity activity;
    private IMainView view;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public MainPresenter(Activity activity, IMainView view) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.view = view;
    }

    public void loadWeather(String city){
        if (InternetConnection.checkConnection(context)) {

            view.showProgress();

            //Creating an object for our api interface
            ApiService api = RetroClient.getRetroClient();

            //Calling Json
            Call<JsonObject> jsonArrayCallWeather = api.getWeather(city);

            jsonArrayCallWeather.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful())
                    {
                        String responseBodyT = response.body().toString();

                        Type type = new TypeToken<Day>(){}.getType();
                        Day day= (Day) JSONParser.getFromJSONtoObject(responseBodyT, type);

                        view.setToday(day);
                        view.setCity(day.getCity());
                        saveDayResponse(responseBodyT);
                        view.setLastUpdateToSP();
                    }
                    else
                    {
                        view.incorrectCity();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    view.incorrectCity();
                }
            });

            //Calling Json
            Call<JsonObject> jsonArrayCallForecast = api.getForecast(city);

            jsonArrayCallForecast.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful())
                    {
                        String responseBodyTM = response.body().toString();

                        Type type = new TypeToken<DayList>(){}.getType();
                        DayList dayList= (DayList) JSONParser.getFromJSONtoObject(responseBodyTM, type);

                        view.setNextDays(dayList);
                        saveNextDaysResponse(responseBodyTM);
                        view.setLastUpdateToSP();
                    }
                    else
                    {
                        view.incorrectCity();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                }
            });


        } else notInternetAccess();

        view.hideProgress();
        view.hideRefreshing();
    }


    private void notInternetAccess(){
        if(loadDayResponse() &&  loadNextDaysResponse()){
            view.showNotInternetConnectionIsCache();
            view.setLastUpdateToTV(getLastUpdate());
        }
        else view.showNotInternetConnection();
    }

    @Override
    public void saveDayResponse(String response) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("today", response);
        editor.apply();
    }

    @Override
    public boolean loadDayResponse() {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String s = sharedPref.getString("today", "");
        if(!TextUtils.isEmpty(s)) {
            Type type = new TypeToken<Day>() {}.getType();
            Day day = (Day) JSONParser.getFromJSONtoObject(s, type);
            view.setToday(day);
            view.setCity(day.getCity());
            return true;
        }
        else return false;
    }

    @Override
    public void saveNextDaysResponse(String response) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("nextdays", response);
        editor.apply();
    }

    @Override
    public boolean loadNextDaysResponse() {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String s = sharedPref.getString("nextdays", "");
        if(!TextUtils.isEmpty(s)){
            Type type = new TypeToken<DayList>(){}.getType();
            DayList dayList = (DayList) JSONParser.getFromJSONtoObject(s, type);
            view.setNextDays(dayList);
            return true;
        }
        return false;
    }

    @Override
    public String getLoadedCity() {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String s = sharedPref.getString("today", "");
        if(!TextUtils.isEmpty(s)) {
            Type type = new TypeToken<Day>() {}.getType();
            Day day = (Day) JSONParser.getFromJSONtoObject(s, type);
            return day.getCity();
        }
        else return "";
    }

    @Override
    public void setLastUpdate(String l) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("lastupdate", l);
        editor.apply();
    }

    @Override
    public String getLastUpdate() {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String s = sharedPref.getString("lastupdate", "");
        return s;
    }
}
