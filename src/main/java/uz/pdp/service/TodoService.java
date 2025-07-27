package uz.pdp.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.daos.AuthUserDao;
import uz.pdp.daos.TodoDao;
import uz.pdp.domains.AuthUser;
import uz.pdp.domains.Todo;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoDao todoDao;
    private final AuthUserDao authUserDao;

    public TodoService(TodoDao todoDao, AuthUserDao authUserDao) {
        this.todoDao = todoDao;
        this.authUserDao = authUserDao;
    }

    public Todo addTodo(Todo todo,String userName) {
        AuthUser authUser = authUserDao.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        todo.setAuthUsers(authUser);
        todo.setCreated_at(LocalDateTime.now());
        todoDao.save(todo);
        return todo;
    }

    public Optional<Todo> getAllTodo(String username){
        AuthUser user = authUserDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return todoDao.findByUserId(user.getId());
    }
    public void updateTodo(Todo todo,String username){
        AuthUser user = authUserDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if (!(user.getId().equals(todo.getAuthUser().getId()))) {
            throw new RuntimeException("user not update");
        }
        todoDao.update(todo);
    }
    public void delete(Todo todo,String username){
        AuthUser user = authUserDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if (!(user.getId().equals(todo.getAuthUsers().getId()))) {
            throw new RuntimeException("user not update");
        }
        todoDao.update(todo);
    }
}
