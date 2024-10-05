package com.musubi.domain.community.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.musubi.domain.community.dao.PostRepository;
import com.musubi.domain.community.domain.Post;
import com.musubi.domain.community.dto.GuardianPostCreateDto;
import com.musubi.domain.community.dto.PostDetailResponseDto;
import com.musubi.domain.community.dto.PostResponseDto;
import com.musubi.domain.user.dao.GuardianRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.global.exception.BusinessLogicException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuardianPostService {
	private final PostRepository postRepository;
	private final GuardianRepository guardianRepository;


	@Transactional
	public void createGuardianPost(GuardianPostCreateDto guardianPostCreateDto) {
		Guardian guardian = guardianRepository.findById(guardianPostCreateDto.getGuardianId())
			.orElseThrow(() -> new BusinessLogicException("올바르지 않은 GuardianId 입니다.", HttpStatus.BAD_REQUEST.value()));

		if (guardian.notHasLocation()) {
			throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
		}

		Post post = Post.builder()
			.title(guardianPostCreateDto.getTitle())
			.content(guardianPostCreateDto.getContent())
			.guardianAuthor(guardian)
			.build();

		postRepository.save(post);
	}

	public List<PostResponseDto> getAllPosts(Long guardianId) {
		Guardian guardian = guardianRepository.findById(guardianId)
			.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Guardian ID 입니다.", HttpStatus.BAD_REQUEST.value()));

		if (guardian.notHasLocation()) {
			throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
		}

		List<Post> posts = postRepository.findPostByGuardianAuthor_Location_District(guardian.getLocationDistrict());

		return posts.stream().map(PostResponseDto::of).toList();
	}

	public PostDetailResponseDto findPostByPostId(Long postId, Long guardianId) {
		Guardian guardian = guardianRepository.findById(guardianId)
			.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Guardian ID 입니다.", HttpStatus.BAD_REQUEST.value()));

		if (guardian.notHasLocation()) {
			throw new BusinessLogicException("인증된 위치 정보가 없습니다.", HttpStatus.BAD_REQUEST.value());
		}

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new BusinessLogicException("올바르지 않은 Post ID 입니다.", HttpStatus.BAD_REQUEST.value()));

		return PostDetailResponseDto.of(post);
	}
}
