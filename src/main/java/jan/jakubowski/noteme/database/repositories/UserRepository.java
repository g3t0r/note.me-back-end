package jan.jakubowski.noteme.database.repositories;

import jan.jakubowski.noteme.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getOneById(Long id);
    Optional<User> getOneByLogin(String login);
}
