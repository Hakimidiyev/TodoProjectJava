package uz.pdp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.daos.AuthUserDao;
import uz.pdp.domains.AuthUser;
import uz.pdp.domains.Uploads;
import uz.pdp.dto.UserRegisterDto;


@Service
public class AuthService {
    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;
    private final UploadService uploadService;

    public AuthService(AuthUserDao authUserDao, PasswordEncoder passwordEncoder, UploadService uploadService) {
        this.authUserDao = authUserDao;
        this.passwordEncoder = passwordEncoder;
        this.uploadService = uploadService;
    }

    public AuthService(PasswordEncoder passwordEncoder,
                       AuthUserDao authUserDao,
                       UploadService uploadService) {
        this.passwordEncoder = passwordEncoder;
        this.authUserDao = authUserDao;
        this.uploadService = uploadService;
    }


    public String register(UserRegisterDto dto) {
        Long imageId = null;
        if (dto.photo() != null && !dto.photo().isEmpty()) {
            Uploads uploads = uploadService.uploadFile(dto.photo());
            imageId = uploads.getId();
        }

        AuthUser user = AuthUser.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .userImageId(imageId)
                .build();

        Long id = authUserDao.save(user);
        System.out.println(id);
        return String.valueOf(id);
    }

}
