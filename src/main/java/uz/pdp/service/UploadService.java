package uz.pdp.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.daos.UploadsDao;
import uz.pdp.domains.Uploads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UploadService {
    private final Path rootPath = Path.of("/home/hakim/Desktop/files/upload");
    private final UploadsDao uploadsDao;

    public UploadService(UploadsDao uploadsDao) {
        this.uploadsDao = uploadsDao;
    }

    public Uploads uploadFile(MultipartFile file) throws IOException {
        Uploads uploads = Uploads.builder()
                .originalName(file.getOriginalFilename())
                .generatedName(UUID.randomUUID() + "." +
                        StringUtils.getFilenameExtension(file.getOriginalFilename()))
                .size(file.getSize())
                .mimeType(file.getContentType())
                .path("/upload/")
                .build();
        Uploads saved = uploadsDao.save(uploads);


        Files.copy(
                file.getInputStream(),
                rootPath.resolve(saved.getGeneratedName()),
                StandardCopyOption.REPLACE_EXISTING
        );

        return saved;
    }
}
