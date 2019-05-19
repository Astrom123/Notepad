import java.util.Calendar;

public class Note {

    private String name;
    private String note;
    private Calendar dateOfCreation;

    public Note(String name, String note) {
        this.name = name;
        this.note = note;
        dateOfCreation = Calendar.getInstance();
    }

    public Calendar getDate() {
        return dateOfCreation;
    }

    public String getStringDate() {
        return String.format("%d.%d.%d",
                dateOfCreation.get(Calendar.DAY_OF_MONTH),
                dateOfCreation.get(Calendar.MONTH) + 1,
                dateOfCreation.get(Calendar.YEAR));
    }

    public String getNote() {
        return note;
    }

    public String getName() {
        return name;
    }

    public void setNote(String newData) {
        note = newData;
    }
}
