package ToDoApp;

import java.time.LocalDate;

public class Task {

    private final int id;

    private String category;
    private boolean done;
    private String description;
    private LocalDate deadline;

    public Task( int id, String description,String category, LocalDate deadline ) {
        this.id = id;
        this.description = description;
        this.done = false;
        this.deadline = deadline;
        this.category = category;
    }
    public String toString() {
        return id + ". [" + (done ? "X" : " ") + "] " + description + " | " + category + " | " + deadline;
    }
}
