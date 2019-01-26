package jan.jakubowski.noteme.services;

import jan.jakubowski.noteme.database.entities.Note;
import jan.jakubowski.noteme.database.entities.User;
import jan.jakubowski.noteme.database.repositories.NoteRepository;
import jan.jakubowski.noteme.database.repositories.UserRepository;
import jan.jakubowski.noteme.services.dto.NoteDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

     public List<NoteDTO> findByContentContainingIgnoreCase(String phrase) {
        return noteRepository
                .findByContentContaining(phrase)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<NoteDTO> findByTitleContainingIgnoreCase(String phrase) {
        return noteRepository
                .findByTitleContaining(phrase)
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

    public NoteDTO addNoteToUser(NoteDTO dto, String login) throws NotFoundException {
        Optional<User> userOptional = userRepository.getOneByLogin(login);
        if (userOptional.isPresent()) {
            Note result = noteRepository.save(new Note(dto.id, dto.title, dto.content, userOptional.get()));
            return mapToDTO(result);
        } else {
            throw new NotFoundException("no user with provided username");
        }
    }

    private NoteDTO mapToDTO(Note note) {
        return new NoteDTO(note.getId(), note.getTitle(), note.getContent());
    }

}
