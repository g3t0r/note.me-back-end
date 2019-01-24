package jan.jakubowski.noteme.database.repositories;

import jan.jakubowski.noteme.database.entities.Privilege;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
    Optional<Privilege> getOneById(Long id);
    Optional<Privilege> getOneByName(String name);
}
