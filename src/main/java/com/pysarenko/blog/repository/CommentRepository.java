package com.pysarenko.blog.repository;

import com.pysarenko.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

  Page<Comment> findAllByPostId(String postId, Pageable pageable);
}
