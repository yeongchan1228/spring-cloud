package springcloudstudy.usermicroservices.api.controller.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * RequestUserDto 내용을 바탕으로 User Entity에 값을 전달하기 위한 Dto
 */
@Data
public class UserDto {
    @Email(message = "It's not in email format.")
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, max = 50, message = "Email is 2-50 characters long.")
    private String email;

    @NotNull(message = "pwd cannot be null")
    @Size(min = 6, max = 50, message = "The password is 6-50 characters.")
    private String pwd;

    @NotNull(message = "name cannot be null")
    @Size(min = 2, message = "Name not be less then two characters.")
    private String name;
}
