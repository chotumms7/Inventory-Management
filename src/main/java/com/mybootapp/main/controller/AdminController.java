package com.mybootapp.main.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.mybootapp.main.model.User;
import com.mybootapp.main.service.MyUserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private MyUserService userService;
	
	@PostMapping("/add")
	public User add(@RequestBody User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole("ADMIN");
		return userService.insert(user);
	}
	

	
}
