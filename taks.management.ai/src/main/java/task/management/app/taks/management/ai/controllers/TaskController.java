package task.management.app.taks.management.ai.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import task.management.app.taks.management.ai.dtos.TaskDTO;
import task.management.app.taks.management.ai.models.Task;
import task.management.app.taks.management.ai.models.User;
import task.management.app.taks.management.ai.services.TaskService;
import task.management.app.taks.management.ai.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    // Create a new task
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@RequestBody TaskDTO task) {
        Optional<User> user = userService.getUserById(task.getUserId());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Task newTask = Task.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .assignedUser(user.get())
                .dueDate(task.getDueDate().atStartOfDay())
                .priority(task.getPriority())
                .status(task.getStatus())
                .build();

         taskService.createTask(newTask);
         task.setTaskId(newTask.getId());
         task.setUserEmail(user.get().getEmail());
        return task;
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update task
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO task) {
        Optional<User> user = userService.getUserById(task.getUserId());
        Task newTask = Task.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .assignedUser(user.get())
                .dueDate(task.getDueDate().atStartOfDay())
                .status(task.getStatus())
                .build();
      taskService.updateTask(id, newTask)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
      return ResponseEntity.ok(task);
    }

    // Delete task
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}