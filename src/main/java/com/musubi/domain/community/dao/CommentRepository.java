package com.musubi.domain.community.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musubi.domain.community.domain.Comment;
import com.musubi.domain.community.domain.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	// int countByPostId(long PostId);

	List<Comment> findByPost(Post post);
}
