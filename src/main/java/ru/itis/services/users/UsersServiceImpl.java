package ru.itis.services.users;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.dto.UserDto;
import ru.itis.models.User;
import ru.itis.repositories.users.UsersRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<UserDto> getAll() {
        return maleFemaleSort(usersRepository.findAll());
    }

    @Override
    public void updatePhoto(String photoName, Integer id) {
        usersRepository.updatePhoto(photoName, id);
    }

    @Override
    public void updateFirstAndLastNameAndCity(String firstName, String lastName, String city, Integer id) {
        usersRepository.updateFirstAndLastNameAndCity(firstName, lastName, city, id);
    }

    @Override
    public void updatePassword(String pass, Integer id) {
        usersRepository.updatePassword(passwordEncoder.encode(pass), id);
    }

    @Override
    public void like(Integer fromUserId, Integer toUserId, Integer countLikes) {
        usersRepository.like(fromUserId, toUserId, countLikes);
    }

    @Override
    public List<Integer> likedUsers(Integer currentSessionUserId) {
        return usersRepository.likedUsers(currentSessionUserId);
    }

    @Override
    public Optional<List<String>> getNamesWhereLiked(Integer id) {
        return usersRepository.getNamesWhereLiked(id);
    }

    @Override
    public Optional<List<UserDto>> getChattingUsers(Integer id) {
        return usersRepository.getChattingUsers(id);
    }

    @Override
    public String getNameByUserId(Integer id) {
        UserDto user = findById(id).get();
        return user.getFirstName() + " " + user.getLastName();
    }

    @Override
    public boolean checkPass(Integer userId, String pass) {
        User user = usersRepository.findUserById(userId);

        return passwordEncoder.matches(pass, user.getHashPassword());
    }

    @Override
    public void updateShortAndFullAbout(Integer id, String shortAbout, String fullAbout) {
        usersRepository.updateShortAndFullAbout(id, shortAbout, fullAbout);
    }

    @Override
    public Optional<List<UserDto>> findUsersByFilter(String gender, String fromAge, String toAge, String city) {
        return usersRepository.findUsersByFilter(gender, Integer.parseInt(fromAge), Integer.parseInt(toAge), city);
    }

    @Override
    public Optional<UserDto> findById(Integer id) {
        return usersRepository.findById(id);
    }

    private List<UserDto> maleFemaleSort(List<UserDto> users) {
        List<UserDto> maleUsers = users.stream()
                .filter(user -> user.getGender().equals("М"))
                .collect(Collectors.toList());
        List<UserDto> femaleUsers = users.stream()
                .filter(user -> user.getGender().equals("Ж"))
                .collect(Collectors.toList());

        Collections.shuffle(maleUsers);
        Collections.shuffle(femaleUsers);

        List<UserDto> result = new ArrayList<>();
        int maleSize = maleUsers.size(), femaleSize = femaleUsers.size();
        int i = 0, j = 0, commonCounter = 0;
        while(i < maleSize || j < femaleSize) {
            if(commonCounter % 2 == 0) {
                if(i < maleSize) {
                    result.add(maleUsers.get(i));
                    i++;
                } else {
                    result.add(femaleUsers.get(j));
                    j++;
                }
            } else {
                if(j < femaleSize) {
                    result.add(femaleUsers.get(j));
                    j++;
                } else {
                    result.add(maleUsers.get(i));
                    i++;
                }
            }

            commonCounter++;
        }
        return result;
    }

}
