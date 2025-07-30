package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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


    public void register(UserRegisterDto dto) {
        Long imageId = null;
        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            Uploads uploads = uploadService.uploadFile(dto.getPhoto());
            imageId = uploads.getId();
        }
        AuthUser user = AuthUser.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .userImageId(imageId)
                .build();
        authUserDao.save(user);
    }

}
