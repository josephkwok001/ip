public class Deadline extends Task {
    String by;

    public Deadline(String description, boolean status, String by){
        super(description, status);
        this.by = by;
    }

    @Override
    public String toString(){
        return "[D]" + super.toString() + " (by: " + by + ")";

    }

}
