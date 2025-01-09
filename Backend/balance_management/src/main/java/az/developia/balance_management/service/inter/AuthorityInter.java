package az.developia.balance_management.service.inter;

import az.developia.balance_management.dto.request.AdminAddRequest;
import az.developia.balance_management.dto.request.ClientAddRequest;

public interface AuthorityInter {

    void addAdmin(AdminAddRequest request);

    void addClient(ClientAddRequest request);

}
