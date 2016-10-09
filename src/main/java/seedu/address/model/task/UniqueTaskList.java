package seedu.address.model.task;

import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;

public class UniqueTaskList {
    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}
    
    /**
     * Signals that an operation adding/blocking a time slot in the list would fail because
     * the timeslot is already occupied.
     */
    
    public static class TimeslotOverlapException extends IllegalValueException {

		public TimeslotOverlapException() {
			super("Operation cannot be done due to overlapping with blocked slots.");
			// TODO Auto-generated constructor stub
		}}
}
