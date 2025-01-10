package az.developia.balance_management.repository;

import az.developia.balance_management.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAllByUsername(String username);

    @Query(value = "select * from categories where lower(name) like %?1% and type like %?2% and username = ?3", nativeQuery = true)
    List<CategoryEntity> searchCategory(String name, String type, String username);

}
