package seedu.address.model.task;

import java.util.Date;

import seedu.address.model.tag.UniqueTagList;

public class NonFloatingTask extends Task implements ReadOnlyNonFloatingTask {

    private TaskDate start, end;
    
    public NonFloatingTask(Name name, TaskDate start, TaskDate end, UniqueTagList uniqueTagList) {
        super(name, uniqueTagList);
        this.start = start;
        this.end = end;
    }
    
    public TaskDate getStartTaskDate() {
        return start;
    }
    
    public TaskDate getEndTaskDate() {
        return end;
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
