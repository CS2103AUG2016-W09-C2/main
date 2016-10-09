package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestFloatingTasks {

    public static TestFloatingTask trash, book, homework, lecture, meeting, jogging, george, hoon, ida;

    public TypicalTestFloatingTasks() {
        try {
            trash =  new TaskBuilder().withName("take trash").withTags("notUrgent").build();
            book = new TaskBuilder().withName("read book").withTags("weekly", "textBook").build();
            homework = new TaskBuilder().withName("do homework").build();
            lecture = new TaskBuilder().withName("read weblecture").build();
            meeting = new TaskBuilder().withName("group meeting").build();
            jogging = new TaskBuilder().withName("jogging").build();
            george = new TaskBuilder().withName("visit George Best").build();

            //Manually added
            hoon = new TaskBuilder().withName("eat with Hoon Meier").build();
            ida = new TaskBuilder().withName("play with Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList ab) {

        try {
            ab.addFloatingTask(new FloatingTask(trash));
            ab.addFloatingTask(new FloatingTask(book));
            ab.addFloatingTask(new FloatingTask(homework));
            ab.addFloatingTask(new FloatingTask(lecture));
            ab.addFloatingTask(new FloatingTask(meeting));
            ab.addFloatingTask(new FloatingTask(jogging));
            ab.addFloatingTask(new FloatingTask(george));
        } catch (UniqueTaskFloatingList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestFloatingTask[] getTypicalTasks() {
        return new TestFloatingTask[]{trash, book, homework, lecture, meeting, jogging, george};
    }

    public TaskList getTypicalTaskList(){
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}