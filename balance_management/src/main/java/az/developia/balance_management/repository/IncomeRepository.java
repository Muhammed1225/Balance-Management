package az.developia.balance_management.repository;

import az.developia.balance_management.model.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Integer> {
}
