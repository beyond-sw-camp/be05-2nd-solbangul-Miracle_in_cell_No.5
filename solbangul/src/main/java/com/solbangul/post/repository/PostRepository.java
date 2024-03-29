package com.solbangul.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.solbangul.post.domain.Category;
import com.solbangul.post.domain.Post;
import com.solbangul.room.domain.Room;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

	@Query("select p from Post p where p.room.id = :roomId")
	List<Post> findPostsByRoomId(@Param("roomId") Long roomId);

	@Query("select p from Post p where p.room.id = :roomId")
	Page<Post> findPostsByRoomIdPaging(@Param("roomId") Long roomId, Pageable pageable);

	Page<Post> findAll(Specification<Post> spec, Pageable pagaable);

	@Query("select p from Post p where p.room.user.id = :sederUserId and p.room = :room and "
		+ "p.category = :category order by p.createdDate desc limit 1")
	Post findLastPost(@Param("sederUserId") Long sederUserId, @Param("room") Room room,
		@Param("category") Category category);

	@Query("select p from Post p where p.writer = :writer")
	List<Post> findAllByWriter(@Param("writer") String writer);

	@Modifying
	@Transactional
	@Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id")
	void updateByCount(@Param("id") Long id);

}
