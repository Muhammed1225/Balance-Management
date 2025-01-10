package az.developia.balance_management.repository;

import az.developia.balance_management.model.ExpenseWithCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseWithCategoriesRepository extends JpaRepository<ExpenseWithCategoriesEntity, Integer> {

    @Query(value = "select e.id, e.amount, e.date, c.name as category_name, c.id as category_id, e.username " +
            "from expenses e join categories c where e.category_id = c.id and e.username = ?3 " +
            "and e.date between ?1 and ?2", nativeQuery = true)
    List<ExpenseWithCategoriesEntity> filter(LocalDate beginDate, LocalDate endDate, String username);

    @Query(value = "select e.id, e.amount, e.date, c.name as category_name, c.id as category_id, e.username " +
            "from expenses e join categories c where e.category_id = c.id and e.id = ?1",
            nativeQuery = true)
    Optional<ExpenseWithCategoriesEntity> findExpenseById(Integer id);

}
