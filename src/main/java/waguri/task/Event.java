package waguri.task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.MonthDay;


public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to){
        super(description, false);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return this.from;
    }

    @Override
    public String toString(){
        DateTimeFormatter from_Format;
        DateTimeFormatter to_Format;

        if (from.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            from_Format = DateTimeFormatter.ofPattern("yyyy MMM dd");
        } else {
            from_Format = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm");
        }

        if (to.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            to_Format = DateTimeFormatter.ofPattern("yyyy MMM dd");
        } else {
            to_Format = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm");
        }

        return "[E]" + super.toString() + " (from: " + from.format(from_Format) + " to: " + to.format(to_Format) + ")";
    }
}
