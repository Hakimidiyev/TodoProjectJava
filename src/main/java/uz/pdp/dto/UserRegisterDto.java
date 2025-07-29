package uz.pdp.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserRegisterDto(String username, String password, MultipartFile photo){}
