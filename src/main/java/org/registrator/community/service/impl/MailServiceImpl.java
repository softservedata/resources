package org.registrator.community.service.impl;

import org.registrator.community.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Async
	public void sendRecoveryPasswordMail(String recepientEmail, String token) {
//		mailSender.send(mimeMessage);
	}
}
