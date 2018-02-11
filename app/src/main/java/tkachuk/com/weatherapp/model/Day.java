package tkachuk.com.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

public class Day {

    public class Clouds {
        @SerializedName("all")
        @Expose
        private Integer all;

        public Integer getAll() {
            return all;
        }

        public void setAll(Integer all) {
            this.all = all;
        }
    }

    public class MainWeather {
        @SerializedName("temp")
        @Expose
        private Double temp;
        @SerializedName("humidity")
        @Expose
        private Double humidity;
        @SerializedName("pressure")
        @Expose
        private Double pressure;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Double getHumidity() {
            return humidity;
        }

        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }

        public Double getPressure() {
            return pressure;
        }

        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }
    }

    public class Sys {
        @SerializedName("sunrise")
        @Expose
        private Integer sunrise;
        @SerializedName("sunset")
        @Expose
        private Integer sunset;


        public Integer getSunrise() {
            return sunrise;
        }

        public void setSunrise(Integer sunrise) {
            this.sunrise = sunrise;
        }

        public Integer getSunset() {
            return sunset;
        }

        public void setSunset(Integer sunset) {
            this.sunset = sunset;
        }
    }

    public class Weather {
        @SerializedName("main")
        @Expose
        private String mainClouds;
        @SerializedName("icon")
        @Expose
        private String icon;

        public String getMainClouds() {
            return mainClouds;
        }

        public void setMainClouds(String mainClouds) {
            this.mainClouds = mainClouds;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIconUrl() {
            return "http://openweathermap.org/img/w/" + getIcon() + ".png";
        }
    }

    public class Wind {
        @SerializedName("speed")
        @Expose
        private Double speed;

        public Double getSpeed() {
            return speed;
        }

        public void setSpeed(Double speed) {
            this.speed = speed;
        }
    }

        @SerializedName("sys")
        @Expose
        private Sys sys;
        @SerializedName("weather")
        @Expose
        private List<Weather> weather = null;
        @SerializedName("main")
        @Expose
        private MainWeather main;
        @SerializedName("wind")
        @Expose
        private Wind wind;
        @SerializedName("clouds")
        @Expose
        private Clouds clouds;

        @SerializedName("dt")
        @Expose
        private long dt;

        @SerializedName("name")
        @Expose
        private String city;

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public MainWeather getMain() {
            return main;
        }

        public void setMain(MainWeather mainWeather) {
            this.main = mainWeather;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }


        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        public String getCity() {
            return city;
        }

        public void setName(String city) {
            this.city = city;
        }


        public Calendar getDate() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(dt * 1000);
        return date;
        }

}
