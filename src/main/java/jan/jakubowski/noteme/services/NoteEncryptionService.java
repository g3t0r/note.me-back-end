package jan.jakubowski.noteme.services;

import jan.jakubowski.noteme.database.entities.Note;
import jan.jakubowski.noteme.services.dto.NoteDTO;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class NoteEncryptionService {
    public NoteDTO encryptNote(NoteDTO noteDTO) {
        TextEncryptor textEncryptor = Encryptors.text("password", "4fde492c6d29402a2418112934a22ead");
        noteDTO.content = textEncryptor.encrypt((noteDTO.content));
        noteDTO.title = textEncryptor.encrypt((noteDTO.title));
        return noteDTO;
    }

    public NoteDTO decryptNote(NoteDTO noteDTO) {
        TextEncryptor textEncryptor = Encryptors.text("password", "4fde492c6d29402a2418112934a22ead");
        noteDTO.content = textEncryptor.decrypt((noteDTO.content));
        noteDTO.title = textEncryptor.decrypt((noteDTO.title));
        return noteDTO;
    }

    public Note encryptNote(Note note) {
        TextEncryptor textEncryptor = Encryptors.text("password", "4fde492c6d29402a2418112934a22ead");
        note.setContent(textEncryptor.encrypt((note.getContent())));
        note.setTitle(textEncryptor.encrypt((note.getTitle())));
        return note;
    }

    public Note decryptNote(Note note) {
        TextEncryptor textEncryptor = Encryptors.text("password", "4fde492c6d29402a2418112934a22ead");
        note.setContent(textEncryptor.decrypt((note.getContent())));
        note.setTitle(textEncryptor.decrypt((note.getTitle())));
        return note;
    }

}
