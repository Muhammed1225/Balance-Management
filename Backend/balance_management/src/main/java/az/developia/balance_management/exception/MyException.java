package az.developia.balance_management.exception;

import lombok.Data;
import org.springframework.validation.BindingResult;

@Data
public class MyException extends RuntimeException {

    private BindingResult bindingResult;

    public MyException(String m) {
        super(m);
    }

    public MyException(String m, BindingResult bindingResult) {
        super(m);
        this.bindingResult = bindingResult;
    }

}
