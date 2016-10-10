package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedNonFloatingTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private String startDate;
    @XmlElement
    private String endDate;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    private TaskDate startTaskDate;
    private TaskDate endTaskDate;
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedNonFloatingTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedNonFloatingTask(ReadOnlyNonFloatingTask source) {
        name = source.getName().fullName;
        startTaskDate = source.getStartTaskDate();
        endTaskDate = source.getEndTaskDate();
        startDate = startTaskDate.toString();
        endDate = endTaskDate.toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public NonFloatingTask toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final TaskDate startTaskDate = new TaskDate(startDate);
        final TaskDate endTaskDate = new TaskDate(endDate);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new NonFloatingTask(name, startTaskDate, endTaskDate, tags);
    }
}
