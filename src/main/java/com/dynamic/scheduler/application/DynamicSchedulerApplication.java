package com.dynamic.scheduler.application;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DynamicSchedulerApplication {


	public static void main(String[] args) {
		SpringApplication.run(DynamicSchedulerApplication.class, args);
	}
//	https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java

}
