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
@XmlRootElement(name = "tasklist")
public class XmlSerializableTaskList implements ReadOnlyTaskList {

    @XmlElement
    private XmlSerializableFloatingTask floatingTask;
    @XmlElement
    private XmlSerializableNonFloatingTask nonFloatingTask;
    @XmlElement
    private List<Tag> tags;

    {
        floatingTask = new XmlSerializableFloatingTask();
        nonFloatingTask = new XmlSerializableNonFloatingTask();
        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskList() {}

    /**
     * Conversion
     */
    public XmlSerializableTaskList(ReadOnlyTaskList src) {
        floatingTask.addAll(src);
        nonFloatingTask.addAll(src);
        tags = src.getTagList();
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
        } catch (UniqueTagList.DuplicateTagException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UniqueTaskFloatingList getUniqueFloatingTaskList() {
        return floatingTask.getUniqueTaskList();
    }

    @Override
    public UniqueTaskNonFloatingList getUniqueNonFloatingTaskList() {
        return nonFloatingTask.getUniqueTaskList();
    }    
    
    @Override
    public List<ReadOnlyFloatingTask> getFloatingTaskList() {
        return floatingTask.getFloatingTaskList();
    }

    @Override
    public List<ReadOnlyNonFloatingTask> getNonFloatingTaskList() {
        return nonFloatingTask.getNonFloatingTaskList();
    }    
    
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
