package seedu.address.logic;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskDateComponent;
import seedu.address.model.task.UniqueTaskList;

public class RepeatingTaskManager {
    private static RepeatingTaskManager instance;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    
    private LocalDate initialisedTime;
    private UniqueTaskList repeatingTasks;
    private boolean hasUpdates;
    private RepeatingTaskManager() {
        hasUpdates = false;
    }
    
    public void setInitialisedTime() {
        initialisedTime = LocalDate.now();
        updateRepeatingTasks(); // updates once every boot up
    }
    
    public void setTaskList(UniqueTaskList referenceList) {
        repeatingTasks = referenceList;
    }
    
    /**
     * Only updates if a day has elapsed
     */
    public void update() {
        assert repeatingTasks == null : "Repeating Task list reference cannot be null";
        long daysElapsed = ChronoUnit.DAYS.between(initialisedTime, LocalDate.now());
        if (daysElapsed <= 0) {
            return;
        }
        updateRepeatingTasks();
    }
    
    private void updateRepeatingTasks() {
        logger.info("=============================[ RecurringTaskManager Updating ]===========================");
        for(ReadOnlyTask task : repeatingTasks){
            List<TaskDateComponent> dateComponents = task.getTaskDateComponent();
            TaskDateComponent lastAddedComponent =  dateComponents.get(dateComponents.size()-1);
            
            if(isUpdateRecurringTask(task, lastAddedComponent)) {
            }
        }
    }

    private boolean isUpdateRecurringTask(ReadOnlyTask task, TaskDateComponent lastAddedComponent) {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastAddedStartDate = lastAddedComponent.getStartDate().getDate().
                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();        
        LocalDate lastAddedEndDate = lastAddedComponent.getEndDate().getDate().
                toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        switch (task.getRecurringType()) {
            case DAILY:
                long elapsedDay = ChronoUnit.DAYS.between(lastAddedEndDate, currentDate);
                if (elapsedDay >= 1) {
                    appendDailyRecurringTask(task, lastAddedStartDate, lastAddedEndDate);
                    return true;
                }
                break;
            case WEEKLY:
                long elapsedWeek = ChronoUnit.WEEKS.between(lastAddedEndDate, currentDate);
                if (elapsedWeek >= 1) {
                    appendWeeklyRecurringTask(task, lastAddedStartDate, lastAddedEndDate);
                    return true;
                }
                break;
            case MONTHLY:
                long elapsedMonth = ChronoUnit.MONTHS.between(lastAddedEndDate, currentDate);
                if (elapsedMonth >= 1){
                    appendMonthlyRecurringTask(task, lastAddedStartDate, lastAddedEndDate);
                    return true;
                }
                break;
            case YEARLY:
                long elapsedYear = ChronoUnit.YEARS.between(lastAddedEndDate, currentDate);
                if (elapsedYear >= 1) {
                    appendYearlyRecurringTask(task, lastAddedStartDate, lastAddedEndDate);
                    return true;
                }
                break;
            default:
                assert true : "Failed to set recurring type";
        }
        return false;
    }

    private void appendYearlyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate) {
        LocalDate newLocalStartDate = lastAddedStartDate.plusYears(1);
        LocalDate newLocalEndDate = lastAddedEndDate.plusYears(1);
        
        Date convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        Task mutableTask = (Task) task;
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent(mutableTask, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
        mutableTask.appendRecurringTaskDate(newRepeatingTaskToAdd);
    }

    private void appendMonthlyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate) {
        LocalDate newLocalStartDate = lastAddedStartDate.plusMonths(1);
        LocalDate newLocalEndDate = lastAddedEndDate.plusMonths(1);
        
        Date convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        Task mutableTask = (Task) task;
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent(mutableTask, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
        mutableTask.appendRecurringTaskDate(newRepeatingTaskToAdd);
    }

    private void appendWeeklyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate) {
        // Append a new date to the current task
        LocalDate newLocalStartDate = lastAddedStartDate.plusWeeks(1);
        LocalDate newLocalEndDate = lastAddedEndDate.plusWeeks(1);
        
        Date convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        Task mutableTask = (Task) task;
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent(mutableTask, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
        mutableTask.appendRecurringTaskDate(newRepeatingTaskToAdd);
    }

    private void appendDailyRecurringTask(ReadOnlyTask task, LocalDate lastAddedStartDate,
            LocalDate lastAddedEndDate) {
        // Append a new date to the current task
        LocalDate newLocalStartDate = lastAddedStartDate.plusDays(1);
        LocalDate newLocalEndDate = lastAddedEndDate.plusDays(1);
        
        Date convertedStartDate = Date.from(newLocalStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date convertedEndDate = Date.from(newLocalEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        Task mutableTask = (Task) task;
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent(mutableTask, new TaskDate(convertedStartDate), new TaskDate(convertedEndDate));
        mutableTask.appendRecurringTaskDate(newRepeatingTaskToAdd);
    }
    
    boolean hasUpdates() {
        boolean hasUpdateCopy = hasUpdates;
        hasUpdates = false;
        return hasUpdateCopy;
    }

    public static RepeatingTaskManager getInstance() {
        if (instance == null) {
            instance = new RepeatingTaskManager();
        }
        return instance;
    }
}
