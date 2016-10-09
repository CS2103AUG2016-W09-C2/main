package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyFloatingTask;
import seedu.address.model.task.UniqueTaskFloatingList;
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
public class XmlSerializableFloatingTask {

    @XmlElement
    private List<XmlAdaptedFloatingTask> tasks;

    {
        tasks = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableFloatingTask() {}

    /**
     * Conversion
     */
    public XmlSerializableFloatingTask(ReadOnlyTaskList src) {
        tasks.addAll(src.getFloatingTaskList().stream().map(XmlAdaptedFloatingTask::new).collect(Collectors.toList()));
    }
    
    public void addAll(ReadOnlyTaskList src) {
        tasks.addAll(src.getFloatingTaskList().stream().map(XmlAdaptedFloatingTask::new).collect(Collectors.toList()));
    }

    public UniqueTaskFloatingList getUniqueTaskList() {
        UniqueTaskFloatingList lists = new UniqueTaskFloatingList();
        for (XmlAdaptedFloatingTask p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {

            }
        }
        return lists;
    }

    public List<ReadOnlyFloatingTask> getFloatingTaskList() {
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
