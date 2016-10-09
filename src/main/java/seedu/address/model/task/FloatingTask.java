package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a floating task in the task list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask extends Task implements ReadOnlyFloatingTask {
    
    /**
     * Every field must be present and not null.
     */
    public FloatingTask(){}
    
    public FloatingTask(Name name, UniqueTagList tags) {
        super(name, tags);
    }
    
    /**
     * Copy constructor.
     */
    public FloatingTask(ReadOnlyFloatingTask source) {
        this(source.getName(), source.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyFloatingTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyFloatingTask) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
