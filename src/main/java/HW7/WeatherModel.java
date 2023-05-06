package HW7;

import java.io.IOException;
import java.sql.SQLException;

public interface WeatherModel {
    void getWeather(Period period, String selectedCity) throws IOException, SQLException, ClassNotFoundException;
    String detectCityKey(String selectCity) throws IOException;
    void printDataFromDB() throws ClassNotFoundException, SQLException;
}
