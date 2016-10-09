package seedu.address.model.task;

import java.util.Date;

public class TaskDate {
    String rawDateInput;
    Date parsedDate;
    
    public TaskDate(String dateFromInput) {
        this.rawDateInput = dateFromInput;
    }
    
    public TaskDate(String dateFromInput, Date parsedDate) {
        this.rawDateInput = dateFromInput;
        this.parsedDate = parsedDate;
    }
    
    public TaskDate(TaskDate taskWithDate) {
        this(taskWithDate.getRawDateInput(), taskWithDate.getParsedDate());
    }

    public String getRawDateInput() {
        return rawDateInput;
    }
    
    public Date getParsedDate() {
        return parsedDate;
    }
}
