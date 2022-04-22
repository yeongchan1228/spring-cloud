package springcloudstudy.usermicroservices.api.exception.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springcloudstudy.usermicroservices.api.exception.dto.ErrorDetailDto;
import springcloudstudy.usermicroservices.api.exception.dto.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionAdvice {

//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto validationError(MethodArgumentNotValidException e){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Sign Up Error!");
        e.getFieldErrors().forEach(
                error -> {
                    errorDto.getErrors().add(new ErrorDetailDto(error.getField(), error.getDefaultMessage()));
                }
        );
        return errorDto;
    }
}
