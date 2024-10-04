package com.musubi.domain.community.domain;

import com.musubi.global.utils.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment extends BaseEntity {

	@Id
	private Long id;

	private String content;

	@ManyToOne
	private Post post;
}
