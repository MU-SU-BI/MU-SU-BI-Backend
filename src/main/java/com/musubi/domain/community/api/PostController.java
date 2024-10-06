package com.musubi.domain.community.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musubi.domain.community.application.PostService;
import com.musubi.domain.community.dto.PostCreateDto;
import com.musubi.global.utils.DefaultDataResponse;
import com.musubi.global.utils.DefaultResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController {
	private final PostService postService;

	@PostMapping
	public ResponseEntity<?> createGuardianPost(@RequestBody PostCreateDto postCreateDto,
		@RequestParam String type) {
		postService.createGuardianPost(postCreateDto, type);
		return ResponseEntity.status(201).body(new DefaultResponse(201, "게시물이 성공적으로 작성되었습니다."));
	}

	@GetMapping
	public ResponseEntity<?> findAllPost(@RequestParam Long userId, @RequestParam String type) {
		return ResponseEntity.ok()
			.body(new DefaultDataResponse<>(200, "게시물 조회 성공", postService.getAllPosts(userId, type)));
	}

	@GetMapping("{postId}")
	public ResponseEntity<?> findPostByPostId(@PathVariable Long postId, @RequestParam String type,
		@RequestParam Long userId) {
		return ResponseEntity.ok()
			.body(
				new DefaultDataResponse<>(200, "게시물 조회 성공",
					postService.findPostByPostId(postId, userId, type)));
	}
}
