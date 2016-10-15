package seedu.address.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;

public class EditCommand extends Command {
	
	public static final String COMMAND_WORD = "edit";
	
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": edit a specific task (specified by its index) "
            + "Parameters: TASK_INDEX [NEW_TASK_NAME]"
			+ "[from DATE to DATE | by DEADLINE]"
            + "[t/TAGS]\n"
            + "Example: " + COMMAND_WORD + " 1 a task by today 9pm";
	
	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edit Task: %1$s";
	
	private final Name taskName;
	private final UniqueTagList tags;
	private final TaskDate startDate;
	private final TaskDate endDate;
	private final int targetIndex;
	
	private Name constructName(String taskName) {
		try {
			return new Name(taskName);
		} catch (IllegalValueException e) {
			return null;
		}
	}
	
	private UniqueTagList constructTagList(Set<String> tags) {
		if(tags.size() > 0) {
			final Set<Tag> tagSet = new HashSet<>();
	        for (String tagName : tags) {
	            try {
					tagSet.add(new Tag(tagName));
				} catch (IllegalValueException e) {
				}
	        }
	        return new UniqueTagList(tagSet);
		}
		return null;	
	}
	
	private TaskDate constructTaskDate(Date date) {
		if(date != null) {
			return new TaskDate(date.toString());
		}
		return null;
	}
	
	public EditCommand(int targetIndex, String taskName, Set<String> tags, Date startDate, Date endDate) {
		this.targetIndex = targetIndex;
		this.taskName = constructName(taskName);
		this.tags = constructTagList(tags);
		this.startDate = constructTaskDate(startDate);
		this.endDate = constructTaskDate(endDate);
	}


	@Override
	public CommandResult execute() {
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		
		if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
		
		ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
		try {
			model.editTask(taskToEdit, taskName, tags, startDate, endDate);
		} catch (TaskNotFoundException e) {
			assert false : "The target task cannot be missing";
		} catch (TimeslotOverlapException e) {
			assert false : "The time slot has already been occupied";
		}
		return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
	}

}
