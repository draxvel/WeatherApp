package tkachuk.com.weatherapp.ui;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    private IMainView view;

    private String responseBody;

    public MainPresenter(Context context, IMainView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getWeather(String city) {
        if (InternetConnection.checkConnection(context)) {
            view.showProgress();
            //Creating an object for our api interface
            ApiService api = RetroClient.getRetroClient();

            //Calling Json
            Call<JsonObject> jsonArrayCall = api.getWeather(city);

            jsonArrayCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful())
                    {
                        responseBody = response.body().toString();
                        Log.i("responseBodyE", responseBody);

                        Type type = new TypeToken<Day>(){}.getType();
                        Day day= (Day) JSONParser.getFromJSONtoObject(responseBody, type);

                        view.setToday(day);
                        view.hideProgress();
                        view.hideRefreshing();
                    }
                    else
                    {
                        Log.i("responseBodyE", response.errorBody().toString());
                        view.hideProgress();
                        view.hideRefreshing();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.i("onFailure", t.getMessage());
                    view.incorrectCity();
                    view.hideProgress();
                    view.hideRefreshing();
                }
            });
        }
        else {
            view.showNotInternetConnection();
            view.hideProgress();
            view.hideRefreshing();
        }
    }

    @Override
    public void getForecast(String city) {
        if (InternetConnection.checkConnection(context)) {
            view.showProgress();
            //Creating an object for our api interface
            ApiService api = RetroClient.getRetroClient();

            //Calling Json
            Call<JsonObject> jsonArrayCall = api.getForecast(city);

            jsonArrayCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful())
                    {
                        responseBody = response.body().toString();
                        Log.i("responseBodyE", responseBody);

                        Type type = new TypeToken<DayList>(){}.getType();
                        DayList dayList= (DayList) JSONParser.getFromJSONtoObject(responseBody, type);

                        view.setNextDays(dayList);
                        view.hideProgress();
                        view.hideRefreshing();
                    }
                    else
                    {
                        Log.i("responseBodyE", response.errorBody().toString());
                        view.incorrectCity();
                        view.hideProgress();
                        view.hideRefreshing();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.i("onFailure", t.getMessage());
                    view.hideProgress();
                    view.hideRefreshing();
                }
            });
        }
        else {
            view.showNotInternetConnection();
            view.hideProgress();
            view.hideRefreshing();
        }
    }
}
