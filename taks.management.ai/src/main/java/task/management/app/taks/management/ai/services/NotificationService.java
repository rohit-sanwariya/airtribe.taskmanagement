package task.management.app.taks.management.ai.services;

import task.management.app.taks.management.ai.models.Notification;
import task.management.app.taks.management.ai.models.User;
import task.management.app.taks.management.ai.repository.NotificationRepository;
import task.management.app.taks.management.ai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // Create notification
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Get notification by ID
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    // Update notification
    public Notification updateNotification(Long id, boolean readStatus) {
        Notification existingNotification = notificationRepository.findById(id).orElse(null);
        if (existingNotification != null) {
            existingNotification.setReadStatus(readStatus);
            return notificationRepository.save(existingNotification);
        } else {
            return null;
        }
    }

    // Delete notification
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    // Get notifications by user ID
    public List<Notification> getNotificationsByUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return notificationRepository.findByUser_Id(userId);
        } else {
            return null;
        }
    }

    public Long getNotificationCountByUserId(Long userId){
        return notificationRepository.countByUser_Id(userId);
    }
}