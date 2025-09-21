package com.turkcell.LibraryAppDb.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.LibraryAppDb.dto.user.request.LoginRequest;
import com.turkcell.LibraryAppDb.dto.user.request.RegisterRequest;
import com.turkcell.LibraryAppDb.dto.user.response.LoginResponse;
import com.turkcell.LibraryAppDb.dto.user.response.RegisteredResponse;
import com.turkcell.LibraryAppDb.service.UserService;

@RequestMapping("/api/v1/users")
@RestController
public class UsersController {

	private final UserService userService;

	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("register")
	public RegisteredResponse register(@RequestBody RegisterRequest request) {
		return userService.register(request);
	}

	@PostMapping("login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		return userService.login(request);
	}
}