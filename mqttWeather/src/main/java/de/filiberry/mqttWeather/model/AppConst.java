package de.filiberry.mqttWeather.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConst {

	private final static Logger LOGGER = LoggerFactory.getLogger(AppConst.class);

	public static final String HOST = "mqtt.host";
	public static final String TOPIC = "mqtt.topic";
	public static final String WUURL = "weatherUnderground.url";
	public static final String CLIENTID = "mqtt.client.id";
	public static final String INTERVALL = "weatherUnderground.intervall";

}
