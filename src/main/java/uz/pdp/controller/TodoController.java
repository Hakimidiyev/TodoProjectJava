package uz.pdp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.config.security.CustomUserDetails;
import uz.pdp.config.security.SessionUser;
import uz.pdp.daos.TodoDao;
import uz.pdp.dto.todo.TodoCreateDto;
import uz.pdp.dto.todo.TodoUpdateDto;
import uz.pdp.exceptions.TodoNotFoundException;
import uz.pdp.service.TodoService;

@Controller
@RequestMapping("/todo")
public class TodoController {
    private final TodoDao todoDao;
    private final TodoService todoService;
    private final SessionUser sessionUser;

    @Autowired
    public TodoController(TodoDao todoDao, TodoService todoService, SessionUser sessionUser) {
        this.todoDao = todoDao;
        this.todoService = todoService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/todo")
    public String todoPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getAuthUser().getId()!=null) {
            model.addAttribute("user", userDetails.getAuthUser());
            return "todo";
        }
        return String.valueOf(new TodoNotFoundException("todo is null","/auth/login"));
    }

    @GetMapping("/list")
    public String getAllTodos(Model model) {
        Long userId = sessionUser.getId();
        model.addAttribute("list", todoDao.findAllByUserId(userId));
        model.addAttribute("todo",new TodoCreateDto());
        return "todo";
    }

    @PostMapping("/update")
    public String updateTodo(@ModelAttribute TodoUpdateDto todo) {
       todoService.updateTodo(todo);
        return "redirect:/todo/list";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("todo") TodoCreateDto todo, BindingResult errors,Model model) {
        if (errors.hasErrors()){
            model.addAttribute("list",todoDao.findAllByUserId(sessionUser.getId()));
            return "todo";
        }
        todoService.addTodo(todo);
        return "redirect:/todo/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        todoService.delete(id);
        return "redirect:/todo/list";
    }
}
