package org.registrator.community.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan({"org.registrator.community.components"})
@PropertySource(value = "classpath:mail.properties")
public class AdditionalAppConfig {
	
	@Autowired
    private Environment env;

    @Bean(name = "mailSender")
    public JavaMailSender mailSender(){
    	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    	mailSender.setHost(env.getProperty("mail.host"));
    	mailSender.setPort(Integer.valueOf(env.getProperty("mail.port")));
    	mailSender.setUsername(env.getProperty("mail.user"));
    	mailSender.setPassword(env.getProperty("mail.password"));
    	
    	Properties javaMailProperties = new Properties();
    	javaMailProperties.setProperty("mail.smtp.auth", "true");
    	javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
		mailSender.setJavaMailProperties(javaMailProperties);
    	return mailSender;
    }
}

