package az.developia.balance_management.repository;

import az.developia.balance_management.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {
    ClientEntity findByToken(Integer token);

    ClientEntity findByUsername(String username);
}
