package com.hawkingbros.adapter;

import com.hawkingbros.adapter.meteoSource.Meteo;
import com.hawkingbros.adapter.meteoSource.Yandex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AdapterApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(AdapterApplication.class, args);
	}

}
