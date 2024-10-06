package com.musubi.domain.community.dto;

import com.musubi.domain.community.domain.Comment;
import com.musubi.domain.community.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CommentResponseDto {
	private final Long commentId;
	private final String content;
	private final String authorName;
	private final String createAt;

	public static CommentResponseDto of(Comment comment) {

		String authorName = comment.getGuardianAuthor().getNickname() == null ? comment.getAuthor().getNickname() :
			comment.getGuardianAuthor().getNickname();

		return CommentResponseDto.builder()
			.commentId(comment.getId())
			.content(comment.getContent())
			.authorName(authorName)
			.createAt(comment.getCreatedAt().toString())
			.build();
	}

}
