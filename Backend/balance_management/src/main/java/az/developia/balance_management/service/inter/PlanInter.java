package az.developia.balance_management.service.inter;

import az.developia.balance_management.dto.request.PlanAddRequest;
import az.developia.balance_management.dto.request.PlanUpdateRequest;
import az.developia.balance_management.dto.response.PlanResponse;

public interface PlanInter {

    void add(PlanAddRequest request);

    PlanResponse findPlans();

    void update(PlanUpdateRequest request);

    void delete(Integer id);

    PlanResponse findPlan(Integer id);

}
