package com.musubi.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class GuardianCommentCreateDto {
	private final Long guardianId;
	private final String content;
}
