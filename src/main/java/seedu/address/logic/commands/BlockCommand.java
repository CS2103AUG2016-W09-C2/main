package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.NonFloatingTask;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.UniqueTaskFloatingList;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;

public class BlockCommand extends Command{
	
	public static final String COMMAND_WORD = "block";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Blocks a certain timeslot in the schedule. "
            + "Parameters: block start/DATE TIME end/DATE TIME [tag/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " from 24 sep 6pm to 29 sep 10am t/highPriority";

    public static final String MESSAGE_SUCCESS = "Timeslot blocked: %1$s";
    public static final String MESSAGE_TIMESLOT_OCCUPIED = "This timeslot is already blocked or overlapped with existing tasks.";
    public static final String MESSAGE_ILLEGAL_TIME_SLOT = "End time must be later than Start time.";
    private static final String DUMMY_NAME = "BLOCKED SLOT";

    private final NonFloatingTask toBlock;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public BlockCommand(TaskDate start, TaskDate end, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toBlock = new NonFloatingTask(
                new Name(DUMMY_NAME),
                new TaskDate(start),
                new TaskDate(end),
                new UniqueTagList(tagSet)
        );
        if(!this.toBlock.isValidTimeSlot())
        	throw new IllegalValueException(MESSAGE_ILLEGAL_TIME_SLOT);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addNonFloatingTask(toBlock);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toBlock));
        } catch (UniqueTaskFloatingList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_TIMESLOT_OCCUPIED);
        } catch (TimeslotOverlapException e) {
			// TODO Auto-generated catch block
			return new CommandResult(MESSAGE_TIMESLOT_OCCUPIED);
		}

    }
}
