package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.model.tag.UniqueTagList;

public interface ReadOnlyNonFloatingTask {
    Name getName();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    TaskDate getStartTaskDate();
    TaskDate getEndTaskDate();
    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyNonFloatingTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getStartTaskDate().equals(this.getStartTaskDate())
                && other.getEndTaskDate().equals(this.getEndTaskDate())
                );
    }

    /**
     * Formats the task as text, showing all non floating task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();

        builder.append(getName())
                .append(" Start Date: " + getStartTaskDate())
                .append(" End Date: " + getEndTaskDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Task's tags
     */
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getTags().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }

}
