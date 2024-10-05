package com.musubi.domain.community.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musubi.domain.community.application.GuardianCommentService;
import com.musubi.domain.community.application.GuardianPostService;
import com.musubi.domain.community.dto.GuardianCommentCreateDto;
import com.musubi.domain.community.dto.GuardianPostCreateDto;
import com.musubi.global.utils.DefaultDataResponse;
import com.musubi.global.utils.DefaultResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/guardians/posts/{postId}/comments")
public class GuardianCommentController {
	private final GuardianCommentService guardianCommentService;

	@PostMapping
	public ResponseEntity<?> createGuardianComment(@PathVariable Long postId,
		@RequestBody GuardianCommentCreateDto guardianCommentCreateDto) {
		guardianCommentService.saveNewComment(postId, guardianCommentCreateDto);
		return ResponseEntity.status(201).body(new DefaultResponse(201, "댓글이 성공적으로 작성되었습니다."));
	}

	@GetMapping
	public ResponseEntity<?> findAllPost(@PathVariable Long postId, @RequestParam Long guardianId) {
		return ResponseEntity.ok()
			.body(new DefaultDataResponse<>(200, "댓글 조회 성공",
				guardianCommentService.findAllCommentsByPostId(postId, guardianId)));
	}
}
