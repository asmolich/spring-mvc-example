package com.travel.wifimap.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.travel.wifimap.domain.User;
import com.travel.wifimap.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/user/list")
	public String listUsers(Map<String, Object> map) {

		System.out.println(userService);
		map.put("User", new User());
		map.put("UserList", userService.findUsers());

		return "User";
	}

	@RequestMapping("/user")
	public String home() {
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("User") User User,
			BindingResult result) {

		userService.saveUser(User);

		return "redirect:/user/list";
	}

	@RequestMapping("/user/delete/{UserId}")
	public String deleteUser(@PathVariable("UserId") String UserId) {

		userService.removeUser(UserId);

		return "redirect:/user/list";
	}
}
