package seedu.address.model;


import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyFloatingTask;
import seedu.address.model.task.ReadOnlyNonFloatingTask;
import seedu.address.model.task.UniqueTaskFloatingList;
import seedu.address.model.task.UniqueTaskNonFloatingList;

import java.util.List;

/**
 * Unmodifiable view of an tag list
 */
public interface ReadOnlyTaskList {

    UniqueTagList getUniqueTagList();

    UniqueTaskFloatingList getUniqueFloatingTaskList();
    UniqueTaskNonFloatingList getUniqueNonFloatingTaskList();
    
    /**
     * Returns an unmodifiable view of floating tasks list
     */
    List<ReadOnlyFloatingTask> getFloatingTaskList();

    /**
     * Returns an unmodifiable view of non floating tasks list
     */
    List<ReadOnlyNonFloatingTask> getNonFloatingTaskList();    
    
    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
