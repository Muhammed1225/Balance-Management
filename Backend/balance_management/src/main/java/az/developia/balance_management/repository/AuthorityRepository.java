package az.developia.balance_management.repository;

import az.developia.balance_management.model.AuthorityEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


@Transactional
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {

    @Query(value = "insert into authorities (username, authority) select ?1, authority from authority_list where admin = true",
            nativeQuery = true)
    @Modifying
    void addAdmin(String username);

    @Query(value = "insert into authorities (username, authority) select ?1, authority from authority_list where client = true",
            nativeQuery = true)
    @Modifying
    void addClient(String username);

}
