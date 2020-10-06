package ru.shop.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shop.forum.entities.Post;
import ru.shop.forum.entities.dto.PostDto;
import ru.shop.forum.services.PostService;
import ru.shop.forum.services.PrivateMessageService;
import ru.shop.repositories.UserRepository;

import java.util.Optional;

@WebMvcTest(controllers = {ForumIndexController.class, PostRestController.class, PrivateMessageRestController.class})
public class PostRestControllerPathsTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private PostRestController postRestController;
	
	@MockBean
	private PrivateMessageService privateMessageService;
	
	@MockBean
	private PostService postService;
	
	@MockBean
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	public void beforeEach() {
		Mockito.when(postService.getEntityClass()).thenReturn(Post.class);
		postRestController.setEntityClass(Post.class);
		Mockito.when(postService.save(Mockito.any(Post.class))).thenReturn(new Post());
	}
	
	@Test
	public void get_All_Posts_Should_Return_OK() throws Exception {
		//given
		Mockito.when(postService.findAll(Mockito.any(Pageable.class))).thenReturn(Page.empty());
		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/posts").secure(true))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void get_One_Post_Should_Return_OK() throws Exception {
		//given
		Mockito.when(postService.findOne(0L)).thenReturn(Optional.of(new Post()));
		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/posts/0").secure(true))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void get_All_Posts_By_Ids_Should_Return_OK() throws Exception {
		//given
		Mockito.when(postService.findAllByIds(Mockito.any(Pageable.class), Mockito.anySet())).thenReturn(Page.empty());
		
		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/posts/all-by-ids?id=0,1").secure(true))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void delete_One_Post_Should_Return_OK() throws Exception {
		//given
		postRestController.setEntityClass(Post.class);
		//when
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1.0/posts/0")
				.secure(true)
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void delete_All_Posts_By_Ids_Should_Return_OK() throws Exception {
		//given
		postRestController.setEntityClass(Post.class);
		//when
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1.0/posts/all-by-ids?id=0,1,2")
				.secure(true)
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void post_One_Post_Should_Return_201_Created() throws Exception {
		//given
		PostDto postDto = new PostDto();
		//when
		mockMvc.perform(MockMvcRequestBuilders.post("/v1.0/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postDto))
				.secure(true)
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
	}
	
	@Test
	public void put_One_Post_Should_Return_OK() throws Exception {
		//given
		PostDto postDto = new PostDto();
		postDto.setId(1L);
		//when
		mockMvc.perform(MockMvcRequestBuilders.put("/v1.0/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postDto))
				.secure(true)
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
}
