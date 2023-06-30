package com.linkin.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.linkin.model.UserDTO;
import com.linkin.model.UserPrincipal;
import com.linkin.service.UserService;
import com.linkin.utils.RoleEnum;

@RestController
@RequestMapping("/api")
public class UserAPIController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/member/me")
	private UserDTO me(@RequestParam(name = "notificationId", required = false) String notificationId) {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserDTO userDTO = new UserDTO();
		userDTO.setId(currentUser.getId());
		userDTO.setDeviceId(notificationId);

		return userService.getUserById(currentUser.getId());
	}

	@PostMapping(value = "/member/logout")
	private void logout(@RequestParam(name = "notificationId", required = false) String notificationId) {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserDTO userDTO = new UserDTO();
		userDTO.setId(currentUser.getId());
		userDTO.setDeviceId(notificationId);

	}

	@PutMapping("/member/profile")
	public @ResponseBody UserDTO updateUser(@RequestBody UserDTO userDTO) {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		userDTO.setId(currentUser.getId());
		userService.updateProfile(userDTO);
		return userDTO;
	}

	@PutMapping("/member/password")
	public @ResponseBody void changePassword(@RequestBody UserDTO userDTO) {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		userDTO.setId(currentUser.getId());

		userService.changePassword(userDTO);
	}

	@PostMapping("/register")
	public UserDTO register(@RequestBody UserDTO userDTO) {
		userDTO.setRoleId((long) RoleEnum.PATIENT.getRoleId());
		userDTO.setEnabled(true);
		userService.addUser(userDTO);
		return userDTO;
	}

}
