package com.pysarenko.blog.repository;

import com.pysarenko.blog.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

  List<Post> findAllByAuthorUsername(String id);

}
