package ru.shop.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.shop.entities.RegistrationConfirmationUuid;
import ru.shop.entities.User;
import ru.shop.entities.utils.*;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.forum.entities.dto.UserForumSettingsDto;
import ru.shop.security.Roles;

import javax.persistence.Convert;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class UserDto extends AbstractDto<User> {
	
	//	@JsonView(ShopViews.UserDtoExt.class)
	@NotEmpty(message = "{field.notEmpty}")
	@Email(message = "{email.notValid}")
	private String email;
	
	@JsonIgnore
	@Pattern(groups = {ValidationCreateGroup.class}, message = "{field.password}", regexp = "^[\\d\\w]{3,}$")
	private String password;
	
	//	@JsonView(ShopViews.UserDtoExt.class)
	@UniqueNickname(groups = {ValidationCreateGroup.class}, message = "{user.nonUniqueNickname}")
	@NotEmpty(message = "{field.notEmpty}")
	private String nickName;
	
	//	@JsonView(ShopViews.UserDtoExt.class)
	@Size(min = 2, max = 35, message = "{user.nameLength}")
	private String firstName;
	
	//	@JsonView(ShopViews.UserDtoExt.class)
	@Size(min = 2, max = 35, message = "{user.nameLength}")
	private String lastName;
	
	//	@JsonView(ShopViews.UserDtoExt.class)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Past(message = "{date.past}")
	private LocalDate birthdate;
	
	/**
	 * Accepts possible English variations like 'F', 'fm', 'fem', 'female' etc and Russian 'М', 'Ж', 'муж', 'жен' etc.
	 */
//	@Pattern(flags = {Pattern.Flag.CASE_INSENSITIVE}, message = "{user.sexPattern}",
//			regexp = "(?i)^(m|M|ma|MA|male|f|fe|fem|female|м|М|му|МУ|муж|мужской|Мужской|мужчина|ж|Ж|жен|женский|Женский)$")
//	@Size(min = 1, max = 8, message = "{user.sex}")
//	@Convert(converter = SexSqlConverter.class) //No need if "autoApply=true" in the @Converter
	private Sex sex;
	
	@Size(max = 512000, message = "{photo.maxSize}")
	private byte[] photo;
	
	@Size(max = 50, message = "{user.selfDescriptionLength}")
	private String selfDescription;
	
	/**
	 * Security role. Default is {@link Roles#USER}
	 */
//	@Size(max = 35, message = "{user.RoleLength}")
	private Roles role = Roles.USER;
	
	private ImgAvatar avatar;
	
	private Boolean enabled = true;
	
	private Boolean locked = false;
	
	private LocalDate lockedUntil;
	
	//TODO: in production this field must be eliminated
	private RegistrationConfirmationUuid registrationConfirmationUuid;
	
	private UserForumSettingsDto userForumSettingsDto;
	
	public UserDto() {
		this.entityClass = User.class;
	}
	
	public void setEntityClass(Class<ru.shop.entities.User> entityClass) {
		this.entityClass = entityClass;
	}
	
	
}
