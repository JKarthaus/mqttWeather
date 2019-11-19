
package de.filiberry.mqttWeather.model.weatherUnderground;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metric {

	@SerializedName("temp")
	@Expose
	private Integer temp;
	@SerializedName("heatIndex")
	@Expose
	private Integer heatIndex;
	@SerializedName("dewpt")
	@Expose
	private Integer dewpt;
	@SerializedName("windChill")
	@Expose
	private Integer windChill;
	@SerializedName("windSpeed")
	@Expose
	private Integer windSpeed;
	@SerializedName("windGust")
	@Expose
	private Integer windGust;
	@SerializedName("pressure")
	@Expose
	private Double pressure;
	@SerializedName("precipRate")
	@Expose
	private Integer precipRate;
	@SerializedName("precipTotal")
	@Expose
	private Double precipTotal;
	@SerializedName("elev")
	@Expose
	private Integer elev;

	public Integer getTemp() {
		return temp;
	}

	public void setTemp(Integer temp) {
		this.temp = temp;
	}

	public Integer getHeatIndex() {
		return heatIndex;
	}

	public void setHeatIndex(Integer heatIndex) {
		this.heatIndex = heatIndex;
	}

	public Integer getDewpt() {
		return dewpt;
	}

	public void setDewpt(Integer dewpt) {
		this.dewpt = dewpt;
	}

	public Integer getWindChill() {
		return windChill;
	}

	public void setWindChill(Integer windChill) {
		this.windChill = windChill;
	}

	public Integer getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(Integer windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Integer getWindGust() {
		return windGust;
	}

	public void setWindGust(Integer windGust) {
		this.windGust = windGust;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	public Integer getPrecipRate() {
		return precipRate;
	}

	public void setPrecipRate(Integer precipRate) {
		this.precipRate = precipRate;
	}

	public Double getPrecipTotal() {
		return precipTotal;
	}

	public void setPrecipTotal(Double precipTotal) {
		this.precipTotal = precipTotal;
	}

	public Integer getElev() {
		return elev;
	}

	public void setElev(Integer elev) {
		this.elev = elev;
	}

}
