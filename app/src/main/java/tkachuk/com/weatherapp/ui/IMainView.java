package tkachuk.com.weatherapp.ui;

import tkachuk.com.weatherapp.model.Day;
import tkachuk.com.weatherapp.model.DayList;

public interface IMainView {

    void setToday(Day day);

    void setNextDays (DayList dayList);

    void setCity(String city);

    void showProgress();

    void hideProgress();

    void hideRefreshing();

    void showNotInternetConnection();

    void incorrectCity();
}
