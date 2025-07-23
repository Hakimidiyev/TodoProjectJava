package uz.pdp.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.daos.AuthPermissionDao;
import uz.pdp.daos.AuthRoleDao;
import uz.pdp.daos.AuthUserDao;
import uz.pdp.domains.AuthPermission;
import uz.pdp.domains.AuthRole;
import uz.pdp.domains.AuthUser;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserDao authUserDao;
    private final AuthRoleDao authRoleDao;
    private final AuthPermissionDao authPermissionDao;


    public CustomUserDetailsService(AuthUserDao authUserDao, AuthRoleDao authRoleDao, AuthPermissionDao authPermissionDao) {
        this.authUserDao = authUserDao;
        this.authRoleDao = authRoleDao;
        this.authPermissionDao = authPermissionDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDao.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found by username '%s'".formatted(username)));
        Long userId = authUser.getId();
        List<AuthRole> roles = authRoleDao.findAllByUserId(userId);
        for (AuthRole role : roles) {
            List<AuthPermission> permissions = authPermissionDao.findAllByRoleId(role.getId());
            role.setPermissions(permissions);
        }
        authUser.setRoles(roles);
        return new CustomUserDetails(authUser);
    }
}
