package jan.jakubowski.noteme.database.repositories;

import jan.jakubowski.noteme.database.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> getOneById(Long id);
    Optional<Role> getOneByName(String name);
}
