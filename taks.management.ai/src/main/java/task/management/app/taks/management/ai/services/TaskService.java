package task.management.app.taks.management.ai.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import task.management.app.taks.management.ai.dtos.TaskDTO;
import task.management.app.taks.management.ai.models.Task;
import task.management.app.taks.management.ai.repository.TaskRepository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task getReferenceById(Long id) {
        return taskRepository.getReferenceById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<TaskDTO> getAllTasks() {


        List<TaskDTO> taskDTOs = taskRepository.findAllTaskDTOs();



        return taskDTOs;
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setDueDate(updatedTask.getDueDate());
                    task.setStatus(updatedTask.getStatus());
                    task.setAssignedUser(updatedTask.getAssignedUser());
                    task.setPriority(updatedTask.getPriority());
                    return taskRepository.save(task);
                });
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public boolean existsById(Long taskID) {
        return taskRepository.existsById(taskID);
    }
}