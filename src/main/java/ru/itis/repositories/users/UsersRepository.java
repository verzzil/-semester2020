package ru.itis.repositories.users;

import ru.itis.dto.UserDto;
import ru.itis.models.User;
import ru.itis.repositories.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    List<UserDto> findAll();

    Optional<UserDto> findById(Integer id);

    List<User> findAllByAge(Integer age);

    Optional<User> exitUser(String email);

    void updatePhoto(String photoName, Integer id);

    void updateFirstAndLastNameAndCity(String firstName, String LastName, String city, Integer id);

    void updatePassword(String hashPassword, Integer id);

    void like(Integer fromUserId, Integer toUserId, Integer countLikes);

    List<Integer> likedUsers(Integer currentSessionUserId);

    Optional<List<String>> getNamesWhereLiked(Integer id);

    Optional<List<UserDto>> getChattingUsers(Integer id);

    User findUserById(Integer id);

    void updateShortAndFullAbout(Integer id, String shortAbout, String fullAbout);

    Optional<List<UserDto>> findUsersByFilter(String gender, Integer fromAge, Integer toAge, String city);
}
