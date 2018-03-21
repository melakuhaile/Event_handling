package com.codingdojo.javabeltexam.controllers;

import java.security.Principal;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.javabeltexam.models.Event;
import com.codingdojo.javabeltexam.models.User;
import com.codingdojo.javabeltexam.services.EventService;
import com.codingdojo.javabeltexam.services.UserService;
import com.codingdojo.javabeltexam.validators.UserValidator;

@Controller
public class UserController {
	private UserService userService;
	private UserValidator userValidator;
	private EventService eventService;
	
	public UserController(UserService userService, UserValidator userValidator, EventService eventService) {
		this.userService = userService;
		this.userValidator = userValidator;
		this.eventService = eventService;
	}
	
	@RequestMapping("/login")
	public String loginAndReg(@Valid @ModelAttribute("user") User user, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials. Please try again.");
		}
		
		if (logout != null) {
			model.addAttribute("logoutMessage", "Logout Successful!");
		}
		
		return "loginReg";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result) {
		userValidator.validate(user, result);
		
		if (result.hasErrors()) {
			return "loginReg";
		} else {
			userService.saveWithUserRole(user);
			return "redirect:/events";
		}
	}
	
	@RequestMapping(value = {"/", "/events"})
	public String eventsDashboard(Principal principal, Model model, @Valid @ModelAttribute("event") Event event) {
		String email = principal.getName();
		User currentUser = userService.findByEmail(email);
		
		List<Object[]> eventsInState = eventService.findEventsInState(currentUser.getState());
		List<Object[]> eventsOutOfState = eventService.findEventsOutOfState(currentUser.getState());
		
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("eventsInState", eventsInState);
		model.addAttribute("eventsOutOfState", eventsOutOfState);
		return "eventsView";
	}
}