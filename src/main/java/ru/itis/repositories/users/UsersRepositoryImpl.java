package ru.itis.repositories.users;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.dto.UserDto;
import ru.itis.models.User;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository {


    private JdbcTemplate jdbcTemplate;

    private RowMapper<UserDto> userDtoRowMapper = (resultSet, i) -> UserDto.builder()
            .id(resultSet.getInt("id"))
            .firstName(resultSet.getString("first_name"))
            .lastName(resultSet.getString("last_name"))
            .fullAbout(resultSet.getString("full_about"))
            .shortAbout(resultSet.getString("short_about"))
            .countLikes(resultSet.getInt("count_likes"))
            .city(resultSet.getString("city"))
            .gender(resultSet.getString("gender"))
            .age(resultSet.getInt("age"))
            .build();

    private ResultSetExtractor<UserDto> userDtoResultSetExtractor = resultSet -> {
        if (resultSet.next()) {
            return UserDto.builder()
                    .id(resultSet.getInt("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .shortAbout(resultSet.getString("short_about"))
                    .fullAbout(resultSet.getString("full_about"))
                    .countLikes(resultSet.getInt("count_likes"))
                    .city(resultSet.getString("city"))
                    .gender(resultSet.getString("gender"))
                    .age(resultSet.getInt("age"))
                    .role(resultSet.getString("role"))
                    .build();
        }
        return null;
    };

    private ResultSetExtractor<User> userResultSetExtractor = resultSet -> {
        if (resultSet.next()) {
            return User.builder()
                    .id(resultSet.getInt("id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .shortAbout(resultSet.getString("short_about"))
                    .fullAbout(resultSet.getString("full_about"))
                    .countLikes(resultSet.getInt("count_likes"))
                    .city(resultSet.getString("city"))
                    .gender(resultSet.getString("gender"))
                    .age(resultSet.getInt("age"))
                    .hashPassword(resultSet.getString("hash_password"))
                    .build();
        }
        return null;
    };

    //language=SQL
    private final static String SQL_INSERT = "insert into semestr_user(first_name, last_name, email, city, gender, age, hash_password) " +
            "values (?, ?, ?, ?, ?, ?, ?)";
    //language=SQL
    private final static String SQL_FIND_BY_EMAIL = "select * from semestr_user where email = ?";
    //language=SQL
    private final static String SQL_FIND_BY_ID = "select * from semestr_user where id = ?";
    //language=SQL
    private final static String SQL_FIND_ALL = "select * from semestr_user";
    //language=SQL
    private final static String SQL_UPDATE_PHOTO = "";
    //language=SQL
    private final static String SQL_UPDATE_NAMES = "update semestr_user set first_name = ?, last_name = ?, city = ? where id = ?";
    //language=SQL
    private final static String SQL_UPDATE_PASS = "update semestr_user set hash_password = ? where id = ?";
    //language=SQL
    private final static String SQL_UPDATE_LIKE = "update semestr_user set count_likes = ? where id = ?";
    //language=SQL
    private final static String SQL_INSERT_LIKE = "insert into users_likes(id_from_user, id_to_user) values (?, ?)";
    //language=SQL
    private final static String SQL_CHECK_LIKE = "select * from users_likes where id_from_user = ? and id_to_user = ?";
    //language=SQL
    private final static String SQL_DELETE_LIKE = "delete from users_likes where id_from_user = ? and id_to_user = ?";
    //language=SQL
    private final static String SQL_FIND_ALL_WHERE_LIKED = "select id_to_user from users_likes where id_from_user = ?";
    //language=SQL
    private final static String SQL_GET_NAMES_WHERE_LIKED = "select semestr_user.id, first_name, last_name from semestr_user join users_likes on semestr_user.id = users_likes.id_to_user where users_likes.id_from_user = ? order by users_likes.id desc";
    //language=SQL
    private final static String SQL_GET_CHATTING_USERS = "select distinct semestr_user.*, max(message.id) " + "max" + " from semestr_user, message where (message.id_from_user = ? or message.id_to_user = ?) and (semestr_user.id = message.id_from_user or semestr_user.id = message.id_to_user) and semestr_user.id != ? group by semestr_user.id order by max desc;";
    //language=SQL
    private final static String SQL_UPDATE_SHORT_FULL_ABOUT = "update semestr_user set short_about = ?, full_about = ? where id = ?";
    //language=SQL
    private final static String SQL_FIND_FILTER_USER = "select * from semestr_user where gender = ? and age >= ? and age <= ? and city = ?";
    //language=SQL
    private final static String SQL_FIND_FILTER_USER_WITHOUT_GENDER = "select * from semestr_user where age >= ? and age <= ? and city = ?";
    //language=SQL
    private final static String SQL_FIND_FILTER_USER_WITHOUT_CITY = "select * from semestr_user where gender = ? and age >= ? and age <= ?";
    //language=SQL
    private final static String SQL_FIND_FILTER_USER_WITHOUT_GENDER_AND_GENDER = "select * from semestr_user where age >= ? and age <= ?";

    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> findAllByAge(Integer age) {
        return null;
    }

    @Override
    public Optional<User> exitUser(String email) {
        return Optional.ofNullable(jdbcTemplate.query(SQL_FIND_BY_EMAIL, new Object[]{email}, userResultSetExtractor));
    }

    @Override
    public void updatePhoto(String photoName, Integer id) {

    }


    @Override
    public List<UserDto> findAll() {
        return jdbcTemplate.query(
                SQL_FIND_ALL,
                userDtoRowMapper
        );
    }

    @Override
    public void updateFirstAndLastNameAndCity(String firstName, String lastName, String city, Integer id) {
        jdbcTemplate.update(SQL_UPDATE_NAMES, firstName, lastName, city, id);
    }

    @Override
    public void updatePassword(String hashPassword, Integer id) {
        jdbcTemplate.update(SQL_UPDATE_PASS, hashPassword, id);
    }

    @Override
    public void like(Integer fromUserId, Integer toUserId, Integer countLikes) {
        Integer likeId = jdbcTemplate.query(
                SQL_CHECK_LIKE, new Object[]{fromUserId, toUserId},
                resultSet -> {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                    return null;
                }
        );

        if (likeId == null) {
            jdbcTemplate.update(SQL_INSERT_LIKE, fromUserId, toUserId);
            jdbcTemplate.update(SQL_UPDATE_LIKE, countLikes + 1, toUserId);
        } else {
            jdbcTemplate.update(SQL_DELETE_LIKE, fromUserId, toUserId);
            jdbcTemplate.update(SQL_UPDATE_LIKE, countLikes - 1, toUserId);
        }
    }

    @Override
    public List<Integer> likedUsers(Integer currentSessionUserId) {
        return jdbcTemplate.query(SQL_FIND_ALL_WHERE_LIKED, new Object[]{currentSessionUserId}, (resultSet, i) -> resultSet.getInt("id_to_user"));
    }

    @Override
    public Optional<List<String>> getNamesWhereLiked(Integer id) {
        return Optional.of(jdbcTemplate.query(
                SQL_GET_NAMES_WHERE_LIKED,
                new Object[]{id},
                (resultSet, i) -> resultSet.getString("first_name") + " " + resultSet.getString("last_name") + "/" + resultSet.getString("id")
        ));
    }

    @Override
    public Optional<List<UserDto>> getChattingUsers(Integer id) {
        return Optional.of(jdbcTemplate.query(
                SQL_GET_CHATTING_USERS,
                new Object[]{id, id, id},
                userDtoRowMapper
        ));
    }

    @Override
    public User findUserById(Integer id) {
        return jdbcTemplate.query(
                SQL_FIND_BY_ID,
                new Object[]{id},
                userResultSetExtractor
        );
    }

    @Override
    public void updateShortAndFullAbout(Integer id, String shortAbout, String fullAbout) {
        jdbcTemplate.update(
                SQL_UPDATE_SHORT_FULL_ABOUT,
                shortAbout,
                fullAbout,
                id
        );
    }

    @Override
    public Optional<List<UserDto>> findUsersByFilter(String gender, Integer fromAge, Integer toAge, String city) {
        if (gender.equals("any-gender") && city.equals("any-city")) {
            return Optional.of(
                    jdbcTemplate.query(
                            SQL_FIND_FILTER_USER_WITHOUT_GENDER_AND_GENDER,
                            new Object[]{fromAge, toAge},
                            userDtoRowMapper
                    )
            );
        } else if (gender.equals("any-gender")) {
            return Optional.of(
                    jdbcTemplate.query(
                            SQL_FIND_FILTER_USER_WITHOUT_GENDER,
                            new Object[]{fromAge, toAge, city},
                            userDtoRowMapper
                    )
            );
        } else if (city.equals("any-city")) {
            return Optional.of(
                    jdbcTemplate.query(
                            SQL_FIND_FILTER_USER_WITHOUT_CITY,
                            new Object[]{gender, fromAge, toAge},
                            userDtoRowMapper
                    )
            );
        } else {
            return Optional.of(
                    jdbcTemplate.query(
                            SQL_FIND_FILTER_USER,
                            new Object[]{gender, fromAge, toAge, city},
                            userDtoRowMapper
                    )
            );
        }
    }

    @Override
    public Optional<UserDto> findById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(SQL_FIND_BY_ID, new Object[]{id}, userDtoResultSetExtractor));
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(
                SQL_INSERT,
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getCity(),
                entity.getGender(),
                entity.getAge(),
                entity.getHashPassword()
        );
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void remove(User entity) {

    }

    @Override
    public void removeById(Integer id) {

    }

}