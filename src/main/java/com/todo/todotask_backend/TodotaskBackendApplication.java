package com.todo.todotask_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*") // Allows cross-origin fetch requests from any domain
public class TodotaskBackendApplication {

    // Thread-safe in-memory storage for concurrent web requests
    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(TodotaskBackendApplication.class, args);
    }

    // Root path endpoint -> https://todotask-backend-qje0.onrender.com/
    @GetMapping("/")
    public String home() {
        return "TODOTASK Spring Boot Backend is running cleanly!";
    }

    // Health Check Endpoint -> https://todotask-backend-qje0.onrender.com/api/items/health
    @GetMapping("/api/items/health")
    public String health() {
        return "Backend is live!";
    }

    // GET /api/items -> Fetches task list
    @GetMapping("/api/items")
    public List<Task> getTasks() {
        return tasks;
    }

    // POST /api/items -> Adds a new task
    @PostMapping("/api/items")
    public Task addTask(@RequestBody Task task) {
        task.setId(System.currentTimeMillis());
        tasks.add(task);
        return task;
    }

    // DELETE /api/items/{id} -> Removes a task by ID
    @DeleteMapping("/api/items/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        tasks.removeIf(t -> t.getId() != null && t.getId().equals(id));
        return "Item deleted successfully";
    }

    // Task DTO Class
    public static class Task {
        private Long id;
        private String name;
        private String title;
        private String description;

        public Task() {}

        public Task(Long id, String name, String title, String description) {
            this.id = id;
            this.name = name;
            this.title = title;
            this.description = description;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}