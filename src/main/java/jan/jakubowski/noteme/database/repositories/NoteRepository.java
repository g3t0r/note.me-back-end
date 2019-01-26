package jan.jakubowski.noteme.database.repositories;

import jan.jakubowski.noteme.database.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByTitleContaining(String phrase);

    List<Note> findByAuthorLoginAndTitleContainingIgnoreCase(String login, String phrase);

    List<Note> findByContentContaining(String phrase);

    List<Note> findByAuthorLoginAndContentContainingIgnoreCase(String login, String phrase);

    List<Note> findByAuthorLogin(String login);

    Optional<Note> getOneById(Long id);
}
