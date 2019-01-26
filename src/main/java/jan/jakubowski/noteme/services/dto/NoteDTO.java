package jan.jakubowski.noteme.services.dto;

public class NoteDTO {

    public NoteDTO(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public NoteDTO() {
    }

    public long id;
    public String title;
    public String content;

    @Override
    public String toString() {
        return "NoteDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
