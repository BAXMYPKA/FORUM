package ru.shop.forum.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/forum")
public class ForumLoginController {
	
	@GetMapping(path = "/login")
	public String getForumLogin() {
		return "login_forum";
	}
	
	@PostMapping(path = "/login")
	public String postForumLogin() {
		return "login_forum";
	}
	
}
