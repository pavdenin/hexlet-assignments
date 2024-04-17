package exercise.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

// BEGIN
@Getter
@Setter
public class GuestCreateDTO {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^\\+(\\d){11,13}$")
    private String phoneNumber;

    @Pattern(regexp = "^\\d{4}$")
    private String clubCard;

    @Future
    private LocalDate cardValidUntil;
}
// END
