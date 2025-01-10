package az.developia.balance_management.controller;

import az.developia.balance_management.dto.request.ClientAddRequest;
import az.developia.balance_management.dto.response.ClientResponse;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.service.impl.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> add(@Valid @RequestBody ClientAddRequest request, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException("There is a problem in sent data!", br);
        }
        service.add(request);
        return ResponseEntity.ok("Verification email sent. Please check your inbox.");
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ClientResponse findAll() {
        return service.findAll();
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") Integer token) {
        service.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully.");
    }

}
