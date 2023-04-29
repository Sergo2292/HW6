package HW7;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controller {

        private WeatherModel weatherModel = (WeatherModel) new AccuweatherModel();
        private Map<Integer, Period> variants = new HashMap<>();

        public Controller() {
            variants.put(1, Period.NOW);
            variants.put(5, Period.FIVE_DAYS);
        }

        public void getWeather(String userInput, String selectedCity) throws IOException {
            Integer userIntegerInput = Integer.parseInt(userInput);

            switch (variants.get(userIntegerInput)) {
                case NOW:
                    weatherModel.getWeather(Period.NOW, selectedCity);
                    break;
                case FIVE_DAYS:
                    weatherModel.getWeather(Period.FIVE_DAYS, selectedCity);
                    break;
            }
        }
        public boolean rightCity(String selectedCity) throws IOException {
            if(weatherModel.detectCityKey(selectedCity) == null) return false;
            else return true;
        }
}
