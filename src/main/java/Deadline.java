import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.MonthDay;

public class Deadline extends Task {
    LocalDateTime by;

    public Deadline(String description, LocalDateTime by){
        super(description, false);
        this.by = by;
    }

    public LocalDateTime getBy () {
        return this.by;
    }
    @Override
    public String toString(){
        DateTimeFormatter formatter;

        if (by.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            formatter = DateTimeFormatter.ofPattern("yyyy MMM dd");
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm");
        }

        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }


}
