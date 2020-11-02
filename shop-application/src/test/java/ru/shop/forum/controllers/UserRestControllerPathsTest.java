package ru.shop.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shop.controllers.UserRestController;
import ru.shop.entities.RegistrationConfirmationUuid;
import ru.shop.entities.User;
import ru.shop.entities.dto.UserDto;
import ru.shop.repositories.UserRepository;
import ru.shop.security.JwtService;
import ru.shop.services.UserService;
import ru.shop.utils.ShopEventPublisher;

import java.util.Optional;

@WebMvcTest(controllers = {UserRestController.class})
@Import({ModelMapper.class, ShopEventPublisher.class, JwtService.class})
public class UserRestControllerPathsTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRestController userRestController;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	public void beforeEach() {
		Mockito.when(userService.getEntityClass()).thenReturn(ru.shop.entities.User.class);
		userRestController.setEntityClass(ru.shop.entities.User.class);
		userRestController.setServletContextPath("/shop.ru/forum");
		Mockito.when(userService.saveNewUnconfirmed(Mockito.any(ru.shop.entities.User.class))).thenReturn(new ru.shop.entities.User());
	}
	
	@Test
	public void get_All_Users_Should_Return_OK() throws Exception {
		//given
		Mockito.when(userService.findAll(Mockito.any(Pageable.class))).thenReturn(Page.empty());
		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/users").secure(true))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void get_One_User_Should_Return_OK() throws Exception {
		//given
		Mockito.when(userService.findOne(0L)).thenReturn(Optional.of(new ru.shop.entities.User()));
		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/users/0").secure(true))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void get_All_Users_By_Ids_Should_Return_OK() throws Exception {
		//given
		Mockito.when(userService.findAllByIds(Mockito.any(Pageable.class), Mockito.anySet())).thenReturn(Page.empty());
		
		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/users/all-by-ids?id=0,1").secure(true))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void delete_One_User_Should_Return_NoContent() throws Exception {
		//given
		userRestController.setEntityClass(ru.shop.entities.User.class);
		//when
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1.0/users/0")
				.secure(true)
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void delete_All_Users_By_Ids_Should_Return_NoContent() throws Exception {
		//given
		userRestController.setEntityClass(ru.shop.entities.User.class);
		//when
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1.0/users/all-by-ids?id=0,1,2")
				.secure(true)
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void post_One_User_Should_Return_201_Created() throws Exception {
		//given
		UserDto userDto = new UserDto();
		userDto.setEmail("userdto@email.com");
		userDto.setNickName("Nick");
		userDto.setRegistrationConfirmationUuid(new RegistrationConfirmationUuid());
		
		User user = modelMapper.map(userDto, User.class);
		
		Mockito.when(userService.saveNewUnconfirmed(Mockito.any(User.class))).thenReturn(user);
		Mockito.when(userService.existsUserByNickName("Nick")).thenReturn(false);
		
		//when
		mockMvc.perform(MockMvcRequestBuilders.post("/v1.0/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto))
				.secure(true)
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
	}
	
	@Test
	public void put_One_User_Should_Return_OK() throws Exception {
		//given
		UserDto userDto = new UserDto();
		userDto.setEmail("userdto@email.com");
		userDto.setNickName("Nick");
		userDto.setId(1L);
		
		Mockito.when(userService.existsUserByNickName("Nick")).thenReturn(true);
		Mockito.when(userService.update(Mockito.any(ru.shop.entities.User.class))).thenReturn(new ru.shop.entities.User("userdto@email.com"));
		
		//when
		mockMvc.perform(MockMvcRequestBuilders.put("/v1.0/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto))
				.secure(true)
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
}
