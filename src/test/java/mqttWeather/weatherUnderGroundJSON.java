package mqttWeather;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.google.gson.Gson;

import de.filiberry.mqttWeather.model.WuStation;
import de.filiberry.mqttWeather.model.weatherUnderground.WeatherUndergroundApi;

public class weatherUnderGroundJSON {

	@Test
	public void test() {

		Gson gson = new Gson();

		URL url;
		try {
			url = new URL("https://api.weather.com/v2/pws/observations/current?stationId=INMBRECH13&format=json&units=m&apiKey=9f1c3899e0a149fc9c3899e0a139fc26");
			InputStreamReader reader = new InputStreamReader(url.openStream());

			WeatherUndergroundApi test = gson.fromJson(reader, WeatherUndergroundApi.class);

			System.out.println("Temperatur=" + test.getObservations().get(0).getMetric().getTemp());

		} catch (MalformedURLException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

	}

}
