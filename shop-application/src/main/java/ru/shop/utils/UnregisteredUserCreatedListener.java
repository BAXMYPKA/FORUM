package ru.shop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UnregisteredUserCreatedListener implements ApplicationListener<UnregisteredUserCreated> {
	
	@Autowired
	private ShopEmailService shopEmailService;
	
	@Override
	public void onApplicationEvent(UnregisteredUserCreated event) {
		shopEmailService.sendSimpleEmail(event.getUser());
	}
}
