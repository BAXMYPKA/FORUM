package ru.shop.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.shop.entities.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@Component
public class ShopEmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.properties..mail.registration-confirmation.from}")
	private String from;
	
	@Value("${system.current.time-zone}")
	private String defaultTimeZone;
	
	public void sendSimpleEmail(User user) {
		TimeZone timeZone = null;
		if (user.getUserForumSettings() != null && user.getUserForumSettings().getTimeZone() != null) {
			timeZone = user.getUserForumSettings().getTimeZone();
		} else {
			timeZone = TimeZone.getTimeZone(ZoneId.of(defaultTimeZone));
		}
		
		Date currentDate = Date.from(LocalDateTime.now().atZone(timeZone.toZoneId()).toInstant());
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom(this.from);
		mailMessage.setSentDate(currentDate);
		mailMessage.setText("You have just registered at SHOP.RU. \n To confirm you registration and enter the portal please click to follow this link: "
			+ user.getRegistrationConfirmationUuid().getConfirmationUrl() + " \n If you consider this email as a wrong one please ignore it.");
		try {
			mailSender.send(mailMessage);
		} catch (MailParseException | MailAuthenticationException e) {
			log.warn(e.getMessage(), e);
		} catch (MailException e) {
			log.info(e.getMessage(), e);
		}
	}
	
}
