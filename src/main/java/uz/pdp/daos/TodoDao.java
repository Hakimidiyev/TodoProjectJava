package uz.pdp.daos;

import lombok.NonNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.domains.Todo;

import java.util.List;

@Repository
public class TodoDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public TodoDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(@NonNull Todo todo) {
        var sql = "insert into auth.todo (title, priority, user_id) " +
                "values (:title, :priority, :userId);";

        var paramSource = new MapSqlParameterSource()
                .addValue("title", todo.getTitle())
                .addValue("priority", todo.getPriority())
                .addValue("userId", todo.getUserId());

        namedParameterJdbcTemplate.update(sql, paramSource);
    }

    public void update(@NonNull Todo todo) {
        var sql = "update auth.todo set title=:title,priority=:priority where id=:id";
        var paramSource = new MapSqlParameterSource()
                .addValue("title", todo.getTitle())
                .addValue("priority", todo.getPriority())
                .addValue("id", todo.getId());
        namedParameterJdbcTemplate.update(sql, paramSource);
    }

    public void delete(@NonNull Long id) {
        var sql = "delete from auth.todo where id=:id";
        var paramSource = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, paramSource);
    }

    public Todo findById(Long id) {
        String sql = "select * from auth.todo where id =:id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Todo.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<Todo> findAllByUserId(Long userId) {
        var sql = "select * from auth.todo where user_id=:userId";
        var paramSource = new MapSqlParameterSource("userId", userId);
        return namedParameterJdbcTemplate.query(sql, paramSource, BeanPropertyRowMapper.newInstance(Todo.class));
    }
}
