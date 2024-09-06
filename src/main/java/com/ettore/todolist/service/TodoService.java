package com.ettore.todolist.service;

import com.ettore.todolist.model.Todo;
import com.ettore.todolist.model.User;
import com.ettore.todolist.repository.TodoRepository;
import com.ettore.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public List<Todo> getAllTodos(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .map(User::getId)
                .map(todoRepository::findByUserId)
                .orElseThrow();
    }

    public Optional<Todo> getTodoById(Principal principal, Long id) {
        User user = getUserFromPrincipal(principal);
        return todoRepository.findByIdAndUserId(id, user.getId());
    }

    public Todo createTodo(Principal principal, Todo todo) {
        LocalDateTime now = LocalDateTime.now();
        User user = getUserFromPrincipal(principal);
        todo.setUser(user);
        todo.setCreatedAt(now);
        todo.setUpdatedAt(now);
        todo.setCompleted(false);
        return todoRepository.save(todo);
    }

    public Optional<Todo> updateTodo(Principal principal, Long id, Todo todoDetails) {
        User user = getUserFromPrincipal(principal);
        return todoRepository.findByIdAndUserId(id, user.getId())
                .map(todo -> {
                    todo.setName(todoDetails.getName());
                    todo.setDescription(todoDetails.getDescription());
                    todo.setCompleted(todoDetails.isCompleted());
                    todo.setUpdatedAt(LocalDateTime.now());
                    return todoRepository.save(todo);
                });
    }

    public void deleteTodoById(Principal principal, Long id) {
        User user = getUserFromPrincipal(principal);
        todoRepository.deleteByIdAndUserId(id, user.getId());
    }

    private User getUserFromPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow();
    }
}