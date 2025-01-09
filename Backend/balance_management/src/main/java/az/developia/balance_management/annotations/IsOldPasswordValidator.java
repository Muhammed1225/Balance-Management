package az.developia.balance_management.annotations;

import az.developia.balance_management.model.UserEntity;
import az.developia.balance_management.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class IsOldPasswordValidator implements ConstraintValidator<IsOldPassword, String> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public boolean isValid(String oldPassword, ConstraintValidatorContext context) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            return false;
        }

        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username);

        if (user == null || user.getPassword() == null || user.getEnabled() == 0) {
            return false;
        }

        return encoder.matches(oldPassword, user.getPassword().substring(8));
    }
}