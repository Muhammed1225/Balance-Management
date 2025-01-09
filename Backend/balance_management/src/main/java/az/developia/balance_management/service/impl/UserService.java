package az.developia.balance_management.service.impl;

import az.developia.balance_management.dto.request.AdminAddRequest;
import az.developia.balance_management.dto.request.ClientAddRequest;
import az.developia.balance_management.dto.request.PasswordUpdateRequest;
import az.developia.balance_management.model.UserEntity;
import az.developia.balance_management.repository.UserRepository;
import az.developia.balance_management.service.inter.UserInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserInter {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void addAdmin(AdminAddRequest request) {
        UserEntity user = new UserEntity();
        String encode = encoder.encode(request.getPassword());
        user.setUsername(request.getUsername());
        user.setPassword("{bcrypt}" + encode);
        user.setEnabled(1);
        repository.save(user);
    }

    @Override
    public void addClient(ClientAddRequest request) {
        UserEntity user = new UserEntity();
        String encode = encoder.encode(request.getPassword());
        user.setUsername(request.getUsername());
        user.setPassword("{bcrypt}" + encode);
        user.setEnabled(0);
        repository.save(user);
    }

    @Override
    public void refreshPassword(PasswordUpdateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = repository.findByUsername(username);
        String encode = encoder.encode(request.getNewPassword());
        user.setPassword("{bcrypt}" + encode);
        repository.save(user);
    }

}
