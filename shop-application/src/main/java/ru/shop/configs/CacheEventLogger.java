package ru.shop.configs;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheEventLogger implements CacheEventListener<Object, Object> {
	
	@Override
	public void onEvent(CacheEvent<?,?> cacheEvent) {
		log.warn("Key: {} | EventType: {} | Old value: {} | New value: {}",
			cacheEvent.getKey(), cacheEvent.getType(), cacheEvent.getOldValue(),
			cacheEvent.getNewValue());
	}
}