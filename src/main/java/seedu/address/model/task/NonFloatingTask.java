package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;

public class NonFloatingTask extends Task implements ReadOnlyNonFloatingTask {

    private TaskDate start, end;
    public NonFloatingTask(){}
    
    public NonFloatingTask(Name name, TaskDate start, TaskDate end, UniqueTagList uniqueTagList) {
        super(name, uniqueTagList);
        this.start = start;
        this.end = end;

    }
    
    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public NonFloatingTask(ReadOnlyNonFloatingTask source) {
        this(source.getName(), source.getStartTaskDate(), source.getEndTaskDate(), source.getTags());
    }
    
    
    public TaskDate getStartTaskDate() {
        return start;
    }
    
    public TaskDate getEndTaskDate() {
        return end;
    }
    
    public boolean isValidTimeSlot(){
    	return start.getParsedDate().before(end.getParsedDate());
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyNonFloatingTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyNonFloatingTask) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
