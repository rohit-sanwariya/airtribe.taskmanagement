package task.management.app.taks.management.ai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task.management.app.taks.management.ai.IUserService;
import task.management.app.taks.management.ai.dtos.UserCreateDto;
import task.management.app.taks.management.ai.dtos.UserDTO;
import task.management.app.taks.management.ai.dtos.UserUpdateDto;
import task.management.app.taks.management.ai.enums.Role;
import task.management.app.taks.management.ai.models.User;
import task.management.app.taks.management.ai.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getReferenceById(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateDto userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> {
            user.setFirstname(userDetails.getFirstname());
            user.setLastname(userDetails.getLastname());
            user.setEmail(userDetails.getEmail());
            user.setDob(userDetails.getDob());
            user.setProfileImage(userDetails.getProfileImage());
             User udpatedUser = userRepository.save(user);
             return convertToDto(udpatedUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    public UserDTO convertToDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .createdat(user.getCreatedat())
                .updatedat(user.getUpdatedat())
                .dob(user.getDob())
                .role(user.getRole())
                .build();
    }
    public List<UserDTO> searchUsers(String query) {

            return userRepository.searchUsers(query).stream()
                    .map(user -> UserDTO.builder()
                            .id(user.getId())
                            .firstname(user.getFirstname())
                            .lastname(user.getLastname())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .profileImage(user.getProfileImage())
                            .dob(user.getDob())
                            .createdat(user.getCreatedat())
                            .updatedat(user.getUpdatedat())
                            .build())
                    .collect(Collectors.toList());

    }
    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public User create(UserCreateDto user) {
        User newUser =
        User.builder()
                .password(passwordEncoder.encode(user.getPassword()))
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .email(user.getEmail())
                .role(Role.USER)
                .createdat(LocalDateTime.now())
                .updatedat(LocalDateTime.now())
                .build();

       return userRepository.save(newUser);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found with email: " + email));
    }

    @Override
    public UserDTO getUserByToken() {
        return null;
    }

    public boolean existsById(Long authorUserID) {
        return userRepository.existsById(authorUserID);
    }
}
