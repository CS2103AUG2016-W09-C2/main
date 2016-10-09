package seedu.address.model.task;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UniqueTaskNonFloatingList extends UniqueTaskList implements Iterable<NonFloatingTask> {

    private final ObservableList<NonFloatingTask> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskNonFloatingList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyNonFloatingTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }
    
    /**
     * Returns true if the given task requests to use a blocked timeslot.
     */
    public boolean overlaps(ReadOnlyNonFloatingTask toCheck) {
        assert toCheck != null;
        for(NonFloatingTask t: internalList){
        	if(!(t.getEndTaskDate().getParsedDate().before(toCheck.getStartTaskDate().getParsedDate())||
        	t.getStartTaskDate().getParsedDate().after(toCheck.getEndTaskDate().getParsedDate())))
        		return true;
        }
        return false;
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     * @throws TimeslotOverlapException 
     */
    public void add(NonFloatingTask toAdd) throws DuplicateTaskException, TimeslotOverlapException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        if (overlaps(toAdd)){
        	throw new TimeslotOverlapException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyFloatingTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public ObservableList<NonFloatingTask> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<NonFloatingTask> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskFloatingList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskNonFloatingList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
