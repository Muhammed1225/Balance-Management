package az.developia.balance_management.handler;

import az.developia.balance_management.exception.MyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class MyHandler {

    @ExceptionHandler
    public ErrorResponse handleException(MyException e) {
        ErrorResponse response = new ErrorResponse();
        BindingResult bindingResult = e.getBindingResult();

        if (bindingResult != null) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            List<MyFieldError> fieldErrors = new ArrayList<>();

            for (FieldError error : errors) {
                MyFieldError fieldError = new MyFieldError();
                fieldError.setField(error.getField());
                fieldError.setDefaultMessage(error.getDefaultMessage());
                fieldErrors.add(fieldError);
            }

            response.setFieldErrors(fieldErrors);
        }

        response.setMessage(e.getMessage());
        return response;
    }

}
