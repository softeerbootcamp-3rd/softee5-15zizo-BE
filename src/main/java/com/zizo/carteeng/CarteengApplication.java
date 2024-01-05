package com.zizo.carteeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CarteengApplication {

	public static void main(String[] args) { SpringApplication.run(CarteengApplication.class, args); }

}
