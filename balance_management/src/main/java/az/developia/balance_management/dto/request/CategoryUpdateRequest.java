package az.developia.balance_management.dto.request;

import az.developia.balance_management.enums.CategoryTypes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CategoryUpdateRequest {

    @NotNull
    @Positive
    private Integer id;

    @NotBlank(message = "The name is required!")
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryTypes type;

}
