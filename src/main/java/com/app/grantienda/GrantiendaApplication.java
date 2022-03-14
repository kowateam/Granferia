package com.app.grantienda;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mercadopago.MercadoPago;

import com.mercadopago.exceptions.MPException;

@Configuration
@SpringBootApplication
@ComponentScan
public class GrantiendaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws MPException {
		SpringApplication.run(GrantiendaApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GrantiendaApplication.class);
	}

}
