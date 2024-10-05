package com.musubi.domain.community.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.musubi.domain.community.dao.CommentRepository;
import com.musubi.domain.community.dao.PostRepository;
import com.musubi.domain.community.domain.Comment;
import com.musubi.domain.community.domain.Post;
import com.musubi.domain.community.dto.CommentResponseDto;
import com.musubi.domain.community.dto.GuardianCommentCreateDto;
import com.musubi.domain.community.dto.PostResponseDto;
import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.global.exception.BusinessLogicException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuardianCommentService {
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final GuardianRepository guardianRepository;

	public void saveNewComment(Long postId, GuardianCommentCreateDto guardianCommentCreateDto) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Post ID 입니다.", HttpStatus.BAD_REQUEST.value()));

		Guardian guardian = guardianRepository.findById(guardianCommentCreateDto.getGuardianId())
			.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Guardian ID 입니다.", HttpStatus.BAD_REQUEST.value()));

		if (guardian.notHasLocation()) {
			throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
		}

		Comment comment = Comment.builder()
			.content(guardianCommentCreateDto.getContent())
			.guardianAuthor(guardian)
			.post(post)
			.build();

		commentRepository.save(comment);
		post.addNewComment(comment);
	}

	public List<CommentResponseDto> findAllCommentsByPostId(Long postId, Long guardianId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Post ID 입니다.", HttpStatus.BAD_REQUEST.value()));

		Guardian guardian = guardianRepository.findById(guardianId)
			.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Guardian ID 입니다.", HttpStatus.BAD_REQUEST.value()));

		if (guardian.notHasLocation()) {
			throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
		}

		List<Comment> comments = commentRepository.findByPost(post);

		return comments.stream().map(CommentResponseDto::of).toList();
	}
}
