package doctor.app.treatment_service.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "treatments")
@NoArgsConstructor
@AllArgsConstructor
public class Treatment {
    @Id
    private String id;
    @NonNull
    private String patientId;
    @NonNull
    private  String dentistId;
    @NotBlank
    private String name;
    @Length(max = 500)
    private String description;

}
