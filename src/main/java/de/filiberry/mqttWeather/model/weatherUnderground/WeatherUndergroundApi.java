
package de.filiberry.mqttWeather.model.weatherUnderground;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherUndergroundApi {

    @SerializedName("observations")
    @Expose
    private List<Observation> observations = null;

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

}
