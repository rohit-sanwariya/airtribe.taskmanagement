package task.management.app.taks.management.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task.management.app.taks.management.ai.models.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {



        List<Notification> findByUser_Id(Long userId);
    Long countByUser_Id(Long userId);

}