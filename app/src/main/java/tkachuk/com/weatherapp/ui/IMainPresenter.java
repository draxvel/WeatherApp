package tkachuk.com.weatherapp.ui;

public interface IMainPresenter {
    void getWeather(final String city);

    void getForecast (final String city);
}
