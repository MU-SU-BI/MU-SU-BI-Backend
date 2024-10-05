package com.musubi.domain.community.dto;

import com.musubi.domain.community.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PostResponseDto {
	private Long postId;
	private final String title;
	private final String authorName;
	private final String createAt;
	private final int commentsCount;

	public static PostResponseDto of(Post post) {
		return PostResponseDto.builder()
			.postId(post.getId())
			.title(post.getTitle())
			.authorName(post.getGuardianAuthor().getNickname())
			.commentsCount(post.getComments().size())
			.createAt(post.getCreatedAt().toString())
			.build();
	}

}
