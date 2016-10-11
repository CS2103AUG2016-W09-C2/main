package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDate {
    public static final int DATE_NOT_PRESENT = -1;
    private long date;
    
    public TaskDate(long date) {
        this.date = date;
    }
        
    public TaskDate(TaskDate copy) {
        this.date = copy.date;
    }
    
    public String getFormattedDate() {
        if (date == DATE_NOT_PRESENT) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d hh.mma");
        return formatter.format(new Date(date));
    }

    public long getDate() {
        if (date == DATE_NOT_PRESENT) {
            return DATE_NOT_PRESENT;
        }
        return date;
    }   
}
