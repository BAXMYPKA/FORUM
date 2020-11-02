package ru.shop.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.shop.entities.User;
import ru.shop.entities.dto.UserDto;

@Slf4j
@Component
public class ShopEventPublisher {
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	public void publishUnregisteredUserCreated(User user) {
		UnregisteredUserCreated userCreatedEvent = new UnregisteredUserCreated(user);
		new Thread(() -> eventPublisher.publishEvent(userCreatedEvent)).start();
//		eventPublisher.publishEvent(userCreatedEvent);
		log.debug("Even for new User={} has been published", user.getEmail());
	}
}
