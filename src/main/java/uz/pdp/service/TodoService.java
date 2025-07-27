package uz.pdp.service;

import org.springframework.stereotype.Service;
import uz.pdp.config.security.SessionUser;
import uz.pdp.daos.TodoDao;
import uz.pdp.domains.Todo;
import uz.pdp.dto.todo.TodoCreateDto;
import uz.pdp.dto.todo.TodoUpdateDto;

import java.util.List;


@Service
public class TodoService {

    private final TodoDao todoDao;
    private final SessionUser sessionUser;

    public TodoService(TodoDao todoDao, SessionUser sessionUser) {
        this.todoDao = todoDao;
        this.sessionUser = sessionUser;
    }

    public void addTodo(TodoCreateDto dto) {
        long userId = sessionUser.getId();
        Todo todo = new Todo(dto.title(), dto.priority(), userId);
        todoDao.save(todo);
    }

    public List<Todo> getAll() {
        return todoDao.findAllByUserId(sessionUser.getUser().getId());
    }

    public void updateTodo(TodoUpdateDto dto) {
        long userId = sessionUser.getId();
        long todoId = dto.id();
        Todo todo = todoDao.findById(todoId);
        if (!todo.getUserId().equals(userId)) {
            throw new IllegalArgumentException("user can not update");
        }
        todo.setId(dto.id());
        todo.setTitle(dto.title());
        todo.setPriority(dto.priority());
        todoDao.update(todo);
    }

    public void delete(Long todoId) {
        long userId = sessionUser.getId();
        Todo todo = todoDao.findById(todoId);
        if (todo == null) {
            throw new IllegalArgumentException("todo is null");
        }
        if (!todo.getUserId().equals(userId)) {
            throw new IllegalArgumentException("user can not update");
        }
        todoDao.delete(todoId);
    }
}
