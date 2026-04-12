package doctor.app.patient_service.model;

import doctor.app.patient_service.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "patients")
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    private String patientId;

    private String name;
    private String surname;

    @Indexed(unique = true)
    private String email;

    @Pattern(
            regexp = "^\\+34\\d{8,11}$",
            message = "Phone number must start with +34 and contain between 10 and 15 digits"
    )
    private String phoneNumber;

    private String address;
    private LocalDate dateOfBirth;
    private Gender gender;

    private List<String> treatmentIds;
}

