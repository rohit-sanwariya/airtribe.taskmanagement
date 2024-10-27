package task.management.app.taks.management.ai.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import task.management.app.taks.management.ai.dtos.TaskDTO;
import task.management.app.taks.management.ai.models.Task;

import java.util.List;

    public interface TaskRepository extends JpaRepository<Task, Long> {

        @Query("SELECT new task.management.app.taks.management.ai.dtos.TaskDTO(t.id, t.title, t.description, t.dueDate,t.priority, t.status, t.assignedUser.id, t.assignedUser.email) FROM Task t")
        List<TaskDTO> findAllTaskDTOs();




}
