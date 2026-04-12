package doctor.app.notification_service.model;

import doctor.app.notification_service.enums.EventType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "notifications")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    private String eventId;
    @NotBlank
    private EventType eventType;
    private LocalDateTime occurredAt;
    private Map<String, Object> data;

}
