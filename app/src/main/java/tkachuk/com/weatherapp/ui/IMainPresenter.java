package tkachuk.com.weatherapp.ui;

public interface IMainPresenter {

    void loadWeather (String city);

    void saveDayResponse(final String response);

    boolean loadDayResponse();

    void saveNextDaysResponse(final String response);

    boolean loadNextDaysResponse();

    String getLoadedCity();

    void setLastUpdate(final String l);

    String getLastUpdate();
}
