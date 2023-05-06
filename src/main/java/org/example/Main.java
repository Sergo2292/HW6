package org.example;

import javax.imageio.IIOException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        WeatherApp weatherApp = new WeatherApp();
        try {
            System.out.println("Прогноз погоды: " + weatherApp.get5DayForecast(weatherApp.getCityID()));
        } catch (IIOException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
