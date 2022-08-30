package com.pysarenko.blog.repository;

import com.pysarenko.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

  Page<Post> findAllByAuthorUsername(String id, Pageable pageable);

}
