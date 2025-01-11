package az.developia.balance_management.service.impl;

import az.developia.balance_management.dto.request.ClientAddRequest;
import az.developia.balance_management.dto.response.ClientResponse;
import az.developia.balance_management.dto.response.ClientSingleResponse;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.model.ClientEntity;
import az.developia.balance_management.model.UserEntity;
import az.developia.balance_management.repository.ClientRepository;
import az.developia.balance_management.repository.UserRepository;
import az.developia.balance_management.service.inter.ClientInter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ClientService implements ClientInter {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void add(ClientAddRequest request) {
        ClientEntity client = new ClientEntity();
        Random random = new Random();
        Integer pin = random.nextInt(1_000, 10_000);
        mapper.map(request, client);
        client.setToken(pin);
        sendEmail(request.getEmail(), pin);
        repository.save(client);
        userService.addClient(request);
        authorityService.addClient(request);
    }

    @Override
    public ClientResponse findAll() {
        ClientResponse response = new ClientResponse();
        List<ClientEntity> entities = repository.findAll();
        List<ClientSingleResponse> responses = new ArrayList<>();

        for (ClientEntity e : entities) {
            ClientSingleResponse singleResponse = new ClientSingleResponse();
            mapper.map(e, singleResponse);
            responses.add(singleResponse);
        }

        response.setClients(responses);
        return response;
    }

    @Override
    public void verifyEmail(Integer token) {
        ClientEntity client = repository.findByToken(token);
        if (client == null) {
            throw new MyException("Invalid PIN! Please, try again!");
        }
        UserEntity user = userRepository.findByUsername(client.getUsername());
        user.setEnabled(1);
        client.setToken(null);
        repository.save(client);
        userRepository.save(user);
    }

    private void sendEmail(String to, Integer pin) {
        String verificationLink = "https://balance-management-production.up.railway.app/clients/verify-email?token=" + pin;
        String message = "Please verify your email by clicking the following link: " + verificationLink;
        emailService.sendEmail(to, "Verify Your Email!", message);
    }

}
