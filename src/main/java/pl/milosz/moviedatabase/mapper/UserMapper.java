package pl.milosz.moviedatabase.mapper;

import pl.milosz.moviedatabase.dto.MovieDto;
import pl.milosz.moviedatabase.dto.UserDto;
import pl.milosz.moviedatabase.entity.Movie;
import pl.milosz.moviedatabase.entity.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(String.valueOf(user.getRole()))
                .build();
    }
    public static User toEntity(UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role(User.Role.valueOf(userDto.getRole()))
                .build();
    }
}
