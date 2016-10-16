package guitests;

import guitests.guihandles.FloatingTaskCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddFloatingCommand;
import seedu.address.logic.commands.AddNonFloatingCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskListGuiTest {

    @Test
    public void add() {
        //add one floatingTask
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another floatingTask
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate floatingTask
        commandBox.runCommand(td.hoon.getAddFloatingCommand());
        assertResultMessage(AddFloatingCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(floatingTaskListPanel.isListMatching(currentList));
        
        //add one non-floating task
        taskToAdd = td.project;
        assertAddNonFloatingSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add deadline task
        taskToAdd = td.paper;
        assertAddNonFloatingSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        
        //add task with overlapping slot
        commandBox.runCommand(td.movie.getAddNonFloatingCommand());
        assertResultMessage(AddNonFloatingCommand.MESSAGE_TIMESLOT_OCCUPIED);
        assertTrue(floatingTaskListPanel.isListMatching(currentList));
        
        //add task with illegal time slot
        commandBox.runCommand("add illegal timeslot from 2 oct 2pm to 2 oct 1pm");
        assertResultMessage(AddNonFloatingCommand.MESSAGE_ILLEGAL_TIME_SLOT);
        assertTrue(floatingTaskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.trash);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddFloatingCommand());

        //confirm the new card contains the right data
        FloatingTaskCardHandle addedCard = floatingTaskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous floatingTasks plus the new floatingTask
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));
    }
    
    private void assertAddNonFloatingSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddNonFloatingCommand());

        //confirm the new card contains the right data
        FloatingTaskCardHandle addedCard = floatingTaskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous floatingTasks plus the new floatingTask
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));
    }

}
