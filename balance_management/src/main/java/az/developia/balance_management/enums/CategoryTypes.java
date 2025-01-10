package az.developia.balance_management.enums;

import az.developia.balance_management.exception.MyException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum CategoryTypes {

    INCOME,
    EXPENSE;

    @JsonCreator
    public static CategoryTypes fromString(String value) {
        try {
            return CategoryTypes.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MyException("Invalid category type!");
        }
    }

}
