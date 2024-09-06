package com.ettore.todolist.controller;

import com.ettore.todolist.model.Todo;
import com.ettore.todolist.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodos(Principal principal) {
        return todoService.getAllTodos(principal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(Principal principal, @PathVariable Long id) {
        Optional<Todo> todo = todoService.getTodoById(principal, id);
        return todo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Todo createTodo(Principal principal, @RequestBody Todo todo) {
        return todoService.createTodo(principal, todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(Principal principal, @PathVariable Long id, @RequestBody Todo todoDetails) {
        Optional<Todo> updatedTodo = todoService.updateTodo(principal, id, todoDetails);
        return updatedTodo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(Principal principal, @PathVariable Long id) {
        todoService.deleteTodoById(principal, id);
        return ResponseEntity.noContent().build();
    }
}