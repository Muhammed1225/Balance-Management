package az.developia.balance_management.dto.response;

import az.developia.balance_management.enums.CategoryTypes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CategorySingleResponse {

    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryTypes type;

}
