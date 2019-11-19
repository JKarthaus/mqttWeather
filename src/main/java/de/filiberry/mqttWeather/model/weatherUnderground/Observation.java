
package de.filiberry.mqttWeather.model.weatherUnderground;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Observation {

    @SerializedName("stationID")
    @Expose
    private String stationID;
    @SerializedName("obsTimeUtc")
    @Expose
    private String obsTimeUtc;
    @SerializedName("obsTimeLocal")
    @Expose
    private String obsTimeLocal;
    @SerializedName("neighborhood")
    @Expose
    private String neighborhood;
    @SerializedName("softwareType")
    @Expose
    private String softwareType;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("solarRadiation")
    @Expose
    private Object solarRadiation;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("realtimeFrequency")
    @Expose
    private Object realtimeFrequency;
    @SerializedName("epoch")
    @Expose
    private Integer epoch;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("uv")
    @Expose
    private Object uv;
    @SerializedName("winddir")
    @Expose
    private Integer winddir;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("qcStatus")
    @Expose
    private Integer qcStatus;
    @SerializedName("metric")
    @Expose
    private Metric metric;

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getObsTimeUtc() {
        return obsTimeUtc;
    }

    public void setObsTimeUtc(String obsTimeUtc) {
        this.obsTimeUtc = obsTimeUtc;
    }

    public String getObsTimeLocal() {
        return obsTimeLocal;
    }

    public void setObsTimeLocal(String obsTimeLocal) {
        this.obsTimeLocal = obsTimeLocal;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getSoftwareType() {
        return softwareType;
    }

    public void setSoftwareType(String softwareType) {
        this.softwareType = softwareType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Object getSolarRadiation() {
        return solarRadiation;
    }

    public void setSolarRadiation(Object solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Object getRealtimeFrequency() {
        return realtimeFrequency;
    }

    public void setRealtimeFrequency(Object realtimeFrequency) {
        this.realtimeFrequency = realtimeFrequency;
    }

    public Integer getEpoch() {
        return epoch;
    }

    public void setEpoch(Integer epoch) {
        this.epoch = epoch;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Object getUv() {
        return uv;
    }

    public void setUv(Object uv) {
        this.uv = uv;
    }

    public Integer getWinddir() {
        return winddir;
    }

    public void setWinddir(Integer winddir) {
        this.winddir = winddir;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getQcStatus() {
        return qcStatus;
    }

    public void setQcStatus(Integer qcStatus) {
        this.qcStatus = qcStatus;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

}
