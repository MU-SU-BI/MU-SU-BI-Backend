package com.musubi.domain.community.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.musubi.domain.community.dao.PostRepository;
import com.musubi.domain.community.domain.Post;
import com.musubi.domain.community.dto.PostCreateDto;
import com.musubi.domain.community.dto.PostDetailResponseDto;
import com.musubi.domain.community.dto.PostResponseDto;
import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.dao.UserRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.domain.user.domain.User;
import com.musubi.global.exception.BusinessLogicException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final GuardianRepository guardianRepository;
	private final UserRepository userRepository;

	@Transactional
	public void createGuardianPost(PostCreateDto postCreateDto, String type) {

		if (type.equals("guardian")) {
			Guardian guardian = guardianRepository.findById(postCreateDto.getUserId())
				.orElseThrow(
					() -> new BusinessLogicException("올바르지 않은 GuardianId 입니다.", HttpStatus.BAD_REQUEST.value()));

			if (guardian.notHasLocation()) {
				throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
			}

			Post post = Post.builder()
				.title(postCreateDto.getTitle())
				.content(postCreateDto.getContent())
				.guardianAuthor(guardian)
				.build();

			postRepository.save(post);

		} else if (type.equals("user")) {
			User user = userRepository.findById(postCreateDto.getUserId())
				.orElseThrow(
					() -> new BusinessLogicException("올바르지 않은 GuardianId 입니다.", HttpStatus.BAD_REQUEST.value()));

			if (user.notHasLocation()) {
				throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
			}

			Post post = Post.builder()
				.title(postCreateDto.getTitle())
				.content(postCreateDto.getContent())
				.userAuthor(user)
				.build();

			postRepository.save(post);
		}
	}

	@Transactional
	public List<PostResponseDto> getAllPosts(Long userId, String type) {
		if (type.equals("guardian")) {
			Guardian guardian = guardianRepository.findById(userId)
				.orElseThrow(
					() -> new BusinessLogicException("올바르지 않은 Guardian ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			if (guardian.notHasLocation()) {
				throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
			}

			List<Post> posts = postRepository.findPostByGuardianAuthor_Location_District(
				guardian.getLocationDistrict());

			return posts.stream().map(PostResponseDto::of).toList();
		} else {
			User user = userRepository.findById(userId)
				.orElseThrow(
					() -> new BusinessLogicException("올바르지 않은 USER ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			if (user.notHasLocation()) {
				throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
			}

			List<Post> posts = postRepository.findPostByGuardianAuthor_Location_District(
				user.getLocationDistrict());

			List<PostResponseDto> response = new ArrayList<>();

			for (Post post : posts) {
				response.add(PostResponseDto.of(post));
			}

			return response;
		}
	}

	public PostDetailResponseDto findPostByPostId(Long postId, Long userId, String type) {
		if (type.equals("guardian")) {
			Guardian guardian = guardianRepository.findById(userId)
				.orElseThrow(
					() -> new BusinessLogicException("올바르지 않은 Guardian ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			if (guardian.notHasLocation()) {
				throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
			}

			Post post = postRepository.findById(postId)
				.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Post ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			return PostDetailResponseDto.of(post);
		} else {
			User user = userRepository.findById(userId)
				.orElseThrow(
					() -> new BusinessLogicException("올바르지 않은 Guardian ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			if (user.notHasLocation()) {
				throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
			}

			Post post = postRepository.findById(postId)
				.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Post ID 입니다.", HttpStatus.BAD_REQUEST.value()));

			return PostDetailResponseDto.of(post);
		}
	}
}
