package uz.pdp.daos;

import lombok.NonNull;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import uz.pdp.domains.Uploads;

@Component
public class UploadDao {
    private final NamedParameterJdbcTemplate template;

    public UploadDao(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    public void save(@NonNull Uploads uploads){
        var sql="insert into uploads (originalname,generatedname,size,mimetype,path) values (:originalName,:generatedName,:size,:mimeType,:path);";
        var paramSource=new BeanPropertySqlParameterSource(uploads);
        template.update(sql,paramSource);
    }
}
