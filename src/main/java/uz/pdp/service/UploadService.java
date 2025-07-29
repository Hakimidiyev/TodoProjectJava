package uz.pdp.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.daos.UploadsDao;
import uz.pdp.domains.Uploads;

import java.util.UUID;

@Service
public class UploadService {
    private final UploadsDao uploadsDao;

    public UploadService(UploadsDao uploadsDao) {
        this.uploadsDao = uploadsDao;
    }

    public Uploads uploadFile(MultipartFile file){
        Uploads uploads = Uploads.builder()
                .originalName(file.getOriginalFilename())
                .generatedName(UUID.randomUUID() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename()))
                .size(file.getSize())
                .mimeType(file.getContentType())
                .path("/upload/")
                .build();
        uploadsDao.save(uploads);
        return uploads;
    }
}
