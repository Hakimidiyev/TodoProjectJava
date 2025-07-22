package uz.pdp.daos;

import lombok.NonNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.domains.AuthRole;
import java.util.Collections;
import java.util.List;

@Component
public class AuthRoleDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuthRoleDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    public List<AuthRole> findAllByUserId(@NonNull Long userId) {
        var sql = "select ar.* from authuser_authrole auar inner join authrole ar on ar.id=auar.role_id where auar.user_id=:userId";
        var paramSource = new MapSqlParameterSource().addValue("userId", userId);
        try {
            return namedParameterJdbcTemplate.query(sql,paramSource, (rs, rowNum) -> AuthRole.builder()
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
