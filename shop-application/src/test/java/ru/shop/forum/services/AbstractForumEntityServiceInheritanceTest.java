package ru.shop.forum.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.shop.forum.entities.AbstractForumEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AbstractForumEntityServiceInheritanceTest {

	@MockBean
	private PostService postService;
	
	@Test
	public void all_Inherited_Services_Should_Return_Correct_Types() {
		System.out.println();
		
	}
}