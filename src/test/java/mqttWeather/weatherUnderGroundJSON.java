package mqttWeather;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import com.google.gson.Gson;

import de.filiberry.mqttWeather.model.WuStation;

public class weatherUnderGroundJSON {

	@Test
	public void test() {

		Gson gson = new Gson();

		URL url;
		try {
			url = new URL("http://api.wunderground.com/api/e51e87f3e7592de9/conditions/q/pws:IWIEHL10.json");
			InputStreamReader reader = new InputStreamReader(url.openStream());

			WuStation test = gson.fromJson(reader, WuStation.class);

			System.out.println("Temperatur=" + test.getCurrentObservation().getTemperatureString());

		} catch (MalformedURLException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

	}

}
