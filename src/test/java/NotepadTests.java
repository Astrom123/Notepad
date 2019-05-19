import org.junit.jupiter.api.Test;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.*;



public class NotepadTests {

    Notepad notepad = new Notepad();

    @Test
    public void testAddNoteTest() {
        notepad.addNote("testNote", "hello");
        assertTrue(notepad.filterNotes("name", "testNote").size() != 0);
        notepad.deleteNote("testNote");
    }

    @Test
    public void deleteNoteTest() {
        notepad.addNote("testNote", "hello");
        notepad.deleteNote("testNote");
        assertTrue(notepad.filterNotes("name", "testNote").size() == 0);
    }

    @Test
    public void changeNoteTest() {
        notepad.addNote("testNote", "hello");
        assertTrue(notepad.filterNotes("note", "hello").size() != 0);
        notepad.changeNote("testNote", "not hello");
        assertTrue(notepad.filterNotes("note", "hello").size() == 0);
        notepad.deleteNote("testNote");
    }

    @Test
    public void filterNoteTest() {
        notepad.addNote("testNote1", "hello");
        notepad.addNote("testNote2", "hello");
        notepad.addNote("testNote3", "hello");
        assertTrue(notepad.filterNotes("note", "hello").size() == 3);
    }

    @Test
    public void filterNameTest() {
        notepad.addNote("testNote", "hello");
        assertTrue(notepad.filterNotes("name", "testNote").size() > 0);
        notepad.deleteNote("testNote");
    }

    @Test
    public void filterDateTest() {
        notepad.addNote("testNote", "hello");
        Calendar now = Calendar.getInstance();
        String date = String.format("%d.%d.%d", now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH )+ 1,
                now.get(Calendar.YEAR));
        assertTrue(notepad.filterNotes("date", date).size() > 0);
        notepad.deleteNote("testNote");
    }

    @Test
    public void addFailTest() {
        notepad.addNote("testNote", "hello");
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            notepad.addNote("testNote", "hello");
        });
        assertNotNull(thrown.getMessage());
        notepad.deleteNote("testNote");
    }

    @Test
    public void deleteFailTest() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            notepad.deleteNote("testNote");
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void editFailTest() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> {
            notepad.changeNote("testNote", "");
        });
        assertNotNull(thrown.getMessage());
    }
}
