import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CUI {

    private static final Notepad notepad = new Notepad();

    public static void main(String args[]) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String[] input = reader.readLine().split(" ");
                String command = input[0];
                switch (command) {
                    case "exit":
                        notepad.serialize("data.json");
                        return;
                    case "all":
                        printNotes(notepad.getAll());
                        break;
                    case "add":
                        addNote(input);
                        break;
                    case "delete":
                        deleteNote(input);
                        break;
                    case "filter":
                        filterNotes(input);
                        break;
                    case "edit":
                        editNote(input);
                        break;
                    default:
                        System.out.println("Wrong command");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    public static void printNotes(Iterable<Note> notes) {
        StringBuilder sb = new StringBuilder();
        for (Note note : notes) {
            sb.append("Name: ");
            sb.append(note.getName());
            sb.append("\nDate: ");
            sb.append(note.getStringDate());
            sb.append("\n");
            sb.append(note.getNote());
            sb.append("\n======\n");
        }
        String res = sb.toString();
        if (res.equals("")) {
            System.out.println("No notes");
            return;
        }
        System.out.println(res);
    }

    public static void addNote(String[] args) {
        try {
            if (args.length > 2 && !args[1].equals("")) {
                StringBuilder note = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    note.append(args[i]);
                }
                notepad.addNote(args[1], note.toString());
                System.out.println("Note " + args[1] + " is created");
            } else {
                System.out.println("Wrong arguments");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteNote(String[] args) {
        try {
            if (args.length > 1) {
                String name = args[1];
                notepad.deleteNote(name);
                System.out.println("Note " + name + " is deleted");
            } else {
                System.out.println("Wrong arguments");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void filterNotes(String args[]) {
        try {
            if (args.length == 3 && args[1].equals("name")) {
                printNotes(notepad.filterNotes(args[1], args[2]));
            }
            else if (args.length > 2 && args[1].equals("note")) {
                StringBuilder note = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    note.append(args[i]);
                }
                printNotes(notepad.filterNotes(args[1], note.toString()));
            }
            else if (args.length == 3 && checkDataFormat(args[2])) {
                printNotes(notepad.filterNotes(args[1], args[2]));
            }
            else {
                System.out.println("Wrong arguments");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean checkDataFormat(String string) {
        String[] date = string.split("\\.");
        if (date.length == 3) {
            try {
                int day = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);
                if (month > 12 || month < 1 || day > 31 || day < 1) {
                    return false;
                }
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return true;
    }

    public static void editNote(String args[]) {
        try {
            if (args.length > 2) {
                StringBuilder noteData = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    noteData.append(args[i]);
                }
                notepad.changeNote(args[1], noteData.toString());
                System.out.println("note " + args[1] + " is changed");
            } else {
                System.out.println("Wrong arguments");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
