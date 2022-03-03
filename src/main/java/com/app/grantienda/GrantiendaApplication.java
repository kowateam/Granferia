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
		MercadoPago.SDK.setAccessToken("TEST-7797993896470538-091018-8c68e58ed7c6d3f61e70f7f6efe9318a-210314886");
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GrantiendaApplication.class);
	}

}
