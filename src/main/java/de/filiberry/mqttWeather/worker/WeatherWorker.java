package de.filiberry.mqttWeather.worker;

import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.filiberry.mqttWeather.model.AppConst;
import de.filiberry.mqttWeather.model.MqttData;
import de.filiberry.mqttWeather.model.WuStation;
import de.filiberry.mqttWeather.model.weatherUnderground.WeatherUndergroundApi;

public class WeatherWorker implements Job {

	private Gson gson = new Gson();
	private MemoryPersistence persistence = new MemoryPersistence();
	public static final int mqttQOS = 2;
	private final static Logger LOGGER = LoggerFactory.getLogger(WeatherWorker.class);
	private SimpleDateFormat formatWuApi = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY);

	// -- WS2300 FAKE
	// private WS2300Push ws2300Push = new WS2300Push();

	/**
	 * 
	 */
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {

			JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

			URL url = new URL(dataMap.getString(AppConst.WUURL));
			LOGGER.info("Conect Weather Underground :" + dataMap.getString(AppConst.WUURL));

			InputStreamReader reader = new InputStreamReader(url.openStream());
			WeatherUndergroundApi wuapi = gson.fromJson(reader, WeatherUndergroundApi.class);

			// DO WS2300 PUSH
			// ws2300Push.publish(wuStation.getCurrentObservation());

			// Connect Broker
			MqttClient sampleClient = new MqttClient(dataMap.getString(AppConst.HOST),
					dataMap.getString(AppConst.CLIENTID), persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			LOGGER.info("Connecting to MQTT Broker:" + dataMap.getString(AppConst.HOST));
			sampleClient.connect(connOpts);
			LOGGER.info("connected");

			// Send Temperature
			// ------------------------------------------------------------------------------
			String obsTimeLocal = wuapi.getObservations().get(0).getObsTimeLocal();
			sampleClient.publish(dataMap.getString(AppConst.TOPIC) + "/temperature",
					buildMQTTMessage(obsTimeLocal, wuapi.getObservations().get(0).getMetric().getTemp().toString()));
			
			// UGLY HACK for local Heating Control
			sampleClient.publish("heatingcontrol/outdoor/temp",
					buildSimpleMQTTMessage(wuapi.getObservations().get(0).getMetric().getTemp().toString()));
			
			// Send Pressure
			// --------------------------------------------------------------------------------
			sampleClient.publish(dataMap.getString(AppConst.TOPIC) + "/pressure", buildMQTTMessage(obsTimeLocal,
					wuapi.getObservations().get(0).getMetric().getPressure().toString()));
			// Send WeatherText - not supported in new API
			// --------------------------------------------------------------------------------
			// sampleClient.publish(dataMap.getString(AppConst.TOPIC) + "/weather",
			// buildMQTTMessage(obsTimeUTC,
			// wuStation.getCurrentObservation().getWeather()));
			// Send WindDirection
			// --------------------------------------------------------------------------------
			sampleClient.publish(dataMap.getString(AppConst.TOPIC) + "/windDirection", buildMQTTMessage(obsTimeLocal,
					wuapi.getObservations().get(0).getMetric().getWindGust().toString()));
			// Send WindKPH
			// --------------------------------------------------------------------------------
			sampleClient.publish(dataMap.getString(AppConst.TOPIC) + "/windKPH", buildMQTTMessage(obsTimeLocal,
					wuapi.getObservations().get(0).getMetric().getWindSpeed().toString()));

			LOGGER.info("Messages send");
			sampleClient.disconnect();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			LOGGER.info("Weather Worker interrupted.");
		}
	}

	/**
	 * 
	 * @param timeRfc8332
	 * @param value
	 * @return
	 */
	private MqttMessage buildMQTTMessage(String wuApiDateTime, String value) {
		MqttData mqttData = new MqttData();
		try {
			mqttData.setTimeStamp(formatWuApi.parse(wuApiDateTime));
		} catch (ParseException e) {
			mqttData.setTimeStamp(new Date());
			LOGGER.error(e.getMessage());
		}
		mqttData.setValue(value);
		MqttMessage result = new MqttMessage(gson.toJson(mqttData).getBytes());
		result.setQos(mqttQOS);
		return result;
	}

	private MqttMessage buildSimpleMQTTMessage(String value) {
		MqttMessage result = new MqttMessage(value.getBytes());
		result.setQos(mqttQOS);
		return result;
	}

}
