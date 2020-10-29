package ru.shop.forum.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ForumIndexController {
	
	@GetMapping(path = {"/forum", "/forum/index"})
	public String getIndex() {
		return "index_forum";
	}
}
