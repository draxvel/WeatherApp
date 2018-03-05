package tkachuk.com.weatherapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tkachuk.com.weatherapp.R;
import tkachuk.com.weatherapp.model.Day;
import tkachuk.com.weatherapp.util.DateManager;

public class TodayFragment extends Fragment{

    private View root;

    private TextView date_tv;
    private ImageView skyNow_iv;
    private TextView skyNow_tv;
    private TextView temp_tv;
    private TextView pressure_tv;
    private TextView humidity_tv;
    private TextView wind_tv;
    private TextView clouds_tv;
    private TextView sunrise_tv;
    private TextView sunset_tv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_today, container, false);
        setRetainInstance(true);
        initView();
        return root;
    }

    private void initView() {
        date_tv = root.findViewById(R.id.date_tv);
        skyNow_iv = root.findViewById(R.id.skyNow_iv);
        skyNow_tv = root.findViewById(R.id.skyNow_tv);
        temp_tv = root.findViewById(R.id.temp_tv);
        pressure_tv = root.findViewById(R.id.pressure_tv);
        humidity_tv =root.findViewById(R.id.humidity_tv);
        wind_tv = root.findViewById(R.id.wind_tv);
        clouds_tv = root.findViewById(R.id.clouds_tv);
        sunrise_tv = root.findViewById(R.id.sunrise_tv);
        sunset_tv = root.findViewById(R.id.sunset_tv);
    }


    public void setData(Day day){
        date_tv.setText(DateManager.getDayOfWeek(0));

        Picasso.with(getActivity().getApplicationContext())
                .load(day.getWeather().get(0).getIconUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(skyNow_iv);

        skyNow_tv.setText(day.getWeather().get(0).getMainClouds());
        temp_tv.setText(day.getMain().getTemp().toString()+"° C");
        pressure_tv.setText("pressure - "+day.getMain().getPressure().toString()+" mm");
        humidity_tv.setText("humidity - "+day.getMain().getHumidity().toString()+" %");
        wind_tv.setText("wind - "+day.getWind().getSpeed().toString()+" m/s");
        clouds_tv.setText("clouds - "+day.getClouds().getAll().toString()+" %");
        String sunrise = DateManager.getTimeFromUTC(day.getSys().getSunrise());
        String sunset = DateManager.getTimeFromUTC(day.getSys().getSunset());
        sunrise_tv.setText("sunrise - "+sunrise);
        sunset_tv.setText("sunset - "+sunset);
    }

}
