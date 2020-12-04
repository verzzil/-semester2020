package ru.itis.repositories.files;

import ru.itis.models.FileInfo;
import ru.itis.repositories.CrudRepository;

import java.util.Optional;

public interface FilesRepository extends CrudRepository<FileInfo> {
    Optional<FileInfo> findById(Integer id);
}
