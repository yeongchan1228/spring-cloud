package springcloudstudy.usermicroservices.api.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUserDto {
    private String email;
    private String name;
    private String userId;
}
