package springcloudstudy.usermicroservices.api.user.controller.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLoginDto {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less then two characters.")
    @Email(message = "It's not in email format.")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be equals or greater then six characters.")
    private String password;
}
