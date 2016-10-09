package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyFloatingTask;
import seedu.address.model.task.ReadOnlyNonFloatingTask;
import seedu.address.model.task.UniqueTaskFloatingList;
import seedu.address.model.task.UniqueTaskNonFloatingList;
import seedu.address.model.ReadOnlyTaskList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable TaskList that is serializable to XML format
 */
public class XmlSerializableNonFloatingTask {

    @XmlElement
    private List<XmlAdaptedNonFloatingTask> tasks;

    {
        tasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableNonFloatingTask() {}

    /**
     * Conversion
     */
    public XmlSerializableNonFloatingTask(ReadOnlyTaskList src) {
        tasks.addAll(src.getNonFloatingTaskList().stream().map(XmlAdaptedNonFloatingTask::new).collect(Collectors.toList()));
    }
    
    public void addAll(ReadOnlyTaskList src) {
        tasks.addAll(src.getNonFloatingTaskList().stream().map(XmlAdaptedNonFloatingTask::new).collect(Collectors.toList()));
    }

    public UniqueTaskNonFloatingList getUniqueTaskList() {
        UniqueTaskNonFloatingList lists = new UniqueTaskNonFloatingList();
        for (XmlAdaptedNonFloatingTask p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    public List<ReadOnlyNonFloatingTask> getNonFloatingTaskList() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
    }

}
