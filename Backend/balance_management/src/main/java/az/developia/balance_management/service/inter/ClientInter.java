package az.developia.balance_management.service.inter;

import az.developia.balance_management.dto.request.ClientAddRequest;
import az.developia.balance_management.dto.response.ClientResponse;

public interface ClientInter {

    void add(ClientAddRequest request);

    ClientResponse findAll();

    void verifyEmail(Integer token);

}
