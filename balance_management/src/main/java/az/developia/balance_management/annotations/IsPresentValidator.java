package az.developia.balance_management.annotations;

import az.developia.balance_management.model.UserEntity;
import az.developia.balance_management.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class IsPresentValidator implements ConstraintValidator<IsPresent, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        UserEntity user = userRepository.findByUsername(username);
        return user == null;
    }

}
