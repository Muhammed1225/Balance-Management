package az.developia.balance_management.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ClientResponse {

    private List<ClientSingleResponse> clients;

}
