package az.developia.balance_management.service.impl;

import az.developia.balance_management.dto.request.AdminAddRequest;
import az.developia.balance_management.dto.response.AdminResponse;
import az.developia.balance_management.dto.response.AdminSingleResponse;
import az.developia.balance_management.model.AdminEntity;
import az.developia.balance_management.repository.AdminRepository;
import az.developia.balance_management.service.inter.AdminInter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService implements AdminInter {

    @Autowired
    private AdminRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void add(AdminAddRequest request) {
        AdminEntity admin = new AdminEntity();
        mapper.map(request, admin);
        repository.save(admin);
        userService.addAdmin(request);
        authorityService.addAdmin(request);
    }

    @Override
    public AdminResponse findAll() {
        AdminResponse response = new AdminResponse();
        List<AdminEntity> entities = repository.findAll();
        List<AdminSingleResponse> responses = new ArrayList<>();

        for (AdminEntity e : entities) {
            AdminSingleResponse singleResponse = new AdminSingleResponse();
            mapper.map(e, singleResponse);
            responses.add(singleResponse);
        }

        response.setAdmins(responses);
        return response;
    }

}
