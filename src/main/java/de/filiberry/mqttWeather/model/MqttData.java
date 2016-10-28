package de.filiberry.mqttWeather.model;

import java.util.Date;

public class MqttData {

	private Date timeStamp;
	private String value;

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
