package seedu.address.logic;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskComponent;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.UniqueTaskList;

/**
 * Handles the behaviour of recurring tasks
 * Dictates when should the recurring tasks be shown
 * This class is using a singleton pattern.
 * Use RecurringTaskManager.getInstance() to get the instance of the class
 * @author User
 *
 */
public class RecurringTaskManager {
    private static final double NUM_MONTHS_IN_YEAR = 12.0;
    private static final double NUM_WEEKS_IN_MONTH = 4.0;
    private static final double NUM_DAYS_IN_WEEK = 7.0;
    private static final int UPDATE_THRESHOLD = 1;
    private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;
    private static RecurringTaskManager instance;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    
    private UniqueTaskList repeatingTasks;
    private Date initialisedDate;
    
    private RecurringTaskManager() {}
    
    /**
     * Initalised the time for recurring task manager to track
     * Updates any of the outdated task to their recurring next date 
     */
    public void setInitialisedTime() {
        updateAnyRecurringTasks(); // updates once every boot up
        initialisedDate = new Date();
    }
    
    public void setTaskList(UniqueTaskList referenceList) {
        logger.fine("Initializing with RecurringTaskManager to manage: " + referenceList.toString());
        repeatingTasks = referenceList;
    }
    
    /**
     * Only updates if a day has elapsed
     * Updates tasks that have not been done
     */
    public void update() {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((new Date().getTime()) - initialisedDate.getTime());
        int daysElapsed = c.get(Calendar.DAY_OF_MONTH) - 1;
        if (daysElapsed < UPDATE_THRESHOLD) {
            return;
        }
        updateAnyRecurringTasks();
    }
    
    public void updateAnyRecurringTasks() {
        assert repeatingTasks != null : "Repeating Task list reference cannot be null";
        logger.info("=============================[ RecurringTaskManager Updating ]===========================");
        for(ReadOnlyTask task : repeatingTasks){
            if (task.getRecurringType().equals(RecurringType.NONE)) {
                continue;
            }
            updateRecurringTask(task);
        }
    }

    /**
     * @param Updates recurring tasks to append a new date when their recurring period has elapsed
     * @return True if the recurring task has been updated
     *          False if the recurring tasks has not been updated;
     */
    private void updateRecurringTask(ReadOnlyTask task) {
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        
        List<TaskComponent> dateComponents = task.getTaskDateComponent();
        TaskComponent lastAddedComponent =  dateComponents.get(dateComponents.size()-1);
        startDate.setTime(lastAddedComponent.getStartDate().getDate());
        endDate.setTime(lastAddedComponent.getEndDate().getDate());
        
        if(!lastAddedComponent.getStartDate().isValid()) {
            startDate = null;
        }
        appendRecurringTasks(task, startDate, endDate);
    }

    private void appendRecurringTasks(ReadOnlyTask task, Calendar startDate, Calendar endDate) {
        LocalDate localDateCurrently = LocalDate.now();
        LocalDate endDateInLocalDate = endDate.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        switch (task.getRecurringType()) {
            case DAILY:
                final int elapsedDay = (int) ChronoUnit.DAYS.between(endDateInLocalDate, localDateCurrently);
                if (elapsedDay > 0) {
                    appendDailyRecurringTask(task, startDate, endDate, elapsedDay);
                }
                break;
            case WEEKLY:
                final int elapsedWeek =  (int) Math.ceil((ChronoUnit.DAYS.between(endDateInLocalDate, localDateCurrently) / NUM_DAYS_IN_WEEK));
                if (elapsedWeek > 0) {
                    appendWeeklyRecurringTask(task, startDate, endDate, elapsedWeek);
                } 
                break;
            case MONTHLY:
                final int elapsedMonth = (int) Math.ceil(ChronoUnit.WEEKS.between(endDateInLocalDate, localDateCurrently) / NUM_WEEKS_IN_MONTH);
                if (elapsedMonth > 0) {
                    appendMonthlyRecurringTask(task, startDate, endDate, elapsedMonth);
                }
                break;
            case YEARLY:
                final int elapsedYear = (int) Math.ceil(ChronoUnit.MONTHS.between(endDateInLocalDate, localDateCurrently) / NUM_MONTHS_IN_YEAR);
                if(elapsedYear > 0) {
                    appendYearlyRecurringTask(task, startDate, endDate, elapsedYear);
                }
                break;
            default:
                assert true : "Failed to set recurring type";
        }
    }


