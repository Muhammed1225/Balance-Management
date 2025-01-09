package az.developia.balance_management.service.inter;

import az.developia.balance_management.dto.request.AdminAddRequest;
import az.developia.balance_management.dto.request.ClientAddRequest;
import az.developia.balance_management.dto.request.PasswordUpdateRequest;

public interface UserInter {

    void addAdmin(AdminAddRequest request);

    void addClient(ClientAddRequest request);

    void refreshPassword(PasswordUpdateRequest request);

}
