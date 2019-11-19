package mqttWeather;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Ignore;
import org.junit.Test;

@Deprecated
@Ignore
public class DateParserTest {

	public static final SimpleDateFormat rfc822DateFormats[] = new SimpleDateFormat[] {
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z",Locale.ENGLISH),
			new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z",Locale.ENGLISH), 
			new SimpleDateFormat("EEE, d MMM yy HH:mm z"),
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z"),
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm z"),
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm z"),
			new SimpleDateFormat("EEE, d MMM yyyy HH:mm"),
			new SimpleDateFormat("d MMM yy HH:mm z"), new SimpleDateFormat("d MMM yy HH:mm:ss z"),
			new SimpleDateFormat("d MMM yyyy HH:mm z"), new SimpleDateFormat("d MMM yyyy HH:mm:ss z") };

	public static Date parseRfc822DateString(String dateString) throws ParseException {
		Date date = null;
		for (SimpleDateFormat sdf : rfc822DateFormats) {
			try {
				date = sdf.parse(dateString);
				System.out.println(date.toLocaleString());
			} catch (Exception e) {
				System.out.println(sdf.toLocalizedPattern() + " passt nicht.");
			}
			if (date != null) {
				return date;
			}
		}
		return null;

	}

	@Test
	public void test() {
		//String source = "Sat 2016 19:22:23";
		String source = "Sat, 22 Oct 2016 19:22:23 +0200";
		//String source = "Wed, 4 Jul 2001 12:08:56 -0700";

		try {
			assertNotNull(parseRfc822DateString(source));
		} catch (ParseException e) {
			fail(e.getMessage());
		}

	}

}
