package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.NonFloatingTask;
import seedu.address.model.task.ReadOnlyFloatingTask;
import seedu.address.model.task.ReadOnlyNonFloatingTask;
import seedu.address.model.task.UniqueTaskList;

import java.util.Set;

import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskList newData);

    /** Returns the TaskList */
    ReadOnlyTaskList getTaskList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyFloatingTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given floating task */
    void addFloatingTask(FloatingTask task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given non floating task */
    void addNonFloatingTask(NonFloatingTask task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyFloatingTask> getFilteredFloatingTaskList();

    UnmodifiableObservableList<ReadOnlyNonFloatingTask> getFilteredNonFloatingTaskList();
    
    /** Updates the filter of the filtered task list to show all floating tasks */
    void updateFilteredFloatingListToShowAll();

    /** Updates the filter of the filtered task list to show all non floating tasks */
    void updateFilteredNonFloatingListToShowAll();
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredFloatingTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredNonFloatingTaskList(Set<String> keywords);    
    
    /** Updates the file path for current storage manager of the model.*/
	void changeDirectory(String filePath);
}
