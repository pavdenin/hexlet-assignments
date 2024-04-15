package exercise.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class ContactDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
