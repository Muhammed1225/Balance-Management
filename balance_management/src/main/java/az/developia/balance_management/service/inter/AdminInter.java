package az.developia.balance_management.service.inter;

import az.developia.balance_management.dto.request.AdminAddRequest;
import az.developia.balance_management.dto.response.AdminResponse;

public interface AdminInter {

    void add(AdminAddRequest request);

    AdminResponse findAll();

}
