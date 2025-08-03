package uz.pdp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserRegisterDto{
    @NotBlank(message = "username.null")
    private String username;
    @NotBlank(message = "password.null")
    private String password;
    @NotBlank(message = "image can not be blank")
    private MultipartFile photo;
}
