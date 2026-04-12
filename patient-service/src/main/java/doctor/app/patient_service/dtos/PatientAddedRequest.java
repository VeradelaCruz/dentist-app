package doctor.app.patient_service.dtos;

import doctor.app.patient_service.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAddedRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(
            regexp = "^\\+34\\d{8,11}$",
            message = "Phone number must start with +34 and contain between 10 and 15 digits"
    )
    private String phoneNumber;

    @Length(min = 10, max = 50, message = "Address must be between 10 and 50 characters")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    private List<String> treatmentIds;
}
