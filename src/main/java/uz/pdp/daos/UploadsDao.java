package uz.pdp.daos;

import lombok.NonNull;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import uz.pdp.domains.Uploads;

@Component
public class UploadsDao {
    private final NamedParameterJdbcTemplate template;
    private final DriverManagerDataSource dataSource;

    public UploadsDao(NamedParameterJdbcTemplate template, DriverManagerDataSource dataSource) {
        this.template = template;
        this.dataSource = dataSource;
    }


    /*public Uploads save(@NonNull Uploads uploads) {
        var sql = "insert into uploads (originalname,generatedname,size,mimetype,path) values (:originalName,:generatedName,:size,:mimeType,:path) returning *";
        var paramSource = new BeanPropertySqlParameterSource(uploads);
        Uploads savedUploads = template.queryForObject(sql, paramSource, Uploads.class);
        return savedUploads;
    }*/

    public Uploads save(@NonNull Uploads uploads) {
        String sql = """
                    insert into uploads (originalname, generatedname, size, mimetype, path)
                    values (:originalName, :generatedName, :size, :mimeType, :path)
                    returning *
                """;
        var paramSource = new BeanPropertySqlParameterSource(uploads);
        return template.queryForObject(
                sql,
                paramSource,
                new BeanPropertyRowMapper<>(Uploads.class)
        );
    }

    public Uploads findByGenerateName(String filename) {
        String sql = "select * from uploads where generatedName=:generatedName";
        var paramSource = new MapSqlParameterSource(
                "generatedName", filename);
        return template.queryForObject(sql, paramSource, BeanPropertyRowMapper.newInstance(Uploads.class));
    }

    public Uploads findById(Long id) {
        String sql = "SELECT * FROM uploads WHERE id = :id";
        var paramSource = new MapSqlParameterSource("id", id);
        return template.queryForObject(sql, paramSource, BeanPropertyRowMapper.newInstance(Uploads.class));
    }
}
