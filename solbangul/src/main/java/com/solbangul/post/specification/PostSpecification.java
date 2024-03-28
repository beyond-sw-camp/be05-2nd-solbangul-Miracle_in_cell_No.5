package com.solbangul.post.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.solbangul.post.domain.Category;
import com.solbangul.post.domain.Post;

public class PostSpecification {

	public static Specification<Post> likeContents(String content) {
		return new Specification<Post>() {
			@Override
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// 2) like
				return criteriaBuilder.like(root.get("content"), "%" + content + "%");
			}
		};
	}

	public static Specification<Post> likeTitle(String title) {
		return new Specification<Post>() {
			@Override
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(root.get("title"), "%" + title + "%");
			}
		};
	}

	public static Specification<Post> likeWriter(String writer) {
		return new Specification<Post>() {
			@Override
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(root.get("writer"), "%" + writer + "%");
			}
		};
	}

	public static Specification<Post> findByCategory(Category category) {
		return new Specification<Post>() {
			@Override
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("category"), category);
			}
		};
	}

	public static Specification<Post> findByRoomId(Long room_id) {
		return new Specification<Post>() {
			@Override
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("room").get("id"), room_id);
			}
		};
	}

}
