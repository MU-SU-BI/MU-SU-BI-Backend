package com.musubi.domain.community.application;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.musubi.domain.community.dao.CommentRepository;
import com.musubi.domain.community.dao.PostRepository;
import com.musubi.domain.community.domain.Comment;
import com.musubi.domain.community.domain.Post;
import com.musubi.domain.community.dto.CommentCreateDto;
import com.musubi.domain.community.dto.CommentResponseDto;
import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.domain.user.domain.User;
import com.musubi.global.exception.BusinessLogicException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final GuardianRepository guardianRepository;
	private final UserRepository userRepository;

	public void saveNewComment(Long postId, CommentCreateDto commentCreateDto, String type) {
		if (type.equals("guardian")) {
			Post post = postRepository.findById(postId)
				.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Post ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			Guardian guardian = guardianRepository.findById(commentCreateDto.getUserId())
				.orElseThrow(
					() -> new BusinessLogicException("올바르지 않은 Guardian ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			if (guardian.notHasLocation()) {
				throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
			}

			Comment comment = Comment.builder()
				.content(commentCreateDto.getContent())
				.guardianAuthor(guardian)
				.post(post)
				.build();

			commentRepository.save(comment);
			post.addNewComment(comment);
		} else {
			Post post = postRepository.findById(postId)
				.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Post ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			User user = userRepository.findById(commentCreateDto.getUserId())
				.orElseThrow(
					() -> new BusinessLogicException("올바르지 않은 USER ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			if (user.notHasLocation()) {
				throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
			}

			Comment comment = Comment.builder()
				.content(commentCreateDto.getContent())
				.author(user)
				.post(post)
				.build();

			commentRepository.save(comment);
			post.addNewComment(comment);
		}
	}

	public List<CommentResponseDto> findAllCommentsByPostId(Long postId, Long userId, String type) {
		if (type.equals("guardian")) {
			Post post = postRepository.findById(postId)
				.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Post ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			Guardian guardian = guardianRepository.findById(userId)
				.orElseThrow(
					() -> new BusinessLogicException("올바르지 않은 Guardian ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			if (guardian.notHasLocation()) {
				throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
			}

			List<Comment> comments = commentRepository.findByPost(post);
			List<CommentResponseDto> response = new ArrayList<>();

			for (Comment comment : comments) {
				response.add(CommentResponseDto.of(comment, type));
			}

			return response;
		} else {
			Post post = postRepository.findById(postId)
				.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Post ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			User user = userRepository.findById(userId)
				.orElseThrow(
					() -> new BusinessLogicException("올바르지 않은 User ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			if (user.notHasLocation()) {
				throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
			}

			List<Comment> comments = commentRepository.findByPost(post);
			List<CommentResponseDto> response = new ArrayList<>();

			for (Comment comment : comments) {
				response.add(CommentResponseDto.of(comment, type));
			}

			return response;
		}
	}
}
