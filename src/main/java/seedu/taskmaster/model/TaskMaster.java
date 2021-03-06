package seedu.taskmaster.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.taskmaster.model.tag.Tag;
import seedu.taskmaster.model.tag.UniqueTagList;
import seedu.taskmaster.model.task.Name;
import seedu.taskmaster.model.task.ReadOnlyTask;
import seedu.taskmaster.model.task.RecurringType;
import seedu.taskmaster.model.task.Task;
import seedu.taskmaster.model.task.TaskDate;
import seedu.taskmaster.model.task.TaskOccurrence;
import seedu.taskmaster.model.task.UniqueTaskList;
import seedu.taskmaster.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.taskmaster.model.task.UniqueTaskList.TimeslotOverlapException;

/**
 * Wraps all data at the task-list level Duplicates are not allowed (by .equals
 * comparison)
 */
public class TaskMaster implements ReadOnlyTaskMaster {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskMaster() {
    }

    /**
     * Tasks and Tags are copied into this task list
     */
    public TaskMaster(ReadOnlyTaskMaster toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this task list
     */
    public TaskMaster(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalTaskList(), tasks.getInternalOccurrenceList(), tags.getInternalList());
    }

    public static ReadOnlyTaskMaster getEmptyTaskList() {
        return new TaskMaster();
    }

    //// list overwrite operations

    public List<Task> getTasks() {
        return tasks.getInternalTaskList();
    }

    @Override
    public ObservableList<TaskOccurrence> getTaskOccurrenceList() {
        return tasks.getInternalOccurrenceList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalTaskList().clear();
        this.tasks.getInternalTaskList().addAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    // @@author A0147967J
    public void resetData(Collection<? extends ReadOnlyTask> newTasks,
            Collection<? extends TaskOccurrence> newComponents, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        rebuildComponentList();
        setTags(newTags);
    }

    /**
     * Rebuilds the component list based on restored tasks, as copy constructor
     * of task component does not work.
     */
    public void rebuildComponentList() {
        this.tasks.getInternalOccurrenceList().clear();
        ArrayList<TaskOccurrence> fullList = new ArrayList<TaskOccurrence>();
        for (Task task : tasks.getInternalTaskList()) {
            ArrayList<TaskOccurrence> newList = new ArrayList<TaskOccurrence>();
            for (TaskOccurrence c : task.getTaskDateComponent()) {
                TaskDate startDate = c.getStartDate();
                TaskDate endDate = c.getEndDate();
                boolean isArchived = c.isArchived();
                TaskOccurrence newTaskComponent = new TaskOccurrence(task, startDate, endDate);
                if (isArchived)
                    newTaskComponent.archive();
                newList.add(newTaskComponent);
                fullList.add(newTaskComponent);
            }
            task.setRecurringDates(newList);
        }
        this.tasks.getInternalOccurrenceList().setAll(fullList);
    }

    // @@author
    public void resetData(ReadOnlyTaskMaster newData) {
        resetData(newData.getTaskList(), newData.getTaskOccurrenceList(), newData.getTagList());
    }

    //// task-level operations

    /**
     * Adds a task to the task list. Also checks the new task's tags and updates
     * {@link #tags} with any new tags found, and updates the Tag objects in the
     * task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     * @throws TimeslotOverlapException
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException, TimeslotOverlapException {
        syncTagsWithMasterList(p);
        tasks.add(p);
    }

    /**
     * Ensures that every tag in this task: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
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

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.getInternalTaskList().size() + " tasks, " + tags.getInternalList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalTaskList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskMaster // instanceof handles nulls
                        && this.tasks.equals(((TaskMaster) other).tasks)
                        && this.tags.equals(((TaskMaster) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(tasks, tags);
    }

    // @@author A0147967J
    public boolean archiveTask(TaskOccurrence target) throws TaskNotFoundException {
        // TODO Auto-generated method stub
        if (tasks.archive(target)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    // @@author

    // @@author A0147995H
    public boolean updateTask(TaskOccurrence target, Name name, UniqueTagList tags, TaskDate startDate, TaskDate endDate,
            RecurringType recurringType) throws TaskNotFoundException, TimeslotOverlapException {
        int index = tasks.getInternalOccurrenceList().indexOf(target);
        if (tasks.updateTask(target, name, tags, startDate, endDate, recurringType)) {
            if (tags != null) {
                this.tags.mergeFrom(tags);

                // Create map with values = tag object references in the master
                // list
                final Map<Tag, Tag> masterTagObjects = new HashMap<>();
                for (Tag tag : this.tags) {
                    masterTagObjects.put(tag, tag);
                }

                // Rebuild the list of task tags using references from the
                // master list
                final Set<Tag> commonTagReferences = new HashSet<>();
                for (Tag tag : tags) {
                    commonTagReferences.add(masterTagObjects.get(tag));
                }
                ((Task)(tasks.getInternalOccurrenceList().get(index).getTaskReference())).setTags(new UniqueTagList(commonTagReferences));
            }
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    // @@author
}
