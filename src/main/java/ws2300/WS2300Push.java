package ws2300;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.filiberry.mqttWeather.model.CurrentObservation;
import de.filiberry.mqttWeather.worker.WeatherWorker;

public class WS2300Push {

	private static final String WS2300TOPIC = "/de/karthaus/wetterstation";
	private static final String WS2300HOST = "tcp://bueropi:1883";
	//private static final String WS2300HOST = "tcp://localhost:1883";
	private static final String CLIENTID = "BathControlWS2300";
	private SimpleDateFormat formatRFC822 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
	private final static Logger LOGGER = LoggerFactory.getLogger(WS2300Push.class);
	private Gson gson = new Gson();

	private String xml2300 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ws2300 version=\"1.0\"><Date>_DATE_</Date><Time>_TIME_</Time><Temperature><Indoor><Value>21.8</Value><Min>21.5</Min><Max>22.6</Max><MinTime>15:43</MinTime><MinDate>2001-01-10</MinDate><MaxTime>06:35</MaxTime><MaxDate>2001-01-10</MaxDate></Indoor><Outdoor><Value>_TEMPERATURE_OUTDOOR_</Value><Min>1.1</Min><Max>12.9</Max><MinTime>14:55</MinTime><MinDate>2001-01-10</MinDate><MaxTime>14:05</MaxTime><MaxDate>2001-01-10</MaxDate></Outdoor></Temperature><Humidity><Indoor><Value>45</Value><Min>45</Min><Max>46</Max><MinTime>17:15</MinTime><MinDate>2001-01-10</MinDate><MaxTime>11:02</MaxTime><MaxDate>2001-01-10</MaxDate></Indoor><Outdoor><Value>110</Value><Min>99</Min><Max>99</Max><MinTime>15:42</MinTime><MinDate>2001-01-10</MinDate><MaxTime>15:42</MaxTime><MaxDate>2001-01-10</MaxDate></Outdoor></Humidity><Dewpoint><Value>136.7</Value><Min>46.9</Min><Max>69.6</Max><MinTime>14:55</MinTime><MinDate>2001-01-10</MinDate><MaxTime>14:05</MaxTime><MaxDate>2001-01-10</MaxDate></Dewpoint><Wind><Value>51.0</Value><Direction><Text>N</Text><Dir0>0.0</Dir0><Dir1>0.0</Dir1><Dir2>0.0</Dir2><Dir3>0.0</Dir3><Dir4>0.0</Dir4><Dir5>0.0</Dir5></Direction><Min>0.0</Min><Max>49.6</Max><MinTime>03:11</MinTime><MinDate>2001-01-01</MinDate><MaxTime>03:34</MaxTime><MaxDate>2001-01-01</MaxDate></Wind><Windchill><Value>136.7</Value><Min>47.1</Min><Max>69.9</Max><MinTime>14:55</MinTime><MinDate>2001-01-10</MinDate><MaxTime>14:05</MaxTime><MaxDate>2001-01-10</MaxDate></Windchill><Rain><OneHour><Value>0.00</Value><Max>3.10</Max><MaxTime>13:44</MaxTime><MaxDate>2001-01-03</MaxDate></OneHour><TwentyFourHour><Value>0.00</Value><Max>10.36</Max><MaxTime>18:57</MaxTime><MaxDate>2001-01-03</MaxDate></TwentyFourHour><Total><Value>469.82</Value><Time>11:37</Time><Date>2014-01-02</Date></Total></Rain><Pressure><Value>971.800</Value><Min>955.800</Min><Max>981.200</Max><MinTime>23:26</MinTime><MinDate>2001-01-01</MinDate><MaxTime>08:18</MaxTime><MaxDate>2001-01-05</MaxDate><Tendency>Falling</Tendency></Pressure><Forecast>Rainy</Forecast></ws2300>";

	private MemoryPersistence persistence = new MemoryPersistence();

	public void publish(CurrentObservation currentObservation) {
		try {
			// Connect Broker
			MqttClient sampleClient = new MqttClient(WS2300HOST, CLIENTID, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			LOGGER.info("Connecting to MQTT Broker:" + WS2300HOST);
			sampleClient.connect(connOpts);
			LOGGER.info("connected");

			sampleClient.publish(WS2300TOPIC, buildMQTTMessage(currentObservation));

			sampleClient.disconnect();
			;

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}

	}

	/**
	 * 
	 * @param timeRfc8332
	 * @param value
	 * @return
	 */
	private MqttMessage buildMQTTMessage(CurrentObservation currentObservation) {

		DateFormat ws2300DateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat ws2300TimeFormat = new SimpleDateFormat("HH:mm:ss");

		Date observationDate;
		try {
			observationDate = formatRFC822.parse(currentObservation.getObservationTimeRfc822());
		} catch (ParseException e) {
			LOGGER.error(e.getMessage());
			observationDate = new Date();
		}

		String messageText = xml2300.replaceAll("_DATE_", ws2300DateFormat.format(observationDate));
		messageText = messageText.replaceAll("_TIME_", ws2300TimeFormat.format(observationDate));
		messageText = messageText.replaceAll("_TEMPERATURE_OUTDOOR_", "" + currentObservation.getTempC());

		LOGGER.info("Pushed out MessageText to Topic " + WS2300TOPIC);

		MqttMessage result = new MqttMessage(messageText.getBytes());
		result.setQos(WeatherWorker.mqttQOS);
		return result;
	}

}
