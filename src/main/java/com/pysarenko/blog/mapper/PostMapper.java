package com.pysarenko.blog.mapper;

import com.pysarenko.blog.dto.PostDto;
import com.pysarenko.blog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PostMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "authorUsername", ignore = true)
  Post toPost(PostDto postDto);

  PostDto toDto(Post post);
}
