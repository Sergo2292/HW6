package HW7;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class AccuweatherModel {
    private static final String PROTOKOL = "https";
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String FORECASTS = "forecasts";
    private static final String VERSION = "v1";
    private static final String SEARCH = "search";
    private static final String DAILY = "daily";
    private static final String ONE_DAY = "1day";
    private static final String FIVE_DAYS = "5day";
    private static final String API_KEY = "3eqz9K5FAS6FdzXhHvA763QiJA0FiGRL";
    private static final String API_KEY_QUERY_PARAM = "apikey";
    private static final String LOCATIONS = "locations";
    private static final String CITIES = "cities";
    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public void getWeather(Period period, String selectedCity) throws IOException {
        switch (period) {
            case NOW:
                HttpUrl oneDayhttpUrl = new HttpUrl.Builder()
                        .scheme(PROTOKOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(ONE_DAY)
                        .addPathSegment(detectCityKey(selectedCity))
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .build();

                Request oneDayrequest = new Request.Builder()
                        .url(oneDayhttpUrl)
                        .build();

                Response oneDayResponse = okHttpClient.newCall(oneDayrequest).execute();
                String oneDayForecastResponse = oneDayResponse.body().string();

                if (!oneDayForecastResponse.equals("[]")) {
                    DailyForecast oneDayForecast = objectMapper.readValue(oneDayForecastResponse, DailyForecast.class);
                    printForecast(oneDayForecast, selectedCity);
                }
                break;
            case FIVE_DAYS:
                //TODO*: реализовать вывод погоды на 5 дней
                HttpUrl fiveDayshttpUrl = new HttpUrl.Builder()
                        .scheme(PROTOKOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(FIVE_DAYS)
                        .addPathSegment(detectCityKey(selectedCity))
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .build();

                Request fiveDaysrequest = new Request.Builder()
                        .url(fiveDayshttpUrl)
                        .build();

                Response fiveDaysResponse = okHttpClient.newCall(fiveDaysrequest).execute();
                String fiveDaysForecastResponse = fiveDaysResponse.body().string();

                if (!fiveDaysForecastResponse.equals("[]")) {
                    DailyForecast oneDayForecast = objectMapper.readValue(fiveDaysForecastResponse, DailyForecast.class);
                    printForecast(oneDayForecast, selectedCity);
                }
                break;
        }
    }
    public String detectCityKey(String selectCity) throws IOException {
        //http://dataservice.accuweather.com/locations/v1/cities/search
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(PROTOKOL)
                .host(BASE_HOST)
                .addPathSegment(LOCATIONS)
                .addPathSegment(VERSION)
                .addPathSegment(CITIES)
                .addPathSegment(SEARCH)
                .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .addQueryParameter("q", selectCity)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseString = response.body().string();

        if (objectMapper.readTree(responseString).isEmpty()) return null;
        else {
            String cityKey = objectMapper.readTree(responseString).get(0).at("/Key").asText();
            return cityKey;
        }
    }
    //TODO: сделать человекочитаемый вывод погоды. Выбрать параметры для вывода на свое усмотрение
    public void printForecast(DailyForecast forecast, String cityName) {

        ArrayList<DailyForecasts> dailyForecasts = new ArrayList<>(forecast.getDailyForecasts());
        System.out.println("Weather forecast from date " + dailyForecasts.get(0).getDate().substring(0, 10) + " in the city " + cityName);
        System.out.println();

        Iterator<DailyForecasts> iter = dailyForecasts.iterator();
        while (iter.hasNext()) {
            DailyForecasts dailyForecastIter = iter.next();
            System.out.println("Date: " + dailyForecastIter.getDate().substring(0, 10) + "; " +
                    "max temperature: " +
                    Math.round(5*(dailyForecastIter.getTemperatureObject().getMaximumObject().getValue()-32)/9) + "C, " +
                    "min temperature: " +
                    Math.round(5*(dailyForecastIter.getTemperatureObject().getMinimumObject().getValue()-32)/9) + "C"
            );
        }
        System.out.println();
        System.out.println("Main info for the next 5 days: "
                + forecast.getHeadline().getText()
                + " (" + forecast.getHeadline().getEffectiveDate().substring(0, 10) + ")");
    }
}
