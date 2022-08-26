package com.pysarenko.blog.repository;

import com.pysarenko.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findPostById(long id);

  List<Post> findPostsByAuthorId(long id);

}
