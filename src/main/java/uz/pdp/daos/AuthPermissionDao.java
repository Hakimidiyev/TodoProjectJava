package uz.pdp.daos;

import lombok.NonNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.domains.AuthPermission;
import java.util.Collections;
import java.util.List;

@Component
public class AuthPermissionDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuthPermissionDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    public List<AuthPermission> findAllByRoleId(@NonNull Long roleId) {
        var sql = "select ap.* from authrole_authpermission arap inner join authpermission ap on ap.id=arap.permission_id where arap.role_id =:roleId";
        var paramSource = new MapSqlParameterSource().addValue("roleId", roleId);
        try {
            return namedParameterJdbcTemplate.query(sql,paramSource,(rs, rowNum) -> AuthPermission.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .code(rs.getString("code"))
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
