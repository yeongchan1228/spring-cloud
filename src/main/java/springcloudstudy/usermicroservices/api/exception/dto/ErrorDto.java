package springcloudstudy.usermicroservices.api.exception.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorDto {
    private String message;
    private List<ErrorDetailDto> errors = new ArrayList<>();
}
