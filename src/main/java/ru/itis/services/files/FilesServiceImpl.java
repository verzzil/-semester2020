package ru.itis.services.files;

import ru.itis.models.FileInfo;
import ru.itis.repositories.files.FilesRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class FilesServiceImpl implements FilesService {

    private FilesRepository filesRepository;

    public FilesServiceImpl(FilesRepository fileRepository) {
        this.filesRepository = fileRepository;
    }

    @Override
    public void saveFileToStorage(
            InputStream stream,
            Integer userId,
            String originalFileName,
            String contentType
    ) {
        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(originalFileName)
                .storageFileNameByUserId(userId)
                .type(contentType)
                .build();
        try {
            String fileName = fileInfo.getStorageFileNameByUserId() + "." + fileInfo.getType().split("/")[1];
            Files.deleteIfExists(
                    Paths.get(
                            "D://Projects/InfaHoWo/src/main/webapp/images/usersPhotos/" +
                            fileName
                    )
            );
            Files.copy(
                    stream,
                    Paths.get(
                            "D://Projects/InfaHoWo/src/main/webapp/images/usersPhotos/" +
                                    fileName
                    )
            );
            filesRepository.save(fileInfo);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeFileFromStorage(Integer userId, OutputStream stream) {
        Optional<FileInfo> fileInfo = filesRepository.findById(userId);
        if(fileInfo.isPresent()) {
            File file = new File(
                    "D://Projects/InfaHoWo/src/main/webapp/images/usersPhotos/" +
                            fileInfo.get().getStorageFileNameByUserId() + "." + fileInfo.get().getType().split("/")[1]
            );
            try {
                Files.copy(file.toPath(), stream);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

    }

    @Override
    public Optional<FileInfo> getFileInfo(Integer userId) {
        return filesRepository.findById(userId);
    }
}
