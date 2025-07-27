package uz.pdp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.daos.TodoDao;
import uz.pdp.domains.Todo;
import uz.pdp.service.TodoService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
     TodoDao todoDao;
    TodoService todoService;


    @GetMapping("/list")
    public String getAllTodos(Model model) {
        model.addAttribute("list", todoDao.findByAll());
        return "redirect:/todo/list";
    }

    @PostMapping("/update")
    public String updateTodo(@ModelAttribute Todo todo,String username) {
        todoService.updateTodo(todo,username);
        return "redirect:/todo/list";
    }

    @PostMapping("/add")
    public String addTodo(@ModelAttribute Todo todo) {
        todo.setCreated_at(LocalDateTime.now());
        todoDao.save(todo);
        return "redirect:/todo/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        todoDao.delete(id);
        return "redirect:/todo/list";
    }
}
