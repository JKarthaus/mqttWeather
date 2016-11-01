# mqttWeather 
Mqtt Weather collect Data from Weather Underground, and publish the Data to a local MQTT Broker

*mqttWeather* is a OSGI Application that comes as Bundle for the [**KARAF**](http://karaf.apache.org/) Application Container 

*mqttWeather* fetch Data from [**Weather Underground**](https://www.wunderground.com/?apiref=f6b55eeacae18321)  

### How to use it ?

## Step One - Get your Weather Underground API Key
Register [here](https://www.wunderground.com/weather/api/)

## Step two - Create a mqttWeather Config File. 

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
Dont forget to replace **{API_KEY_HERE}** with your API Key.   
and Your favourite Weather Station ID with **{PWS_ID_HERE}**  
Set the Name or IP Adresse from your MQTT Broker here **{MQTT_BROKER}**

## Step three Install the Bundle to your Karaf Server
Simply copy [mqttWeather.jar](https://github.com/JKarthaus/mqttWeather/blob/master/mqttWeather.jar) to **KARAF_BASE_DIR/deploy**

## Step four Control the Result
Startup or Log into your Karaf installation.  
![Karaf login](http://www.joern-karthaus.de/blog/img/mqttweather2.png)  
And check the Bundle ist up and running.    
![BundleList](http://www.joern-karthaus.de/blog/img/mqttweather3.png)  

## Thats it ! - check the Result 
Check Karaf Log  
![Check the Result](http://www.joern-karthaus.de/blog/img/mqttweather4.png)

The Result in [MQTTLens](https://github.com/sandro-k/MQTTLensChromeApp) 

![Result in MQTTLens](http://www.joern-karthaus.de/blog/img/mqttweather1.png)  
