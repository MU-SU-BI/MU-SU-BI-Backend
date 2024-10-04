package com.musubi.domain.community.dto;

import com.musubi.domain.community.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PostResponseDto {
	private final String title;
	private final String authorName;
	private final String createAt;

	public static PostResponseDto of(Post post) {
		return PostResponseDto.builder()
			.title(post.getTitle())
			.authorName(post.getGuardianAuthor().getNickname())
			.createAt(post.getCreatedAt().toString())
			.build();
	}

}
