package uz.pdp.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.daos.UploadsDao;
import uz.pdp.domains.Uploads;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
public class UploadController {

    private final Path rootPath="home/hakim/Desktop/files/upload";
    private final UploadsDao uploadsDao;

    public UploadController(UploadsDao uploadsDao) {
        this.uploadsDao = uploadsDao;
    }



    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file)throws IOException {
        Uploads uploads = Uploads.builder()
                .originalName(file.getOriginalFilename())
                .generatedName(UUID.randomUUID() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename()))
                .size(file.getSize())
                .mimeType(file.getContentType())
                .path("/upload/")
                .build();
        uploadsDao.save(uploads);
        Files.copy(file.getInputStream(),rootPath.resolve(uploads.getGeneratedName()), StandardCopyOption.REPLACE_EXISTING);
        return "redirect:/upload";
    }

    @GetMapping("/download{file:.+}")
    private ResponseEntity<FileSystemResource> downloadPage(@PathVariable String filename){
        Uploads uploads=uploadsDao.findByGenerateName(filename);
        FileSystemResource fileSystemResource=new FileSystemResource(rootPath.resolve(filename));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploads.getMimeType()))
                .contentLength(uploads.getSize())
                //.header(uploads.getOriginalName())
                .body(fileSystemResource);

    }
}
