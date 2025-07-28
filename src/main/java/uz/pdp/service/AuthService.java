package uz.pdp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.daos.AuthUserDao;
import uz.pdp.domains.AuthUser;
import uz.pdp.dto.UserRegisterDto;


@Service
public class AuthService {
    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthUserDao authUserDao, PasswordEncoder passwordEncoder) {
        this.authUserDao = authUserDao;
        this.passwordEncoder = passwordEncoder;
    }


    public Long register(UserRegisterDto dto) {
//        boolean exists = authUserDao.existsUserByUsername(dto.username());
//        if (exists)
//            throw new IllegalArgumentException();


        AuthUser user = AuthUser.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .build();
        Long id = authUserDao.save(user);
        System.out.println(id);
        return id;
    }
}
