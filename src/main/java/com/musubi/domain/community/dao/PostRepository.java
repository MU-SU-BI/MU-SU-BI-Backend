package com.musubi.domain.community.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musubi.domain.community.domain.Post;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	// List<Post> findByGuardianAuthorLocationDistrict(String di);

	List<Post> findPostByGuardianAuthor_Location_District(String district);
}
