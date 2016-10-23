/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.filiberry.mqttWeather;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.filiberry.mqttWeather.model.AppConst;
import de.filiberry.mqttWeather.worker.WeatherWorker;

public class Activator implements BundleActivator, ManagedService {

	private static final String BUNDLE_ID = "mqttWeather";
	private AppConst config = null;
	private ServiceRegistration serviceReg;
	private final static Logger LOGGER = LoggerFactory.getLogger(Activator.class);
	private WeatherWorker weatherWorker;
	private Scheduler sheduler;
	private JobDetail jobDetail;

	
	/**
	 * 
	 */
	public void start(BundleContext context) {
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put(Constants.SERVICE_PID, BUNDLE_ID);
		serviceReg = context.registerService(ManagedService.class.getName(), this, properties);
	}

	/**
	 * 
	 */
	public void stop(BundleContext context) {
		try {
			sheduler.shutdown();
			LOGGER.info("The mqttGpioActor Bundle stopped.");
		} catch (SchedulerException e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 
	 */
	public void updated(Dictionary properties) throws ConfigurationException {
		if (properties == null) {
			LOGGER.error("mqttWeather config is null - Please give me a config File");
			return;
		}
		try {

			Integer intervall = new Integer((String) properties.get(AppConst.INTERVALL));

			SchedulerFactory sf = new StdSchedulerFactory();
			sheduler = sf.getScheduler();
			// --
			Trigger trigger = newTrigger().withIdentity("weatherWorkerTrigger", "weatherWorkerGroup").startNow()
					.withSchedule(simpleSchedule().withIntervalInSeconds(intervall.intValue() * 60).repeatForever())
					.build();

			LOGGER.info("Setting mqttWeather Config...");

			jobDetail = newJob(WeatherWorker.class).withIdentity("weatherWorker", "weatherWorkerGroup")
					.usingJobData(AppConst.HOST, (String) properties.get(AppConst.HOST))
					.usingJobData(AppConst.TOPIC, (String) properties.get(AppConst.TOPIC))
					.usingJobData(AppConst.WUURL, (String) properties.get(AppConst.WUURL))
					.usingJobData(AppConst.CLIENTID, (String) properties.get(AppConst.CLIENTID)).build();

			sheduler.scheduleJob(jobDetail, trigger);
			sheduler.start();
			LOGGER.info("Job Sheduler startet with Intervall : " + intervall.toString() + " Minutes");
		} catch (SchedulerException e) {
			LOGGER.error(e.getMessage());
		}
	}

}