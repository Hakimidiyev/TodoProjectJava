package uz.pdp.daos;

import lombok.NonNull;
import lombok.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import uz.pdp.config.security.SessionUser;
import uz.pdp.domains.AuthUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class AuthUserDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SessionUser sessionUser;

    public AuthUserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, SessionUser sessionUser) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.sessionUser = sessionUser;
    }

    public Long save(@NonNull AuthUser authUser) {
        var sql = "insert into authuser(username,password) values(:username,:password)";
        var paramSource = new MapSqlParameterSource()
                .addValue("username", authUser.getUsername())
                .addValue("password", authUser.getPassword());
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, paramSource, keyHolder, new String[]{"id"});
        return (Long) keyHolder.getKeys().get("id");
    }

    public Optional<AuthUser> findByUsername(@NonNull String username) {
        var sql = "select * from authuser t where t.username = :username";
        var paramSource = new MapSqlParameterSource().addValue("username", username);
        try {
            var authUser = namedParameterJdbcTemplate.queryForObject(sql, paramSource, (rs, rowNum) -> AuthUser.builder()
                    .id(rs.getLong("id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .build());
            return Optional.of(authUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
