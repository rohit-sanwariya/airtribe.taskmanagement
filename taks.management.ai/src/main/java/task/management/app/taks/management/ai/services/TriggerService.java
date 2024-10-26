package task.management.app.taks.management.ai.services;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;



@Service
public class TriggerService {

    private final JdbcTemplate jdbcTemplate;
    
    public TriggerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void createTriggerIfNotExists() {
        String dropTriggerSql = "DROP TRIGGER IF EXISTS after_task_update";
        String createTriggerSql = """
                CREATE TRIGGER after_task_update
               AFTER INSERT OR UPDATE ON tasks
                FOR EACH ROW
                BEGIN
                    -- Initialize an empty message variable
                    DECLARE message TEXT;
                IF OLD IS NULL THEN
                            SET message = CONCAT('New task created with title "', NEW.title, '", description "', NEW.description, '", due date "', NEW.dueDate, '", status "', NEW.status, '", and assigned to user ID ', NEW.assigned_userid, '.');
                ELSE
                    -- Check for updates in individual columns
                    IF (OLD.title <> NEW.title) THEN
                        SET message = CONCAT('Task title changed from "', OLD.title, '" to "', NEW.title, '".');
                    END IF;
                
                    IF (OLD.description <> NEW.description) THEN
                        IF message IS NULL THEN
                            SET message = CONCAT('Task description changed from "', OLD.description, '" to "', NEW.description, '".');
                        ELSE
                            SET message = CONCAT(message, ' Task description changed from "', OLD.description, '" to "', NEW.description, '".');
                        END IF;
                    END IF;
                
                    IF (OLD.dueDate <> NEW.dueDate) THEN
                        IF message IS NULL THEN
                            SET message = CONCAT('Task due date changed from "', OLD.dueDate, '" to "', NEW.dueDate, '".');
                        ELSE
                            SET message = CONCAT(message, ' Task due date changed from "', OLD.dueDate, '" to "', NEW.dueDate, '".');
                        END IF;
                    END IF;
                
                    IF (OLD.status <> NEW.status) THEN
                        IF message IS NULL THEN
                            SET message = CONCAT('Task status changed from "', OLD.status, '" to "', NEW.status, '".');
                        ELSE
                            SET message = CONCAT(message, ' Task status changed from "', OLD.status, '" to "', NEW.status, '".');
                        END IF;
                    END IF;
                
                    IF (OLD.assigned_userid <> NEW.assigned_userid) THEN
                        IF message IS NULL THEN
                            SET message = CONCAT('Task assigned to user ID ', NEW.assigned_userid, ' from ', OLD.assigned_userid, '.');
                        ELSE
                            SET message = CONCAT(message, ' Task assigned to user ID ', NEW.assigned_userid, ' from ', OLD.assigned_userid, '.');
                        END IF;
                    END IF;
                
                    -- Insert a new notification if any changes were found
                    IF message IS NOT NULL THEN
                        INSERT INTO notification (userid, content, read_status, created_at)
                        VALUES (
                            NEW.assigned_userid,
                            message,
                            FALSE,
                            NOW()
                        );
                    END IF;
               END IF;
                END;""";

        // Execute the SQL commands using JdbcTemplate
        jdbcTemplate.execute(dropTriggerSql);
        jdbcTemplate.execute(createTriggerSql);
    }
}
