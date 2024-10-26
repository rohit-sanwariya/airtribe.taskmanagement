package task.management.app.taks.management.ai.controllers;

import task.management.app.taks.management.ai.dtos.NotificationStatusDTO;
import task.management.app.taks.management.ai.models.Notification;
import task.management.app.taks.management.ai.models.User;
import task.management.app.taks.management.ai.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Create notification
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.createNotification(notification));
    }

    // Get all notifications
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    // Get notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    // Update notification
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody NotificationStatusDTO dto) {
        return ResponseEntity.ok(notificationService.updateNotification(id, dto.isReadStatus()));
    }

    // Delete notification
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    // Get notifications by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
    }
    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> getNotificationsCountByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationCountByUserId(userId));
    }
}