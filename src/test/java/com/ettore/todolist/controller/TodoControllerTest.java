package com.ettore.todolist.controller;

import com.ettore.todolist.model.Todo;
import com.ettore.todolist.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private final MockMvc mockMvc;

    public TodoControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @Test
    void testGetTodoById() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        when(todoService.getTodoById(any(), eq(1L))).thenReturn(Optional.of(todo));

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCreateTodo() throws Exception {
        Todo todo = new Todo();
        when(todoService.createTodo(any(), any(Todo.class))).thenReturn(todo);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Todo\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateTodo() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        when(todoService.updateTodo(any(), eq(1L), any(Todo.class))).thenReturn(Optional.of(todo));

        mockMvc.perform(put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Todo\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTodoById() throws Exception {
        doNothing().when(todoService).deleteTodoById(any(), eq(1L));

        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());
    }
}