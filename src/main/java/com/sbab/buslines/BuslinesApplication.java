package com.sbab.buslines;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbab.buslines.data.BusLineData;
import com.sbab.buslines.data.HttpCollector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BuslinesApplication {

	record Test<A>(String test, List<A> test2) {}
	public static void main(String[] args) {
		SpringApplication.run(BuslinesApplication.class, args);
	}

	@Bean
	public CommandLineRunner getRunner(ApplicationContext ctx){
		return (args) -> {
			ctx.getBean(BusLineData.class).init(new HttpCollector());
		};
	}

}
