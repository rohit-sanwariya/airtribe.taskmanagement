package task.management.app.taks.management.ai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import task.management.app.taks.management.ai.enums.Role;
import task.management.app.taks.management.ai.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByEmail(String email);



//     @Query("SELECT u FROM User u WHERE " +
//             "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//             "LOWER(u.firstname) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//             "LOWER(u.lastname) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//             "LOWER(u.role) LIKE LOWER(CONCAT('%', :query, '%'))")
     @Query("""
             SELECT u FROM User u WHERE
                          LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) OR
                          LOWER(u.firstname) LIKE LOWER(CONCAT('%', :query, '%')) OR
                          LOWER(u.lastname) LIKE LOWER(CONCAT('%', :query, '%')) OR
                          LOWER(u.role) LIKE LOWER(CONCAT('%', :query, '%'))
             """)
     List<User> searchUsers(@Param("query") String query);
}
