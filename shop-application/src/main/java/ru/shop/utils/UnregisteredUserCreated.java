package ru.shop.utils;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.shop.entities.User;

public class UnregisteredUserCreated extends ApplicationEvent {
	
	@Getter
	private User user;
	
	/**
	 * Create a new {@code ApplicationEvent}.
	 *
	 * @param source the object on which the event initially occurred or with
	 *               which the event is associated (never {@code null})
	 */
	public UnregisteredUserCreated(User source) {
		super(source);
		this.user = source;
	}
}
