package seedu.taskmaster.logic.commands;

import seedu.taskmaster.commons.core.EventsCenter;
import seedu.taskmaster.commons.core.Messages;
import seedu.taskmaster.commons.core.UnmodifiableObservableList;
import seedu.taskmaster.commons.events.ui.JumpToListRequestEvent;
import seedu.taskmaster.model.task.TaskOccurrence;

/**
 * Selects a task identified using it's last displayed index from the task list.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
    	
    	//assert false : "Select does not support recurring tasks"; // Should use TaskComponent instead of task
        UnmodifiableObservableList<TaskOccurrence> lastShownList = model.getFilteredTaskComponentList();
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            urManager.popFromUndoQueue();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, lastShownList.get(targetIndex-1)));

    }

}
