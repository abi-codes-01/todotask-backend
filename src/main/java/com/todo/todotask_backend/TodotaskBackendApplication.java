package com.todo.todotask_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/api/items") // Matches API_URL endpoint path
@CrossOrigin(origins = "*")    // Allows cross-origin fetch requests
public class TodotaskBackendApplication {

    private final List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(TodotaskBackendApplication.class, args);
    }

	@GetMapping("/")
public String home() {
    return "TODOTASK Spring Boot Backend is running cleanly!";
}

    // Health Check Endpoint
    @GetMapping("/health")
    public String health() {
        return "Backend is live!";
    }

    // GET /api/items
    @GetMapping
    public List<Task> getTasks() {
        return tasks;
    }

    // POST /api/items -> Receives { name, title, description }
    @PostMapping
    public Task addTask(@RequestBody Task task) {
        task.setId(System.currentTimeMillis()); // Sets numeric ID matching Date.now() style
        tasks.add(task);
        return task;
    }

    // DELETE /api/items/{id}
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        tasks.removeIf(t -> t.getId() != null && t.getId().equals(id));
        return "Item deleted successfully";
    }

    // Task DTO Class matching frontend field expectations exactly
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