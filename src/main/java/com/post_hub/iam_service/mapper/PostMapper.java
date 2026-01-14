package com.post_hub.iam_service.mapper;

import com.post_hub.iam_service.model.dto.post.PostDto;
import com.post_hub.iam_service.model.dto.post.PostSearchDto;
import com.post_hub.iam_service.model.entities.Post;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.UpdatePostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {

    PostDto toPostDto(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Post createPost(PostRequest postRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updatePost(@MappingTarget Post post, UpdatePostRequest updatePostRequest);

    PostSearchDto toPostSearchDto(Post post);
}
