package ru.shop.forum.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForumIndexController {
	
	@GetMapping(path = {"/test"})
	public String getIndex() {
		return "forum_index";
	}
}
