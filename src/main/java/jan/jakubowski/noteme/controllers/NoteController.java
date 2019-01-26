package jan.jakubowski.noteme.controllers;

import jan.jakubowski.noteme.services.NoteService;
import jan.jakubowski.noteme.services.dto.NoteDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping(value = "/api/users/{user}/notes", params = "content")
    public ResponseEntity notesWithContentContaining(@PathVariable(name = "user") String user,
                                                     @RequestParam(name = "content", required = true) String phrase) {
        if (isLoggedUser(user)) {
            return ResponseEntity.ok(noteService.findByContentContainingIgnoreCase(phrase));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping(value = "/api/users/{user}/notes", params = "title")
    public ResponseEntity notesWithTitleContaining(@PathVariable(name = "user") String user,
                                                   @RequestParam(name = "title", required = true) String phrase) {
        if (isLoggedUser(user)) {
            return ResponseEntity.ok(noteService.findByTitleContainingIgnoreCase(phrase));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
    public ResponseEntity addNote(@PathVariable(name = "user") String user, @RequestBody NoteDTO note) throws URISyntaxException, NotFoundException {
        if (isLoggedUser(user)) {
            NoteDTO result = noteService.addNoteToUser(note, user);
            return ResponseEntity
                    .created(new URI(String.format("/api/users/%s/notes/%d", user, result.id)))
                    .body(result);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
