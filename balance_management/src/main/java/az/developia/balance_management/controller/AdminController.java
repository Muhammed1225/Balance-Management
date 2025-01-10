package az.developia.balance_management.controller;

import az.developia.balance_management.dto.request.AdminAddRequest;
import az.developia.balance_management.dto.response.AdminResponse;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.service.impl.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admins")
public class AdminController {

    @Autowired
    private AdminService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Valid @RequestBody AdminAddRequest request, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException("There is a problem in sent data!", br);
        }
        service.add(request);
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public AdminResponse findAll() {
        return service.findAll();
    }

}
