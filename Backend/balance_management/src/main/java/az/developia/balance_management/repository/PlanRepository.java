package az.developia.balance_management.repository;

import az.developia.balance_management.model.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PlanRepository extends JpaRepository<PlanEntity, Integer> {

    List<PlanEntity> findByUsername(String username);

    @Query(value = "select * from plans where begin_date = ?1 and end_Date = ?2 and username = ?3", nativeQuery = true)
    PlanEntity findByDates(LocalDate beginDate, LocalDate endDate, String username);

}
