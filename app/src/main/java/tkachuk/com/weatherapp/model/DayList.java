package tkachuk.com.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayList {

    @SerializedName("list")
    private List<Day> items;

    public DayList(List<Day> items) {
        this.items = items;
    }

    public List<Day> getItems() {
        return items;
    }
}
