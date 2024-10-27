package task.management.app.taks.management.ai.services;

import task.management.app.taks.management.ai.dtos.CommentDTO;
import task.management.app.taks.management.ai.models.Comment;
import task.management.app.taks.management.ai.models.Task;
import task.management.app.taks.management.ai.models.User;
import task.management.app.taks.management.ai.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, TaskService taskService) {

        this.commentRepository = commentRepository;
        this.userService = userService;
        this.taskService = taskService;
    }

    public Comment createComment(CommentDTO comment) {
        Optional<User> user = userService.getUserById(comment.getAuthorUserID());
        Optional<Task> task = taskService.getTaskById(comment.getTaskID());

//        User user = userService.getReferenceById(comment.getAuthorUserID());
//        Task task = taskService.getReferenceById(comment.getTaskID());

        // Create and save the Comment with only references to User and Task
        Comment newComment = Comment.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .content(comment.getContent())
                .author(user.get())       // Proxy reference, only ID is accessed
                .task(task.get())         // Proxy reference, only ID is accessed
                .build();

        return commentRepository.save(newComment);
    }

    public List<Comment> getCommentsByTask(Long taskId) {
        return commentRepository.findByTask_Id(taskId);
    }

    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public Comment updateComment(Comment comment) {
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}