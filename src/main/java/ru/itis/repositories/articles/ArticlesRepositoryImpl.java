package ru.itis.repositories.articles;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.dto.ArticleDto;
import ru.itis.models.Article;

import javax.sql.DataSource;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ArticlesRepositoryImpl implements ArticlesRepository {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SQL_FIND_ALL_BY_USER_ID = "select * from article where user_id = ? order by id desc";
    //language=SQL
    private final String SQL_FIND_ALL = "select * from article";
    //language=SQL
    private final String SQL_INSERT = "insert into article(text, count_likes, user_id) values (?, ?, ?)";
    //language=SQL
    private final String SQL_DELETE_BY_ID = "delete from article where id = ?";
    //language=SQL
    private final String SQL_DELETE_BY_ID_LIKES = "delete from article_likes where id = ?";
    //language=SQL
    private final String SQL_UPDATE_LIKE = "update article set count_likes = ? where id = ?";
    //language=SQL
    private final String SQL_INSERT_LIKE = "insert into article_likes(id, id_from_user, id_to_user) values (?, ?, ?)";
    //language=SQL
    private final String SQL_CHECK_LIKE = "select * from article_likes where id = ? and id_from_user = ?";
    //language=SQL
    private final String SQL_DELETE_LIKE = "delete from article_likes where id = ?";
    //language=SQL
    private final String SQL_FIND_ALL_WHERE_LIKED = "select id from article_likes where id_from_user = ?";

    public ArticlesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<List<ArticleDto>> findAll() {
        return Optional.of(jdbcTemplate.query(
                SQL_FIND_ALL,
                (resultSet, i) -> ArticleDto.builder()
                        .id(resultSet.getInt("id"))
                        .text(resultSet.getString("text"))
                        .countLikes(resultSet.getInt("count_likes"))
                        .userId(resultSet.getInt("user_id"))
                        .build()
                )
        );
    }

    @Override
    public Optional<List<ArticleDto>> findAllUserArticlesById(Integer id) {
        return Optional.of(jdbcTemplate.query(
                SQL_FIND_ALL_BY_USER_ID,
                new Object[]{id},
                (resultSet, i) -> ArticleDto.builder()
                        .id(resultSet.getInt("id"))
                        .text(resultSet.getString("text"))
                        .countLikes(resultSet.getInt("count_likes"))
                        .userId(resultSet.getInt("user_id"))
                        .build()
                )
        );
    }

    @Override
    public void like(Integer articleId, Integer fromUserId, Integer toUserId, Integer countLikes) {
        Integer likeId = jdbcTemplate.query(
                SQL_CHECK_LIKE, new Object[]{articleId, fromUserId},
                resultSet -> {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                    return null;
                }
        );

        if(likeId == null) {
            jdbcTemplate.update(SQL_INSERT_LIKE, articleId, fromUserId, toUserId);
            jdbcTemplate.update(SQL_UPDATE_LIKE, countLikes+1, articleId);
        }
        else {
            jdbcTemplate.update(SQL_DELETE_LIKE, articleId);
            jdbcTemplate.update(SQL_UPDATE_LIKE, countLikes-1, articleId);
        }
    }

    @Override
    public List<Integer> findAllIdsWhereLiked(Integer userId) {
        return jdbcTemplate.query(SQL_FIND_ALL_WHERE_LIKED, new Object[]{userId}, (resultSet, i) -> resultSet.getInt("id"));
    }

    @Override
    public void save(Article entity) {
        jdbcTemplate.update(
                SQL_INSERT,
                entity.getText(),
                entity.getCountLikes(),
                entity.getUserId()
        );
    }

    @Override
    public void update(Article entity) {

    }

    @Override
    public void remove(Article entity) {

    }

    @Override
    public void removeById(Integer id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
        jdbcTemplate.update(SQL_DELETE_BY_ID_LIKES, id);
    }
}
