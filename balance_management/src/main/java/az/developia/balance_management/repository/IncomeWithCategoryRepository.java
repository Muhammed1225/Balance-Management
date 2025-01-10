package az.developia.balance_management.repository;

import az.developia.balance_management.model.IncomeWithCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeWithCategoryRepository extends JpaRepository<IncomeWithCategoriesEntity, Integer> {

    @Query(value = "select i.id, i.amount, i.date, c.name as category_name, c.id as category_id, i.username " +
            "from incomes i join categories c where i.category_id = c.id and i.username = ?1 " +
            "and i.date between ?2 and ?3",
            nativeQuery = true)
    List<IncomeWithCategoriesEntity> filterIncomeCategories(String username, LocalDate beginDate, LocalDate endDate);

    @Query(value = "select i.id, i.amount, i.date, c.name as category_name, c.id as category_id, i.username " +
            "from incomes i join categories c where i.category_id = c.id and i.id = ?1",
            nativeQuery = true)
    Optional<IncomeWithCategoriesEntity> findIncomeById(Integer id);

}
