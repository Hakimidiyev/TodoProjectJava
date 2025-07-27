package uz.pdp.daos;

import lombok.NonNull;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.details.Todo;
import java.util.List;
import java.util.Optional;

@Repository
public class TodoDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public TodoDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(@NonNull Todo todo){
        var sql="insert into auth.todo (title,priority) values (:title,:priority);";
        var paramSource=new BeanPropertySqlParameterSource(Todo.class);
        namedParameterJdbcTemplate.update(sql,paramSource);
    }

    public void update(@NonNull Todo todo){
        var sql="update auth.todo set title=:title,priority=:priority where id=:id";
        var paramSource=new BeanPropertySqlParameterSource(Todo.class);
        namedParameterJdbcTemplate.update(sql,paramSource);
    }

    public void delete(@NonNull Long id){
        var sql="delete from auth.todo where id=:id";
        var paramSource=new MapSqlParameterSource("id",id);
        namedParameterJdbcTemplate.update(sql,paramSource);
    }

    public Optional<Todo> findById(@NonNull Long id){
        var sql="select * from auth.todo where id=:id";
        var paramSource=new MapSqlParameterSource("id",id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql,paramSource,BeanPropertyRowMapper.newInstance(Todo.class)));
    }

    public Optional<Todo> findByUserId(Long id){
        var sql="select * from auth.todo where authuser_id=:id";
        var paramSource=new MapSqlParameterSource("id",id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql,paramSource,BeanPropertyRowMapper.newInstance(Todo.class)));
    }

    public List<Todo> findByAll(){
        return namedParameterJdbcTemplate.query("select * from auth.todo",BeanPropertyRowMapper.newInstance(Todo.class));
    }
}
