package ru.itis.repositories.messages;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.dto.ArticleDto;
import ru.itis.dto.Message;
import javax.sql.DataSource;
import java.util.List;

public class MessagesRepositoryImpl implements MessagesRepository {

    //language=SQL
    private final static String SQL_INSERT = "insert into message (id_from_user, id_to_user, text) values (?, ?, ?)";
    //language=SQL
    private final static String SQL_GET_DIALOG = "select * from message where (id_from_user = ? and id_to_user = ?) or (id_from_user = ? and id_to_user = ?) order by id";
    private JdbcTemplate jdbcTemplate;

    public MessagesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Message entity) {
        jdbcTemplate.update(
                SQL_INSERT,
                entity.getFromUserId(),
                entity.getToUserId(),
                entity.getText()
        );
    }

    @Override
    public void update(Message entity) {

    }

    @Override
    public void remove(Message entity) {

    }

    @Override
    public void removeById(Integer id) {

    }

    @Override
    public List<Message> getDialog(Integer idFromUser, Integer idToUser) {
        return jdbcTemplate.query(
                SQL_GET_DIALOG,
                new Object[]{idFromUser, idToUser, idToUser, idFromUser},
                (resultSet, i) -> Message.builder()
                        .fromUserId(resultSet.getInt("id_from_user"))
                        .toUserId(resultSet.getInt("id_to_user"))
                        .text(resultSet.getString("text"))
                        .build()
        );
    }
}
