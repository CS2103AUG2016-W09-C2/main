package guitests;

import org.junit.Test;

import seedu.address.model.task.ReadOnlyTask;

import static org.junit.Assert.assertEquals;

public class SelectCommandTest extends TaskListGuiTest {


    @Test
    public void selectPerson_nonEmptyList() {

        assertSelectionInvalid(10); //invalid index
        assertNoPersonSelected();

        assertSelectionSuccess(1); //first floatingTask in the list
        int floatingTaskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(floatingTaskCount); //last floatingTask in the list
        int middleIndex = floatingTaskCount / 2;
        assertSelectionSuccess(middleIndex); //a floatingTask in the middle of the list

        assertSelectionInvalid(floatingTaskCount + 1); //invalid index
        assertPersonSelected(middleIndex); //assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectPerson_emptyList(){
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Task: "+index);
        assertPersonSelected(index);
    }

    private void assertPersonSelected(int index) {
        assertEquals(floatingTaskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedPerson = floatingTaskListPanel.getSelectedTasks().get(0);
        assertEquals(floatingTaskListPanel.getTask(index-1), selectedPerson);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoPersonSelected() {
        assertEquals(floatingTaskListPanel.getSelectedTasks().size(), 0);
    }

}
