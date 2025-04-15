package social.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import social.entity.Post;
import social.model.PostDto;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "postId", source = "id")
    @Mapping(target = "postText", source = "text")
    PostDto toDto(Post post);
}
