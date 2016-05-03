package ca.ubc.cpsc210.todo.model;

/**
 * Created by Mengyu on 2015/10/4.
 */
public class UnscheduledTodoItem extends TodoItem {
   boolean highPriority;


    public UnscheduledTodoItem(String title, String description, boolean highPriority) {
        super(title, description);
        this.highPriority=highPriority;
    }
    public void setHighPriority(boolean highPriority){

    }

    @Override
    public boolean isHighPriority() {
        return false;
    }
}
