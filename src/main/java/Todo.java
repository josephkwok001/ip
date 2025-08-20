public class Todo extends Task {
    public Todo(String description, boolean status){
        super(description, status);
    }

    @Override
    public String toString(){
        return "[T]" + super.toString();
    }
}
