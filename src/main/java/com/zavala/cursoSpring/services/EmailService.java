package com.zavala.cursoSpring.services;

import org.springframework.mail.SimpleMailMessage;

import com.zavala.cursoSpring.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
