package com.musubi.domain.community.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musubi.domain.community.application.CommentService;
import com.musubi.domain.community.dto.CommentCreateDto;
import com.musubi.global.utils.DefaultDataResponse;
import com.musubi.global.utils.DefaultResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts/{postId}/comments")
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<?> createGuardianComment(@PathVariable Long postId,
		@RequestBody CommentCreateDto commentCreateDto, @RequestParam String type) {
		commentService.saveNewComment(postId, commentCreateDto, type);
		return ResponseEntity.status(201).body(new DefaultResponse(201, "댓글이 성공적으로 작성되었습니다."));
	}

	@GetMapping
	public ResponseEntity<?> findAllPost(@PathVariable Long postId, @RequestParam Long userId,
		@RequestParam String type) {
		return ResponseEntity.ok()
			.body(new DefaultDataResponse<>(200, "댓글 조회 성공",
				commentService.findAllCommentsByPostId(postId, userId, type)));
	}
}
