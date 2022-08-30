package com.pysarenko.blog.mapper;

import com.pysarenko.blog.dto.PostDto;
import com.pysarenko.blog.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface PostMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "authorUsername", ignore = true)
  Post toPost(PostDto postDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "authorUsername", ignore = true)
  Post updatePost(@MappingTarget Post storedPost, PostDto updatedPost);

  PostDto toDto(Post post);
}
