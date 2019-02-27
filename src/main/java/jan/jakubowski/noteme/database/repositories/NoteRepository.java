package jan.jakubowski.noteme.database.repositories;

import jan.jakubowski.noteme.database.entities.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {
    List<Note> findByTitleContaining(String phrase);

    List<Note> findByAuthorLoginAndTitleContainingIgnoreCase(String login, String phrase);

    List<Note> findByContentContaining(String phrase);

    List<Note> findByAuthorLoginAndContentContainingIgnoreCase(String login, String phrase);

    Page<Note> findByAuthorLogin(String login, Pageable pageable);

    Optional<Note> getOneById(Long id);
}
