package seedu.address.model.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the task list.
 * A Floating task is created by using the constructor with only the (Name and UniqueTagList)
 * E.g. Task floating = new Task(name, uniqueTagList)
 * A Non Floating task is created by using the constructor with (Name, UniqueTagList, TaskDate, TaskDate)
 * E.g. 
 * TaskDate startDate, endDate;
 * startDate = new TaskDate(...);
 * endDate = new TaskDate(...);
 * Task nonFloating = new Task(name, uniqueTagList, startDate, endDate); 
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private UniqueTagList tags;
    private TaskType taskType;
    private RecurringType recurringType;
    
    private List<TaskComponent> recurringDates;
    /**
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = tags;
        this.taskType = TaskType.FLOATING;
        this.recurringType = RecurringType.NONE;
        this.recurringDates = new ArrayList<TaskComponent>();
        this.recurringDates.add(new TaskComponent(this ,new TaskDate(), new TaskDate()));
    }

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags, TaskDate startDate, TaskDate endDate, RecurringType recurringType) {
        this(name, tags);
        assert !CollectionUtil.isAnyNull(startDate, endDate, recurringType);
        this.taskType = TaskType.NON_FLOATING;
        this.recurringType = recurringType;
        getComponentForNonRecurringType().setStartDate(startDate);
        getComponentForNonRecurringType().setEndDate(endDate);
    }
    
    public Task(Name name, UniqueTagList tags, RecurringType recurringType) {
        this(name, tags);
        assert recurringType != null : "Recurring Type must be specified";
        this.recurringType = recurringType;
    }
    
    public Task(){}

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTags(), source.getRecurringType());
        this.recurringDates = source.getTaskDateComponent();
        this.taskType = source.getTaskType();
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    @Override
    public List<TaskComponent> getTaskDateComponent() {
        return recurringDates;
    }
    
    @Override
    public TaskType getTaskType() {
        return taskType;
    }
    @Override
    public RecurringType getRecurringType() {
        return recurringType;
    }
    
    public void setType(TaskType type) {
        this.taskType = type;
    }
    public void setRecurringType(RecurringType type) {
        if (taskType == TaskType.FLOATING) {
            assert (!type.equals(RecurringType.NONE)) : "Floating Task cannot be a recurring task";
        }
        this.recurringType = type;
    }
    
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    @Override
    public void completeTaskWhenAllComponentArchived() {
        for (TaskComponent c : recurringDates) {
            if (c.isArchived() == false || c.getTaskReference().getRecurringType() != RecurringType.NONE) {
                return;
            }
        }
        taskType = TaskType.COMPLETED;
    }

	@Override
	public void updateTask(Name name, UniqueTagList tags, TaskDate startDate, TaskDate endDate) {
		if(name != null)
			this.name = name;
		
		if(tags != null)		
			this.tags = tags;

		
		if(this.getComponentForNonRecurringType().getStartDate().equals(new TaskDate(TaskDate.DATE_NOT_PRESENT))
				&& this.getComponentForNonRecurringType().getStartDate().equals(new TaskDate(TaskDate.DATE_NOT_PRESENT))
				&& endDate != null) {
			this.taskType = TaskType.NON_FLOATING;
		}
		
		assert this.recurringType == RecurringType.NONE : "Update does not support recurring task";
		if(startDate != null) {
			//this.startDate = startDate;
		    getComponentForNonRecurringType().setStartDate(startDate);
		} else if(endDate != null) {
			//this.startDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
	        getComponentForNonRecurringType().setStartDate(new TaskDate(TaskDate.DATE_NOT_PRESENT));
		}
		
		if(endDate != null) {
			//this.endDate = endDate;
		    getComponentForNonRecurringType().setEndDate(new TaskDate(TaskDate.DATE_NOT_PRESENT));
		}
		
		// needs to be changed just a stop gap measure
		if (recurringType.equals(RecurringType.NONE)) {
		    recurringDates.get(0).setStartDate(startDate);
            recurringDates.get(0).setEndDate(endDate);
		}
		
	}
	
	@Override
	public TaskComponent getComponentForNonRecurringType() {
	    assert recurringDates.size() == 1 : "This method should only be used for non recurring tasks";
	    return recurringDates.get(0);
	}

	@Override
	public void appendRecurringDate(TaskComponent componentToBeAdded) {
	    assert !recurringType.equals(RecurringType.NONE) : "You cannot append new dates to non recurring tasks";
	    recurringDates.add(componentToBeAdded);
	}
}
