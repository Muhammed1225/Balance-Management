package az.developia.balance_management.handler;

import lombok.Data;

@Data
public class MyFieldError {
    private String field;
    private String defaultMessage;
}
