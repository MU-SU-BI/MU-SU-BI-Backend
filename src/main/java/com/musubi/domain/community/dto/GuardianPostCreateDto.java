package com.musubi.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GuardianPostCreateDto {
	private final Long guardianId;
	private final String title;
	private final String content;

}
