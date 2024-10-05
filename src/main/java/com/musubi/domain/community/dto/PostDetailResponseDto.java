package com.musubi.domain.community.dto;

import com.musubi.domain.community.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public final class PostDetailResponseDto {
	private final Long postId;
	private final String title;
	private final String content;
	private final String authorName;
	private final String createAt;

	public static PostDetailResponseDto of(Post post) {
		return PostDetailResponseDto.builder()
			.postId(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.createAt(post.getCreatedAt().toString())
			.authorName(post.getGuardianAuthor().getNickname())
			.build();
	}
}
