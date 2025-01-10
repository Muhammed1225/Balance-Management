package az.developia.balance_management.service.impl;

import az.developia.balance_management.dto.request.AdminAddRequest;
import az.developia.balance_management.dto.request.ClientAddRequest;
import az.developia.balance_management.model.AuthorityEntity;
import az.developia.balance_management.repository.AuthorityRepository;
import az.developia.balance_management.service.inter.AuthorityInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService implements AuthorityInter {

    @Autowired
    private AuthorityRepository repository;

    @Override
    public void addAdmin(AdminAddRequest request) {
        repository.addAdmin(request.getUsername());
    }

    @Override
    public void addClient(ClientAddRequest request) {
        repository.addClient(request.getUsername());
    }

}
