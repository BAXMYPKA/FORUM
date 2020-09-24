package ru.shop.forum.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ForumIndexController {
	
	@GetMapping(path = {"/test"})
	public ModelAndView getIndex() {
		return new ModelAndView("index_forum");
	}
}
