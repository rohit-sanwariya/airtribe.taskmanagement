package task.management.app.taks.management.ai;

import task.management.app.taks.management.ai.dtos.UserCreateDto;
import task.management.app.taks.management.ai.dtos.UserDTO;
import task.management.app.taks.management.ai.dtos.UserUpdateDto;
import task.management.app.taks.management.ai.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    UserDTO updateUser(Long id, UserUpdateDto userDetails);
    void deleteUser(Long id);
    User create(UserCreateDto user);
    User getUserByEmail(String email);
    UserDTO getUserByToken();
}
