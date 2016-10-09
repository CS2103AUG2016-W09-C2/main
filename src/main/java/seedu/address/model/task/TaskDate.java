package seedu.address.model.task;

import java.text.SimpleDateFormat;
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
    
    @Override
    public String toString() {
        if (parsedDate == null) {
            return rawDateInput;
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, MMM d, h:mm a");
        return dateFormatter.format(parsedDate);
    }
    

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                && this.parsedDate.toGMTString().equals(((TaskDate) other).parsedDate.toGMTString())); // state check
    }

    @Override
    public int hashCode() {
        return parsedDate.toGMTString().hashCode();
    }
}
