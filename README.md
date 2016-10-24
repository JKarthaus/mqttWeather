# mqttWeather
Mqtt Weather gather Data from Weather Underground, and publish the Data to a local MQTT Broker

*mqttWeather* is a Application that runs as Bundle in the [**KARAF**](http://karaf.apache.org/) Application Container 

*mqttWeather* fetch Data from [**Weather Underground**](https://www.wunderground.com/?apiref=f6b55eeacae18321)  

## Step One - Get your Weather Underground API Key
Register [here](https://www.wunderground.com/weather/api/)

## Step two - Create a **mqttWeather.cfg** File. 

Goto your Karaf installation **KARAF_BASE_DIR/etc** 
Create a file named **mqttWeather.cfg**
```
############################################################################
# MQTT Configuration
mqtt.host=tcp://{MQTT_BROKER}:1883
mqtt.topic=/filiberry/weather
mqtt.client.id=mqttWeather
# Weather Underground Call Intervall in Minutes
weatherUnderground.intervall=20
weatherUnderground.url=http://api.wunderground.com/api/{API_KEY_HERE}/conditions/q/pws:{PWS_ID_HERE}.json
```
Dont forget to replace **{API_KEY_HERE}** 
and Your favourite Weather Station ID with **{PWS_ID_HERE}**  
Set the Name or IP Adresse from your MQTT Broker here **{MQTT_BROKER}**

## Step three Install the Bundle to your Karaf Server

## Step four Control the Result
