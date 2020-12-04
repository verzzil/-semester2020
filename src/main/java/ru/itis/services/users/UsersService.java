package ru.itis.services.users;

import ru.itis.dto.UserDto;

import java.util.List;
import java.util.Optional;


public interface UsersService {
    List<UserDto> getAll();

    Optional<UserDto> findById(Integer id);

    void updatePhoto(String photoName, Integer id);

    void updateFirstAndLastNameAndCity(String firstName, String lastName, String city, Integer id);

    void updatePassword(String pass, Integer id);

    void like(Integer fromUserId, Integer toUserId, Integer countLikes);

    List<Integer> likedUsers(Integer currentSessionId);

    Optional<List<String>> getNamesWhereLiked(Integer id);

    Optional<List<UserDto>> getChattingUsers(Integer id);

    String getNameByUserId(Integer id);

    boolean checkPass(Integer userId, String pass);

    void updateShortAndFullAbout(Integer id, String shortAbout, String fullAbout);

    Optional<List<UserDto>> findUsersByFilter(String gender, String fromAge, String toAge, String city);
}
