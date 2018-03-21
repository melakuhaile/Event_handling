package com.codingdojo.javabeltexam.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.javabeltexam.models.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

	@Query("SELECT c FROM Comment c WHERE c.event.id = ?1 ORDER BY c.createdAt ASC")
	List<Comment> findCommentsByEvent(Long id);
}