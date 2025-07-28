package uz.pdp.daos;

import lombok.NonNull;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.domains.Uploads;

@Component
public class UploadsDao {
    private final NamedParameterJdbcTemplate template;

    public UploadsDao(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    public void save(@NonNull Uploads uploads){
        var sql="insert into uploads (originalname,generatedname,size,mimetype,path) values (:originalName,:generatedName,:size,:mimeType,:path);";
        var paramSource=new BeanPropertySqlParameterSource(uploads);
        template.update(sql,paramSource);
    }

    public Uploads findByGenerateName(String filename) {
        String sql="select * from uploads where generatedName=:generatedName";
        var paramSource=new MapSqlParameterSource(
                "generatedName",filename);
        return template.queryForObject(sql,paramSource, BeanPropertyRowMapper.newInstance(Uploads.class));
    }
}
