package com.codingdojo.javabeltexam.controllers;

import java.security.Principal;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



import com.codingdojo.javabeltexam.models.Event;
import com.codingdojo.javabeltexam.models.User;
import com.codingdojo.javabeltexam.models.Comment;
import com.codingdojo.javabeltexam.services.EventService;
import com.codingdojo.javabeltexam.services.UserService;
import com.codingdojo.javabeltexam.services.CommentService;


@Controller
public class CommentController {
	private CommentService commentService;
	private EventService eventService;
	private UserService userService;
	
	public CommentController (CommentService commentService, EventService eventService, UserService userService) {
		this.commentService = commentService;
		this.eventService = eventService;
		this.userService = userService;
	}
	
	@PostMapping("/addComment")
	public String addComment(@Valid @ModelAttribute("comment") Comment comment, BindingResult result, Principal principal, Model model) {
		Event commentEvent = comment.getEvent();
		Event event = eventService.findEventById(commentEvent.getId());
		
		model.addAttribute("event", event);
		if (result.hasErrors()) {
			List<Comment> comments = commentService.findCommentsByEvent(event.getId());
			model.addAttribute("comments", comments);
			return "eventDetailsView";
		} else {
			String email = principal.getName();
			User currentUser = userService.findByEmail(email);
			
			comment.setCommenter(currentUser);
			commentService.saveComment(comment);
			
			List<Comment> comments = commentService.findCommentsByEvent(event.getId());
			model.addAttribute("comments", comments);
			return "eventDetailsView";
		}
	}
}
