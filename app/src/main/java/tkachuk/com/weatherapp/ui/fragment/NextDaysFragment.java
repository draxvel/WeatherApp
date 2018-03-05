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
import tkachuk.com.weatherapp.model.DayList;
import tkachuk.com.weatherapp.util.DateManager;

public class NextDaysFragment extends Fragment {

    private View root;

    //day 1
    private TextView date_tv1;
    private ImageView skyNow_iv1;
    private TextView skyNow_tv1;
    private TextView temp_tv1;
    private TextView wind_tv1;
    private TextView clouds_tv1;


    //day 2
    private TextView date_tv2;
    private ImageView skyNow_iv2;
    private TextView skyNow_tv2;
    private TextView temp_tv2;
    private TextView wind_tv2;
    private TextView clouds_tv2;


    //day 3
    private TextView date_tv3;
    private ImageView skyNow_iv3;
    private TextView skyNow_tv3;
    private TextView temp_tv3;
    private TextView wind_tv3;
    private TextView clouds_tv3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_nextdays, container, false);

        initView();
        return root;
    }

    private void initView() {
        date_tv1 = root.findViewById(R.id.date_tv1);
        skyNow_iv1 = root.findViewById(R.id.skyNow_iv1);
        skyNow_tv1 = root.findViewById(R.id.skyNow_tv1);
        temp_tv1 = root.findViewById(R.id.temp_tv1);
        wind_tv1 = root.findViewById(R.id.wind_tv1);
        clouds_tv1 = root.findViewById(R.id.clouds_tv1);

        date_tv2 = root.findViewById(R.id.date_tv2);
        skyNow_iv2 = root.findViewById(R.id.skyNow_iv2);
        skyNow_tv2 =  root.findViewById(R.id.skyNow_tv2);
        temp_tv2 = root.findViewById(R.id.temp_tv2);
        wind_tv2 =  root.findViewById(R.id.wind_tv2);
        clouds_tv2 = root.findViewById(R.id.clouds_tv2);

        date_tv3 = root.findViewById(R.id.date_tv3);
        skyNow_iv3 = root.findViewById(R.id.skyNow_iv3);
        skyNow_tv3 = root.findViewById(R.id.skyNow_tv3);
        temp_tv3 = root.findViewById(R.id.temp_tv3);
        wind_tv3 = root.findViewById(R.id.wind_tv3);
        clouds_tv3 = root.findViewById(R.id.clouds_tv3);
    }

    public void setData(DayList dayList){

        Day day1 = dayList.getItems().get(2);
        Day day2 = dayList.getItems().get(3);
        Day day3 = dayList.getItems().get(4);

        //1
        date_tv1.setText(DateManager.getDayOfWeek(2));

        Picasso.with(getActivity().getApplicationContext())
                .load(day1.getWeather().get(0).getIconUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(skyNow_iv1);

        skyNow_tv1.setText(day1.getWeather().get(0).getMainClouds());
        temp_tv1.setText(day1.getMain().getTemp().toString()+"° C");
        wind_tv1.setText("wind - "+day1.getWind().getSpeed().toString()+" m/s");
        clouds_tv1.setText("clouds - "+day1.getClouds().getAll().toString()+" %");
        
        
        //2
        date_tv2.setText(DateManager.getDayOfWeek(3));

        Picasso.with(getActivity().getApplicationContext())
                .load(day2.getWeather().get(0).getIconUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(skyNow_iv2);

        skyNow_tv2.setText(day2.getWeather().get(0).getMainClouds());
        temp_tv2.setText(day2.getMain().getTemp().toString()+"° C");
        wind_tv2.setText("wind - "+day2.getWind().getSpeed().toString()+" m/s");
        clouds_tv2.setText("clouds - "+day2.getClouds().getAll().toString()+" %");
        
        
        //3
        date_tv3.setText(DateManager.getDayOfWeek(4));

        Picasso.with(getActivity().getApplicationContext())
                .load(day3.getWeather().get(0).getIconUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(skyNow_iv3);

        skyNow_tv3.setText(day3.getWeather().get(0).getMainClouds());
        temp_tv3.setText(day3.getMain().getTemp().toString()+"° C");
        wind_tv3.setText("wind - "+day3.getWind().getSpeed().toString()+" m/s");
        clouds_tv3.setText("clouds - "+day3.getClouds().getAll().toString()+" %");
    }
}
