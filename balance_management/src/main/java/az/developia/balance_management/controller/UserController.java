package az.developia.balance_management.controller;

import az.developia.balance_management.dto.request.PasswordUpdateRequest;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public void login() {}

    @PutMapping
    public void refreshPassword(@Valid @RequestBody PasswordUpdateRequest request, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException("There is a problem in sent data!", br);
        }
        service.refreshPassword(request);
    }
}
