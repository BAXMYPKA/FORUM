package ru.shop.entities.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.shop.entities.User;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.entities.utils.Sex;
import ru.shop.entities.utils.UniqueNickname;
import ru.shop.security.Roles;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDto extends AbstractDto<User> {
	
	@NotEmpty(message = "{field.notEmpty}")
	@Email(message = "{email.notValid}")
	private String email;
	
//	@Pattern(message = "{field.password}", regexp = "^[\\d\\w]{3,}$")
//	private String password;
	
	@UniqueNickname(message = "{user.nonUniqueNickname}")
	@NotEmpty(message = "{field.notEmpty}")
	private String nickName;
	
	@Size(min = 2, max = 35, message = "{user.nameLength}")
	private String firstName;
	
	@Size(min = 2, max = 35, message = "{user.nameLength}")
	private String lastName;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Past(message = "{date.past}")
	private LocalDate birthdate;
	
	/**
	 * Accepts possible English variations like 'F', 'fm', 'fem', 'female' etc and Russian 'М', 'Ж', 'муж', 'жен' etc.
	 */
	@Pattern(flags = {Pattern.Flag.CASE_INSENSITIVE}, message = "{user.sexPattern}",
		  regexp = "(?i)^(m|M|ma|MA|male|f|fe|fem|female|м|М|му|МУ|муж|мужской|Мужской|мужчина|ж|Ж|жен|женский|Женский)$")
	@Size(min = 1, max = 8, message = "{user.sex}")
	private Sex sex;
	
	@Size(max = 512000, message = "{photo.maxSize}")
	private byte[] photo;
	
	@Size(max = 50, message = "{user.selfDescriptionLength}")
	private String selfDescription;
	
	/**
	 * Security role. Default is {@link Roles#USER}
	 */
	@Size(max = 35, message = "{user.RoleLength}")
	private Roles role = Roles.USER;
	
	private ImgAvatar avatar;
	
	private Boolean enabled = true;
	
	private Boolean locked = false;
	
	private LocalDate lockedUntil;
	
	@Override
	protected void setEntityClass(Class<User> entityClass) {
		this.entityClass = entityClass;
	}
}
