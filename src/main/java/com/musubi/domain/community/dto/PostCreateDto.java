package com.musubi.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostCreateDto {
	private final Long userId;
	private final String title;
	private final String content;

}
