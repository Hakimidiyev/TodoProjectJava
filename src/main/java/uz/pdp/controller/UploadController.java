package uz.pdp.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.daos.UploadsDao;
import uz.pdp.domains.Uploads;
import uz.pdp.service.UploadService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


@Controller
public class UploadController {

    private final Path rootPath= Path.of("/home/hakim/Desktop/files/upload");
    private final UploadsDao uploadsDao;
    private final UploadService uploadService;

    public UploadController(UploadsDao uploadsDao, UploadService uploadService) {
        this.uploadsDao = uploadsDao;
        this.uploadService = uploadService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file)throws IOException {
        Uploads uploads = uploadService.uploadFile(file);
        Files.copy(file.getInputStream(),rootPath.resolve(uploads.getGeneratedName()), StandardCopyOption.REPLACE_EXISTING);
        return "redirect:/upload";
    }

    @GetMapping("/download{file:.+}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable String filename){
        Uploads uploads=uploadsDao.findByGenerateName(filename);
        FileSystemResource fileSystemResource=new FileSystemResource(rootPath.resolve(filename));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploads.getMimeType()))
                .contentLength(uploads.getSize())
                //.header(uploads.getOriginalName())
                .body(fileSystemResource);

    }
}
