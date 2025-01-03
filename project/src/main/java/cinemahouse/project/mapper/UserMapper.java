package cinemahouse.project.mapper;

import cinemahouse.project.dto.request.UserCreationRequest;
import cinemahouse.project.dto.response.UserResponse;
import cinemahouse.project.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
}
