package com.pysarenko.blog.mapper;

import com.pysarenko.blog.dto.CommentDto;
import com.pysarenko.blog.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "authorUsername", ignore = true)
  @Mapping(target = "post", ignore = true)
  Comment toComment(CommentDto commentDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "authorUsername", ignore = true)
  @Mapping(target = "post", ignore = true)
  Comment updateComment(@MappingTarget Comment storedComment, CommentDto updatedComment);

  CommentDto toDto(Comment comment);
}
