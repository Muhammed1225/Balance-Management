package az.developia.balance_management.handler;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private String message;
    private List<MyFieldError> fieldErrors;
}
