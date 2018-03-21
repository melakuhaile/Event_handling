package com.codingdojo.javabeltexam.services;

import java.util.List;


import org.springframework.stereotype.Service;

import com.codingdojo.javabeltexam.models.Comment;
import com.codingdojo.javabeltexam.repositories.CommentRepository;

@Service
public class CommentService {
	private CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	public List<Comment> findCommentsByEvent(Long id) {
		return commentRepository.findCommentsByEvent(id);
	}
}