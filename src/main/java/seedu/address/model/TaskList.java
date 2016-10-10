package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.NonFloatingTask;
import seedu.address.model.task.ReadOnlyFloatingTask;
import seedu.address.model.task.ReadOnlyNonFloatingTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskFloatingList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;
import seedu.address.model.task.UniqueTaskNonFloatingList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-list level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskList implements ReadOnlyTaskList {

    private final UniqueTaskFloatingList floatingTasks;
    private final UniqueTaskNonFloatingList nonFloatingTasks;
    private final UniqueTagList tags;

    {
        floatingTasks = new UniqueTaskFloatingList();
        nonFloatingTasks = new UniqueTaskNonFloatingList();
        tags = new UniqueTagList();
    }

    public TaskList() {}

    /**
     * Tasks and Tags are copied into this task list
     */
    public TaskList(ReadOnlyTaskList toBeCopied) {
        this(toBeCopied.getUniqueFloatingTaskList(), toBeCopied.getUniqueNonFloatingTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this task list
     */
    public TaskList(UniqueTaskFloatingList floatingTasks, UniqueTaskNonFloatingList nonFloatingTask, UniqueTagList tags) {
        resetData(floatingTasks.getInternalList(), nonFloatingTask.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskList getEmptyTaskList() {
        return new TaskList();
    }

//// list overwrite operations

    public ObservableList<FloatingTask> getFloatingTasks() {
        return floatingTasks.getInternalList();
    }

    public void setFloatingTasks(List<FloatingTask> tasks) {
        this.floatingTasks.getInternalList().setAll(tasks);
    }

    public ObservableList<NonFloatingTask> getNonFloatingTasks() {
        return nonFloatingTasks.getInternalList();
    }

    public void setNonFloatingTasks(List<NonFloatingTask> tasks) {
        this.nonFloatingTasks.getInternalList().setAll(tasks);
    }    

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyFloatingTask> newFloatingTasks, 
            Collection<? extends ReadOnlyNonFloatingTask> newNonFloatingTasks, Collection<Tag> newTags) {
        setFloatingTasks(newFloatingTasks.stream().map(FloatingTask::new).collect(Collectors.toList()));
        setNonFloatingTasks(newNonFloatingTasks.stream().map(NonFloatingTask::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskList newData) {
        resetData(newData.getFloatingTaskList(), newData.getNonFloatingTaskList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to the task list.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskFloatingList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addFloatingTask(FloatingTask t) throws DuplicateTaskException {
        syncTagsWithMasterList(t);
        floatingTasks.add(t);
    }
    
    public void addNonFloatingTask(NonFloatingTask t) throws DuplicateTaskException, TimeslotOverlapException {
        syncTagsWithMasterList(t);
        nonFloatingTasks.add(t);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyFloatingTask key) throws UniqueTaskFloatingList.TaskNotFoundException {
        if (floatingTasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskFloatingList.TaskNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return floatingTasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyFloatingTask> getFloatingTaskList() {
        return Collections.unmodifiableList(floatingTasks.getInternalList());
    }

    @Override
    public List<ReadOnlyNonFloatingTask> getNonFloatingTaskList() {
        return Collections.unmodifiableList(nonFloatingTasks.getInternalList());
    }   
    
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskFloatingList getUniqueFloatingTaskList() {
        return this.floatingTasks;
    }
    
    @Override
    public UniqueTaskNonFloatingList getUniqueNonFloatingTaskList() {
        return this.nonFloatingTasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && this.floatingTasks.equals(((TaskList) other).floatingTasks)
                && this.tags.equals(((TaskList) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(floatingTasks, tags);
    }
}
