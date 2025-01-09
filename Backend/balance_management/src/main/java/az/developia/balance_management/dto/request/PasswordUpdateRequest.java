package az.developia.balance_management.dto.request;

import az.developia.balance_management.annotations.IsOldPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordUpdateRequest {

    @IsOldPassword
    @NotBlank
    private String oldPassword;

    @NotNull
    @Size(min = 8, max = 20, message = "Password must be minimum 8, maximum 20 character!")
    private String newPassword;

}
