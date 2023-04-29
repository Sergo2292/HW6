package HW7;

import java.io.IOException;

public interface WeatherModel {
    void getWeather(Period period, String selectedCity) throws IOException;
    String detectCityKey(String selectCity) throws IOException;
}
