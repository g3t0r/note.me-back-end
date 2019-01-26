package jan.jakubowski.noteme.controllers;

import jan.jakubowski.noteme.exceptions.AccessingDifferentUserException;
import jan.jakubowski.noteme.exceptions.UserDoesNotExistException;
import jan.jakubowski.noteme.exceptions.UserDoesNotOwnTheNoteException;
import jan.jakubowski.noteme.services.NoteService;
import jan.jakubowski.noteme.services.dto.NoteDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping(value = "/api/users/{user}/notes", params = "content")
    public List<NoteDTO> notesWithContentContaining(@PathVariable(name = "user") String user,
                                                    @RequestParam(name = "content", required = true) String phrase) {
        if (isLoggedUser(user)) {
            return noteService.findByContentContainingIgnoreCase(user, phrase);
        }

        throw new AccessingDifferentUserException();
    }

    @GetMapping(value = "/api/users/{user}/notes", params = "title")
    public List<NoteDTO> notesWithTitleContaining(@PathVariable(name = "user") String user,
                                                  @RequestParam(name = "title", required = true) String phrase) {
        if (isLoggedUser(user)) {
            return noteService.findByTitleContainingIgnoreCase(user, phrase);
        }
        throw new AccessingDifferentUserException();
    }

    @GetMapping("/api/users/{user}/notes")
    @ResponseBody
    public ResponseEntity notesFromUser(@PathVariable(name = "user") String user) {

        if (isLoggedUser(user)) {
            return ResponseEntity.ok(noteService.fetchUserNotes(user));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/api/users/{user}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public NoteDTO addNote(@PathVariable(name = "user") String user, @RequestBody NoteDTO note) throws URISyntaxException, NotFoundException {
        if (isLoggedUser(user)) {
            return noteService.addNoteToUser(note, user);
        }
        throw new AccessingDifferentUserException();
    }

    @DeleteMapping("/api/users/{user}/notes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void deleteNote(@PathVariable(name = "user") String user, @PathVariable(name = "id") Long id) {
        if (isLoggedUser(user)) {
            noteService.removeNoteFromUser(user, id);
            return;
        }
        throw new AccessingDifferentUserException();
    }

    @PatchMapping("/api/users/{user}/notes/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public NoteDTO modifyNote(@PathVariable(name = "user") String user,
                              @PathVariable(name = "id") Long id,
                              @RequestBody Map<String, Object> updates) {
        if (isLoggedUser(user)) {
            return noteService.modifyNote(user, id, updates);
        } else
            throw new AccessingDifferentUserException();
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User does not exist")
    public void userDoesNotExist() {
    }

    @ExceptionHandler(UserDoesNotOwnTheNoteException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User does not own the note")
    public void userDoesNotOwnTheNote() {
    }

    @ExceptionHandler(AccessingDifferentUserException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Accessing different user data")
    public void accessingDifferentUserData() {
    }

    private boolean isLoggedUser(String login) {
        String loggedUser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        return loggedUser.equals(login);
    }

}
