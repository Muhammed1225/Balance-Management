package az.developia.balance_management.repository;

import az.developia.balance_management.model.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Integer> {
}
