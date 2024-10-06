package com.musubi.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class CommentCreateDto {
	private final Long userId;
	private final String content;
}
