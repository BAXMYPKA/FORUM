package ru.shop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UnregisteredUserCreatedListener implements ApplicationListener<UnregisteredUserCreated> {
	
	@Autowired
	private MailSender mailSender;
	
	@Override
	public void onApplicationEvent(UnregisteredUserCreated event) {
		mailSender.sendSimpleEmail(event.getUser());
	}
}
