package jan.jakubowski.noteme.services;

import jan.jakubowski.noteme.database.entities.Note;
import jan.jakubowski.noteme.database.entities.User;
import jan.jakubowski.noteme.database.repositories.NoteRepository;
import jan.jakubowski.noteme.database.repositories.UserRepository;
import jan.jakubowski.noteme.exceptions.UserDoesNotExistException;
import jan.jakubowski.noteme.exceptions.UserDoesNotOwnTheNoteException;
import jan.jakubowski.noteme.services.dto.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    public List<NoteDTO> findByContentContainingIgnoreCase(String login, String phrase) {
        return noteRepository
                .findByAuthorLoginAndContentContainingIgnoreCase(login, phrase)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<NoteDTO> findByTitleContainingIgnoreCase(String login, String phrase) {
        return noteRepository
                .findByAuthorLoginAndTitleContainingIgnoreCase(login, phrase)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<NoteDTO> fetchUserNotes(String login) {
        return noteRepository
                .findByAuthorLogin(login)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public NoteDTO addNoteToUser(NoteDTO dto, String login) throws UserDoesNotExistException {
        User user = userRepository
                .getOneByLogin(login)
                .orElseThrow(UserDoesNotExistException::new);
        Note result = noteRepository.save(new Note(0, dto.title, dto.content, user));
        return mapToDTO(result);
    }

    public void removeNoteFromUser(String login, Long noteId) {
        if (userOwnTheNote(login, noteId)) {
            noteRepository.deleteById(noteId);
            return;
        }
        throw new UserDoesNotOwnTheNoteException();
    }

    public NoteDTO modifyNote(String login, Long noteId, Map<String, Object> updates) {
        if (userOwnTheNote(login, noteId)) {

            Note note = noteRepository.getOne(noteId);

            if (updates.containsKey("title")) {
                note.setTitle((String) updates.get("title"));
            }

            if (updates.containsKey("content")) {
                note.setContent((String) updates.get("content"));
            }

            return mapToDTO(noteRepository.save(note));
        }

        throw new UserDoesNotOwnTheNoteException();
    }

    private boolean userOwnTheNote(String login, Long noteId) {
        return userRepository.getOneByLogin(login)
                .orElseThrow(UserDoesNotExistException::new)
                .getNotes()
                .stream()
                .anyMatch(note -> note.getId() == noteId);
    }

    private NoteDTO mapToDTO(Note note) {
        return new NoteDTO(note.getId(), note.getTitle(), note.getContent());
    }

}
