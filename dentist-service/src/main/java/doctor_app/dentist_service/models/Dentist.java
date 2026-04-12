package doctor_app.dentist_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;

@Data
@Document(collection = "dentists")
@AllArgsConstructor
@NoArgsConstructor
public class Dentist {

    @Id
    private String doctorId;

    private String firstName;
    private String lastName;
    private String specialization;
    private String phoneNumber;

    @Indexed(unique = true)
    private String email;

    private LocalDate dateOfBirth;
}
