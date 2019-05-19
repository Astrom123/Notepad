import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Notepad {

    private HashMap<String, Note> notes;

    public Notepad() {
        notes = load();
        return;
    }

    public void addNote(String name, String note) throws IllegalArgumentException {
        if (!notes.containsKey(name)) {
            notes.put(name, new Note(name, note));
        } else {
            throw new IllegalArgumentException("Note with such name is already existing");
        }
    }

    public void changeNote(String name, String newData) throws IllegalArgumentException {
        if (notes.containsKey(name)) {
            notes.get(name).setNote(newData);
        } else {
            throw new IllegalArgumentException("Note with such name is not existing");
        }
    }

    public void deleteNote(String name) throws IllegalArgumentException {
        if (notes.containsKey(name)) {
            notes.remove(name);
        } else {
            throw new IllegalArgumentException("Note with such name is not existing");
        }
    }

    public Iterable<Note> getAll() {
        return notes.values();
    }

    public ArrayList<Note> filterNotes(String field, String value) {
        if ("name".equals(field)) {
            ArrayList<Note> result = new ArrayList<Note>();
            Note note = notes.get(value);
            if (note != null)
                result.add(note);
            return result;
        } else if ("note".equals(field)) {
            return filterByData(value);
        } else if ("date".equals(field)) {
            return filterByDate(value);
        }
        return new ArrayList<>(0);
    }

    private ArrayList<Note> filterByData(String value) {
        ArrayList<Note> result = new ArrayList<Note>();
        for (Note note : notes.values()) {
            if (note.getNote().equals(value)) {
                result.add(note);
            }
        }
        return result;
    }

    private ArrayList<Note> filterByDate(String value) {
        ArrayList<Integer> date = new ArrayList<Integer>();
        for (String part: value.split("\\.")) {
            date.add(Integer.parseInt(part));
        }
        ArrayList<Note> result = new ArrayList<Note>();
        for (Note note : notes.values()) {
            if (compareNoteDate(date, note)) {
                result.add(note);
            }
        }
        return result;
    }

    private boolean compareNoteDate(ArrayList<Integer> date, Note note) {
        Calendar noteDate = note.getDate();
        return noteDate.get(Calendar.YEAR) == date.get(2) &&
                noteDate.get(Calendar.MONTH) == date.get(1) - 1 &&
                noteDate.get(Calendar.DAY_OF_MONTH) == date.get(0);
    }

    public void serialize(String path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonObj = gson.toJson(notes);
        File file = new File(path);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonObj);
            fileWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private HashMap<String, Note> load() {
        File save = new File("data.json");
        Gson gson = new Gson();
        if (save.exists()) {
            try {
                String data = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
                return gson.fromJson(data, new TypeToken<HashMap<String, Note>>(){}.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<String, Note>();
    }
}
