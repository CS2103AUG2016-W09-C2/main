package seedu.address.model.task;

import java.util.Date;

import seedu.address.model.tag.UniqueTagList;

public class NonFloatingTask extends Task implements ReadOnlyNonFloatingTask {

    private Date start, end;
    
    public NonFloatingTask(Name name, Date start, Date end, UniqueTagList uniqueTagList) {
        super(name, uniqueTagList);
        this.start = start;
        this.end = end;
    }
    
    public Date getStartDateAndTime() {
        return start;
    }
    
    public Date getEndDateAndTime() {
        return end;
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
