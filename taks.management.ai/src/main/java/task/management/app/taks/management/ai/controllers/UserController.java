package task.management.app.taks.management.ai.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import task.management.app.taks.management.ai.dtos.UserDTO;
import task.management.app.taks.management.ai.dtos.UserUpdateDto;
import task.management.app.taks.management.ai.enums.Role;
import task.management.app.taks.management.ai.models.CustomUserDetails;
import task.management.app.taks.management.ai.models.User;
import task.management.app.taks.management.ai.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users") // Base URL for this controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // READ all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam("q") String query ) {
        return userService.searchUsers(query);
    }
    // READ a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE an existing user by ID
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userDetails, Authentication authentication) throws HttpClientErrorException.Forbidden {
        var user =  (CustomUserDetails) authentication.getPrincipal();
        boolean isAdmin = user.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()));
        boolean isSelf = user.getEmail().equals(userDetails.getEmail());
        if(!isSelf && !isAdmin){
            throw HttpClientErrorException.create(HttpStatus.FORBIDDEN, "Forbidden", null, null, null);
        }


        try {
            UserDTO updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

   
   @GetMapping("/profile")
   public ResponseEntity<UserDTO> getProfileInformation(Authentication authentication) {
       try {
           var userFromToken =  (CustomUserDetails) authentication.getPrincipal();
           User user = userService.getUserByEmail(userFromToken.getEmail());
           return new ResponseEntity<>(userService.convertToDto(user), HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
}
