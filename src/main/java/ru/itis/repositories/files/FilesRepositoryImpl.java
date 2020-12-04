package ru.itis.repositories.files;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.itis.models.FileInfo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class FilesRepositoryImpl implements FilesRepository {

    //language=SQL
    private final static String SQL_INSERT = "insert into user_photo(storage_file_name_by_user_id, original_file_name, type) values (?, ?, ?)";

    //language=SQL
    private final static String SQL_UPDATE = "update user_photo set original_file_name = ?, type = ? where storage_file_name_by_user_id = ?";

    //language=SQL
    private final static String SQL_SELECT_BY_ID = "select * from user_photo where storage_file_name_by_user_id = ?";

    private JdbcTemplate jdbcTemplate;

    public FilesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void save(FileInfo entity) {
        Optional<Boolean> isExistRow = Optional.ofNullable(jdbcTemplate.query(
                SQL_SELECT_BY_ID,
                new Object[]{entity.getStorageFileNameByUserId()},
                resultSet -> {
                    return resultSet.next();
                }
        ));

        if (isExistRow.isPresent()) {
            if (isExistRow.get())
                jdbcTemplate.update(
                        SQL_UPDATE,
                        entity.getOriginalFileName(),
                        entity.getType(),
                        entity.getStorageFileNameByUserId()
                );
            else
                jdbcTemplate.update(
                        SQL_INSERT,
                        entity.getStorageFileNameByUserId(),
                        entity.getOriginalFileName(),
                        entity.getType()
                );
        }


    }

    @Override
    public void update(FileInfo entity) {

    }

    @Override
    public void remove(FileInfo entity) {

    }

    @Override
    public void removeById(Integer id) {

    }

    @Override
    public Optional<FileInfo> findById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(
                SQL_SELECT_BY_ID,
                new Object[]{id},
                resultSet -> {
                    if (resultSet.next())
                        return FileInfo.builder()
                                .storageFileNameByUserId(resultSet.getInt("storage_file_name_by_user_id"))
                                .originalFileName(resultSet.getString("original_file_name"))
                                .type(resultSet.getString("type"))
                                .build();
                    return null;
                }
        ));
    }
}
