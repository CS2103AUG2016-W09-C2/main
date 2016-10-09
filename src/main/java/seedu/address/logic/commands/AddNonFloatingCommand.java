package seedu.address.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.NonFloatingTask;
import seedu.address.model.task.UniqueTaskFloatingList;

public class AddNonFloatingCommand extends AddCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a non floating task to the task list. "
            + "Parameters: add TASK_NAME [start/DATE TIME] end/DATE TIME [tag/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Do homework 24sep 6pm to 29sep 10am t/highPriority";

    public static final String MESSAGE_SUCCESS = "New non floating task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final NonFloatingTask toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddNonFloatingCommand(String name, Date start, Date end, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new NonFloatingTask(
                new Name(name),
                new Date(start.getTime()),
                new Date(end.getTime()),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addNonFloatingTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskFloatingList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }
}
