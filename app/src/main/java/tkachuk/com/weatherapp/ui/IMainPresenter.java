package tkachuk.com.weatherapp.ui;

public interface IMainPresenter {

    void loadWeather (String city);

    void saveDayResponse(final String response);

    void loadDayResponse();

    void saveNextDaysResponse(final String response);

    void loadNextDaysResponse();
}
