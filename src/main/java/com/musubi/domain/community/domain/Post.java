package com.musubi.domain.community.domain;

import java.util.List;

import com.musubi.domain.community.dao.CommentRepository;
import com.musubi.domain.user.domain.Guardian;
import com.musubi.global.utils.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String content;

	@OneToMany(mappedBy = "post")
	private List<Comment> comments;

	@ManyToOne
	private Guardian guardianAuthor;

	public void addNewComment(Comment comment) {
		this.comments.add(comment);
	}
}
