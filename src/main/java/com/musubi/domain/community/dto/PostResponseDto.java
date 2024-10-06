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

	public static PostResponseDto of(Post post, String type) {

		String authorName = "";
		if (type.equals("guardian")) {
			authorName = post.getGuardianAuthor().getNickname();
		} else {
			authorName = post.getUserAuthor().getNickname();
		}

		return PostResponseDto.builder()
			.postId(post.getId())
			.title(post.getTitle())
			.authorName(authorName)
			.commentsCount(post.getComments().size())
			.createAt(post.getCreatedAt().toString())
			.build();
	}

}
