package seedu.address.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.TaskList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskDateComponent;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Handles the behaviour of recurring tasks
 * Dictates when should the recurring tasks be shown
 * @author User
 *
 */
public class RecurringTaskManager {
    private static final int UPDATE_THRESHOLD = 1;
    private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;
    private static RecurringTaskManager instance;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    
    private UniqueTaskList repeatingTasks;
    private Date initialisedDate;
    
    private RecurringTaskManager() {
    }
    
    public void setInitialisedTime() {
        updateRecurringTasks(); // updates once every boot up
        initialisedDate = new Date();
    }
    
    public void setTaskList(UniqueTaskList referenceList) {
        repeatingTasks = referenceList;
    }
    
    /**
     * Only updates if a day has elapsed
     */
    public void update() {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        //long daysElapsed = ChronoUnit.DAYS.between(initialisedTime, LocalDate.now());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((new Date().getTime()) - initialisedDate.getTime());
        int daysElapsed = c.get(Calendar.DAY_OF_MONTH) - 1;
        if (daysElapsed < UPDATE_THRESHOLD) {
            return;
        }
        updateRecurringTasks();
    }
    
    public void updateRecurringTasks() {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        logger.info("=============================[ RecurringTaskManager Updating ]===========================");
        for(ReadOnlyTask task : repeatingTasks){
            if (task.getRecurringType().equals(RecurringType.NONE)) {
                continue;
            }
            isUpdateRecurringTask(task);
        }
    }

    private boolean isUpdateRecurringTask(ReadOnlyTask task) {
        List<TaskDateComponent> dateComponents = task.getTaskDateComponent();
        TaskDateComponent lastAddedComponent =  dateComponents.get(dateComponents.size()-1);
        Calendar currentDate = new GregorianCalendar();
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        Calendar resultingDate = new GregorianCalendar();
        currentDate = Calendar.getInstance();
        startDate.setTime(lastAddedComponent.getStartDate().getDate());
        endDate.setTime(lastAddedComponent.getEndDate().getDate());
        resultingDate.setTimeInMillis(currentDate.getTimeInMillis() - endDate.getTimeInMillis());
        
        if(!lastAddedComponent.getStartDate().isValid()) {
            startDate = null;
        }
        if ((currentDate.getTimeInMillis() - endDate.getTimeInMillis()) < 0) {
            return false;
        }
        switch (task.getRecurringType()) {
            case DAILY:
                final int elapsedDay = resultingDate.get(Calendar.DAY_OF_MONTH)-1;
                if (elapsedDay >= 1) {
                    executeDailyRecurringTask(task, startDate, endDate, elapsedDay);
                    return true;
                }
                break;
            case WEEKLY:
                System.out.println(resultingDate.get(Calendar.DAY_OF_MONTH)-1);
                final int elapsedWeek = (resultingDate.get(Calendar.DAY_OF_MONTH)-1) / NUMBER_OF_DAYS_IN_A_WEEK;
                if (elapsedWeek >= 1) {
                    executeWeeklyRecurringTask(task, startDate, endDate, elapsedWeek);
                    return true;
                } 
                final int weekRemainder = (resultingDate.get(Calendar.DAY_OF_MONTH)-1) % NUMBER_OF_DAYS_IN_A_WEEK;
                if (weekRemainder > 0) {
                    executeWeeklyRecurringTask(task, startDate, endDate, 1);
                    return true;
                }
                break;
            case MONTHLY:
                final int elapsedMonth = resultingDate.get(Calendar.MONTH);
                if (elapsedMonth >= 1){
                    executeMonthlyRecurringTask(task, startDate, endDate, elapsedMonth);
                    return true;
                }
                break;
            case YEARLY:
                final int elapsedYear = resultingDate.get(Calendar.YEAR);
                if (elapsedYear >= 1) {
                    executeYearlyRecurringTask(task, startDate, endDate, elapsedYear);
                    return true;
                }
                break;
            default:
                assert true : "Failed to set recurring type";
        }
        return false;
    }

