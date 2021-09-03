package com.zavala.cursoSpring.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.zavala.cursoSpring.services.DBService;
import com.zavala.cursoSpring.services.EmailService;
import com.zavala.cursoSpring.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;  //estrategia de geração do banco de dados
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if ( !"create".equals(strategy)) {
			return false;  //não faz nada no BD
		}
		
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
