package uz.pdp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.domains.Uploads;

import java.io.IOException;
import java.nio.file.Files;

@Controller
public class UploadController {

    @GetMapping("upload")
    public String uploadPage(@ResponseBody){
        return "upload";
    }

    @PostMapping("upload")
    public String uploadFile(@RequestParam("file") MultipartFile file)throws IOException {

        Files.copy()

    }
}