    private void executeYearlyRecurringTask(ReadOnlyTask task, Calendar startDate,
            Calendar endDate, int elapsedYear) {
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if(startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.YEAR, elapsedYear);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
        }else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.YEAR, elapsedYear);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(editedStartDate), new TaskDate(editedEndDate));
        repeatingTasks.getInternalComponentList().set(idx, newRepeatingTaskToAdd);
    }

    private void executeMonthlyRecurringTask(ReadOnlyTask task, Calendar startDate,
            Calendar endDate, int elapsedMonth) {
        // Append a new date to the current task
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if(startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.MONTH, elapsedMonth);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
        }else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.MONTH, elapsedMonth);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(editedStartDate), new TaskDate(editedEndDate));
        repeatingTasks.getInternalComponentList().set(idx, newRepeatingTaskToAdd);
    }

    private void executeWeeklyRecurringTask(ReadOnlyTask task, Calendar startDate,
            Calendar endDate, int elapsedWeek) {
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if(startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, elapsedWeek * NUMBER_OF_DAYS_IN_A_WEEK);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
        }else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, elapsedWeek * NUMBER_OF_DAYS_IN_A_WEEK);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(editedStartDate), new TaskDate(editedEndDate));
        repeatingTasks.getInternalComponentList().set(idx, newRepeatingTaskToAdd);
    }

    private void executeDailyRecurringTask(ReadOnlyTask task, Calendar startDate,
            Calendar endDate, int elapsedDay) {
        // Append a new date to the current task
        Calendar calendar = Calendar.getInstance();
        TaskDate editedStartDate = new TaskDate();
        TaskDate editedEndDate = new TaskDate();
        if(startDate != null) {
            calendar.setTime(startDate.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, elapsedDay);
            editedStartDate.setDateInLong(calendar.getTime().getTime());
        }else {
            editedStartDate.setDateInLong((new TaskDate()).getDateInLong());
        }

        calendar.setTime(endDate.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, elapsedDay);
        editedEndDate.setDateInLong(calendar.getTime().getTime());
        
        int idx = repeatingTasks.getInternalComponentList().indexOf(task.getTaskDateComponent().get(0));
        TaskDateComponent newRepeatingTaskToAdd = new TaskDateComponent((Task)task, new TaskDate(editedStartDate), new TaskDate(editedEndDate));
        repeatingTasks.getInternalComponentList().set(idx, newRepeatingTaskToAdd);
    }
    

    public static RecurringTaskManager getInstance() {
        if (instance == null) {
            instance = new RecurringTaskManager();
        }
        return instance;
    }

    public void removeCompletedRecurringTasks(TaskList initialData) {
        List<TaskDateComponent> components = initialData.getTaskComponentList();
        List<ReadOnlyTask> toBeDeleted = new ArrayList<ReadOnlyTask>();
        for(TaskDateComponent t : components) {
            if (t.getTaskReference().getRecurringType() == RecurringType.NONE) {
                continue;
            }
            Date toConsider = t.getEndDate().getDate();
            Date today = new Date();
            Calendar calendar = Calendar.getInstance();
            Calendar toBeConsidered = new GregorianCalendar();
            toBeConsidered.setTime(toConsider);
            Calendar todayDate = new GregorianCalendar();
            todayDate.setTime(today);
            calendar.setTimeInMillis(todayDate.getTimeInMillis() - toBeConsidered.getTimeInMillis());
//            if (todayDate.getTimeInMillis() - toBeConsidered.getTimeInMillis() < 0) {
//                continue;
//            }
            long elapsedVal = 0;
            System.out.println(calendar.get(Calendar.DAY_OF_MONTH) - 1);
            switch(t.getTaskReference().getRecurringType()) {
                case DAILY:
                    elapsedVal = calendar.get(Calendar.DAY_OF_MONTH) - 1; 
                    break;
                case WEEKLY:
                    elapsedVal = (calendar.get(Calendar.DAY_OF_MONTH) - 1) / NUMBER_OF_DAYS_IN_A_WEEK;
                    break;
                case MONTHLY:
                    elapsedVal = calendar.get(Calendar.MONTH);
                    break;
                case YEARLY:
                    elapsedVal = calendar.get(Calendar.YEAR);
                    break;
                default:
                    break;
            }
            
            if ( elapsedVal > 0) {
                toBeDeleted.add(t.getTaskReference());
            }
        }
        for(ReadOnlyTask t : toBeDeleted) {
            try {
                initialData.removeTask(t);
            } catch (TaskNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleRecurringTask(TaskDate start, TaskDate end, RecurringType type) {
        Calendar local = Calendar.getInstance();
        switch(type) {
            case DAILY:
                local.setTime(end.getDate());
                local.add(Calendar.DAY_OF_MONTH, 1);
                end.setDateInLong(local.getTimeInMillis());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local.setTime(start.getDate());
                    local.add(Calendar.DAY_OF_MONTH, 1);
                    start.setDateInLong(local.getTimeInMillis());                    
                }
                break;
            case WEEKLY:
                local.setTime(end.getDate());
                local.add(Calendar.DAY_OF_MONTH, NUMBER_OF_DAYS_IN_A_WEEK);
                end.setDateInLong(local.getTimeInMillis());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local.setTime(start.getDate());
                    local.add(Calendar.DAY_OF_MONTH, NUMBER_OF_DAYS_IN_A_WEEK);
                    start.setDateInLong(local.getTimeInMillis());                    
                }
                break;
            case MONTHLY:
                local.setTime(end.getDate());
                local.add(Calendar.MONTH, 1);
                end.setDateInLong(local.getTimeInMillis());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local.setTime(start.getDate());
                    local.add(Calendar.MONTH, 1);
                    start.setDateInLong(local.getTimeInMillis());                    
                }
                break;
            case YEARLY:
                local.setTime(end.getDate());
                local.add(Calendar.YEAR, 1);
                end.setDateInLong(local.getTimeInMillis());
                if (start.getDateInLong() != TaskDate.DATE_NOT_PRESENT) {
                    local.setTime(start.getDate());
                    local.add(Calendar.YEAR, 1);
                    start.setDateInLong(local.getTimeInMillis());                    
                }
                break;        }
    }
}
