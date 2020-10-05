package ru.shop.forum.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shop.forum.entities.Post;
import ru.shop.forum.services.PostService;
import ru.shop.forum.services.PrivateMessageService;
import ru.shop.repositories.UserRepository;

import java.util.*;

@WebMvcTest(controllers = {ForumIndexController.class, PostRestController.class, PrivateMessageRestController.class})
public class ForumRestControllersPathsTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PostRestController postRestController;
	
	@MockBean
	private PrivateMessageService privateMessageService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private PostService postService;
	
	@MockBean
	private ModelMapper modelMapper;
	
	@BeforeEach
	private void beforeEach() {
		Mockito.when(postService.getEntityClass()).thenReturn(Post.class);
	}
	
	@Test
	public void forum_Index_Page_Should_Return_Ok() throws Exception {
		//given
		mockMvc.perform(MockMvcRequestBuilders.get("/").secure(true))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
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
		//when
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1.0/posts/0").secure(true))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
}
