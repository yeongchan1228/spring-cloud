package springcloudstudy.usermicroservices.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor // ModelMapper 때문에 필요
@JsonInclude(JsonInclude.Include.NON_EMPTY) // Null 값인 필드를 버린다.
public class ResponseUserDto {
    private String email;
    private String username;
    private String userId;

    private List<ResponseOrder> orders = new ArrayList<>();

    public ResponseUserDto(String email, String name, String userId) {
        this.email = email;
        this.username = name;
        this.userId = userId;
    }
}
