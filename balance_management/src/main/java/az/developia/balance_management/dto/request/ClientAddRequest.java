package az.developia.balance_management.dto.request;

import az.developia.balance_management.annotations.IsPresent;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAddRequest {

    @NotNull
    @Size(min = 4, max = 15, message = "Username must be minimum 4, maximum 15 character!")
    @IsPresent
    private String username;

    @NotNull
    @Size(min = 8, max = 20, message = "Password must be minimum 8, maximum 20 character!")
    private String password;

    @NotNull
    @NotBlank(message = "The name is required!")
    private String name;

    @NotNull
    @Email(message = "It's not a true email address!")
    private String email;

}
