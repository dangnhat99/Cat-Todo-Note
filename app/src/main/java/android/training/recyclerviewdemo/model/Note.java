package android.training.recyclerviewdemo.model;

public class Note {
    int id;
    String content;
    boolean isDone;
    CapooCat imgNote;
    NoteColor color;

    public Note(String content, boolean isDone, CapooCat imgNote) {
        this.content = content;
        this.isDone = isDone;
        this.imgNote = imgNote;
    }

    public Note(int id, String content, boolean isDone, CapooCat imgNote, NoteColor color) {
        this.id = id;
        this.content = content;
        this.isDone = isDone;
        this.imgNote = imgNote;
        this.color = color;
    }

    public Note() {
        this.id = -1;
        this.content = "";
        this.imgNote = CapooCat.HAND_CLAP;
        this.color = NoteColor.LIGHT_BLUE;
    }

    public Note(String content) {
        this.content = content;
        this.isDone = false;
        this.imgNote = CapooCat.HAND_CLAP;
        color = NoteColor.LIGHT_BLUE;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public CapooCat getImgNote() {
        return imgNote;
    }

    public void setImgNote(CapooCat imgNote) {
        this.imgNote = imgNote;
    }

    public NoteColor getColor() {
        return color;
    }

    public void setColor(NoteColor color) {
        this.color = color;
    }
}
