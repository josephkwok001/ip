public class Task {
    private String description;
    private boolean status;

    public Task(String description, boolean status){
        this.description = description;
        this.status = status;
    }

    public void markAsDone(){
         this.status = true;
    }

    public void unmark(){
        this.status = false;
    }

    @Override
    public String toString() {
        String bracket = this.status ? "[X] " : "[ ] ";
        return bracket + this.description;
    }

}