    /**
     * Updates Yearly recurring tasks to the their latest date slot.
     * 
     * @param task Recurring task to be considered.
     * @param startDate The start date of this task if any. Null values represents that start date is not present.
     * @param endDate The end date of the is task.
     * @param elapsedYear The years that have elapsed.
     */
    private void appendYearlyRecurringTask(ReadOnlyTask task, Calendar startDate,
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
        
        TaskComponent newAppendedDate = new TaskComponent((Task) task, editedStartDate, editedEndDate);
        task.appendRecurringDate(newAppendedDate);
        repeatingTasks.getInternalComponentList().add(newAppendedDate);
    }

    /**
     * Updates Monthly recurring tasks to the their latest date slot.
     * 
     * @param task Recurring task to be considered.
     * @param startDate The start date of this task if any. Null values represents that start date is not present.
     * @param endDate The end date of the is task.
     * @param elapsedYear The months that have elapsed.
     */
    private void appendMonthlyRecurringTask(ReadOnlyTask task, Calendar startDate,
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
        
        TaskComponent newAppendedDate = new TaskComponent((Task) task, editedStartDate, editedEndDate);
        task.appendRecurringDate(newAppendedDate);
        repeatingTasks.getInternalComponentList().add(newAppendedDate);
    }

    /**
     * Updates Weekly recurring tasks to the their latest date slot.
     * 
     * @param task Recurring task to be considered.
     * @param startDate The start date of this task if any. Null values represents that start date is not present.
     * @param endDate The end date of the is task.
     * @param elapsedYear The weeks that have elapsed.
     */
    private void appendWeeklyRecurringTask(ReadOnlyTask task, Calendar startDate,
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
        
        TaskComponent newAppendedDate = new TaskComponent((Task) task, editedStartDate, editedEndDate);
        task.appendRecurringDate(newAppendedDate);
        repeatingTasks.getInternalComponentList().add(newAppendedDate);
    }

    /**
     * Updates Daily recurring tasks to the their latest date slot.
     * 
     * @param task Recurring task to be considered.
     * @param startDate The start date of this task if any. Null values represents that start date is not present.
     * @param endDate The end date of the is task.
     * @param elapsedYear The days that have elapsed.
     */
    private void appendDailyRecurringTask(ReadOnlyTask task, Calendar startDate,
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
        
        TaskComponent newAppendedDate = new TaskComponent((Task) task, editedStartDate, editedEndDate);
        task.appendRecurringDate(newAppendedDate);
        repeatingTasks.getInternalComponentList().add(newAppendedDate);
    }
    

    public static RecurringTaskManager getInstance() {
        if (instance == null) {
            instance = new RecurringTaskManager();
        }
        return instance;
    }

//    /** 
//     * @param task Recurring task to be considered for removal
//    */
//    public void removeCompletedRecurringTasks(TaskMaster task) {
//        List<TaskComponent> components = task.getTaskComponentList();
//        List<ReadOnlyTask> toBeDeleted = new ArrayList<ReadOnlyTask>();
//        for(TaskComponent t : components) {
//            if (t.getTaskReference().getRecurringType() == RecurringType.NONE) {
//                continue;
//            }
//            Date toConsider = t.getEndDate().getDate();
//            Date today = new Date();
//            Calendar calendar = Calendar.getInstance();
//            Calendar toBeConsidered = new GregorianCalendar();
//            toBeConsidered.setTime(toConsider);
//            Calendar todayDate = new GregorianCalendar();
//            todayDate.setTime(today);
//            calendar.setTimeInMillis(todayDate.getTimeInMillis() - toBeConsidered.getTimeInMillis());
//
//            long elapsedVal = 0;
//            System.out.println(calendar.get(Calendar.DAY_OF_MONTH) - 1);
//            switch(t.getTaskReference().getRecurringType()) {
//                case DAILY:
//                    elapsedVal = calendar.get(Calendar.DAY_OF_MONTH) - 1; 
//                    break;
//                case WEEKLY:
//                    elapsedVal = (calendar.get(Calendar.DAY_OF_MONTH) - 1) / NUMBER_OF_DAYS_IN_A_WEEK;
//                    break;
//                case MONTHLY:
//                    elapsedVal = calendar.get(Calendar.MONTH);
//                    break;
//                case YEARLY:
//                    elapsedVal = calendar.get(Calendar.YEAR);
//                    break;
//                default:
//                    break;
//            }
//            
//            if ( elapsedVal > 0) {
//                toBeDeleted.add(t.getTaskReference());
//            }
//        }
//        for(ReadOnlyTask t : toBeDeleted) {
//            try {
//                task.removeTask(t);
//            } catch (TaskNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
