package mqttWeather;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class DateParserTest2 {

	private SimpleDateFormat formatWuApi = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY);

	public Date parseWuApiDateString(String dateString) throws ParseException {
		Date date = null;
		try {
			date = formatWuApi.parse(dateString);
			System.out.println(date.toLocaleString());
		} catch (Exception e) {
			System.out.println(formatWuApi.toLocalizedPattern() + " passt nicht.");
		}
		if (date != null) {
			return date;
		}
		return null;

	}

	@Test
	public void test() {
		String source = "2019-03-29 19:09:59";

		try {
			assertNotNull(parseWuApiDateString(source));
		} catch (ParseException e) {
			fail(e.getMessage());
		}

	}

}
