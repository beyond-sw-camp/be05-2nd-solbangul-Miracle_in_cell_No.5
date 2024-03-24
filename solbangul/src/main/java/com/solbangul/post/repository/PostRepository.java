package com.solbangul.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.solbangul.post.domain.Post;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("select p from Post p where p.room.id = :roomId")
	List<Post> findPostsByRoomId(@Param("roomId") Long roomId);

	// @Query("select p from Post p where p.room.id = :roomId")
	@Query("UPDATE Post SET deleteYn   = true ,deleteTime = NOW() WHERE id = :roomId")
	List<Post> delete(@Param("roomId") Long roomId);
}
