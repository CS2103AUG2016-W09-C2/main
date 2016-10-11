package seedu.address.logic;

import com.google.common.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.core.EventsCenter;
import seedu.address.logic.commands.*;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.model.TaskListChangedEvent;
import seedu.address.model.TaskList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;
import seedu.address.storage.StorageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.*;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskList latestSavedTaskList;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskListChangedEvent abce) {
        latestSavedTaskList = new TaskList(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setup() {
        model = new ModelManager();
        String tempTaskListFile = saveFolder.getRoot().getPath() + "TempTaskList.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskListFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskList = new TaskList(model.getTaskList()); // last saved assumed to be up to date before.
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void teardown() {
        EventsCenter.clearSubscribers();
    }

//    @Test
//    public void execute_invalid() throws Exception {
//        String invalidCommand = "       ";
//        assertCommandBehavior(invalidCommand,
//                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
//    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'task list' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, ReadOnlyTaskList, List)
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, new TaskList(), Collections.emptyList());
    }

    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task list data are same as those in the {@code expectedTaskList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(String inputCommand, String expectedMessage,
                                       ReadOnlyTaskList expectedTaskList,
                                       List<? extends ReadOnlyTask> expectedShownList) throws Exception {

        //Execute the command
        CommandResult result = logic.execute(inputCommand);

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskList, model.getTaskList());
        assertEquals(expectedTaskList, latestSavedTaskList);
    }


//    @Test
//    public void execute_unknownCommandWord() throws Exception {
//        String unknownCommand = "uicfhmowqewca";
//        assertCommandBehavior(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    @Test
//    public void execute_help() throws Exception {
//        assertCommandBehavior("help", HelpCommand.SHOWING_HELP_MESSAGE);
//        assertTrue(helpShown);
//    }
//
//    @Test
//    public void execute_exit() throws Exception {
//        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT);
//    }
//
//    @Test
//    public void execute_clear() throws Exception {
//        TestDataHelper helper = new TestDataHelper();
//        model.addTask(helper.generateTask(1));
//        model.addTask(helper.generateTask(2));
//        model.addTask(helper.generateTask(3));
//
//        assertCommandBehavior("clear", ClearCommand.MESSAGE_SUCCESS, new TaskList(), Collections.emptyList());
//    }
//    
//
//
//    @Test
//    public void execute_add_invalidArgsFormat() throws Exception {
//        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFloatingCommand.MESSAGE_USAGE);
//        assertCommandBehavior(
//                "add t/hihi", expectedMessage);
//    }
//
//    @Test
//    public void execute_add_invalidTaskData() throws Exception {
//        assertCommandBehavior(
//                "add []\\[;]", Name.MESSAGE_NAME_CONSTRAINTS);
//        assertCommandBehavior(
//                "add Valid Name t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);
//
//    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskList expectedAB = new TaskList();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddFloatingCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_add_successful_non_floating_from_date_to_date() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.nonFloatingFromDateToDate();
        TaskList expectedAB = new TaskList();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());
    }
    
    @Test
    public void execute_add_successful_non_floating_by_date() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.nonFloatingByDate();
        TaskList expectedAB = new TaskList();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddNonFloatingCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());        
    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskList expectedAB = new TaskList();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task list

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddFloatingCommand.MESSAGE_DUPLICATE_TASK,
                expectedAB,
                expectedAB.getTaskList());
    }
    
    /**
     * The logic for block command is actually the same as add-non=floating commands.
     * */
    @Test
    public void execute_addOverlapSlot_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = new Task(new Name("Task one"), new UniqueTagList(),
        						  new TaskDate("2 oct 2am"), new TaskDate("2 oct 1pm"));
        Task toBeAddedAfter = new Task(new Name("Task two"), new UniqueTagList(),
				  new TaskDate("2 oct 10am"), new TaskDate("2 oct 11am"));
        TaskList expectedAB = new TaskList();
        expectedAB.addTask(toBeAdded);

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task list

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAddedAfter),
                AddNonFloatingCommand.MESSAGE_TIMESLOT_OCCUPIED,
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void execute_addIllegalSlot_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = new Task(new Name("Task one"), new UniqueTagList(),
        						  new TaskDate("2 oct 6am"), new TaskDate("2 oct 5am"));
        TaskList expectedAB = new TaskList();

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddNonFloatingCommand.MESSAGE_ILLEGAL_TIME_SLOT,
                expectedAB,
                expectedAB.getTaskList());

    }


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskList expectedAB = helper.generateTaskList(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare task list state
        helper.addToModel(model, 2);

        assertCommandBehavior("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage) throws Exception {
        assertCommandBehavior(commandWord , expectedMessage); //index missing
        assertCommandBehavior(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandBehavior(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandBehavior(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTasks(2);

        // set AB state to 2 tasks
        model.resetData(new TaskList());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandBehavior(commandWord + " 3", expectedMessage, model.getTaskList(), taskList);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTasks(3);

        TaskList expectedAB = helper.generateTaskList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTasks(3);

        TaskList expectedAB = helper.generateTaskList(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }


    @Test
    public void execute_find_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTasks(p1, pTarget1, p2, pTarget2);
        TaskList expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTasks(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTasks(p3, p1, p4, p2);
        TaskList expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTasks(pTarget1, p1, pTarget2, pTarget3);
        TaskList expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTasks(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandBehavior("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper{

        Task adam() throws Exception {
            Name name = new Name("go shopping with Adam Brown");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, tags);
        }
        
        Task nonFloatingFromDateToDate() throws Exception {
            Name name = new Name("non floating task from XXXX to XXXX");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            TaskDate startDate = new TaskDate("19 oct 10pm");
            TaskDate endDate = new TaskDate("20 oct 11am");
            return new Task(name, tags, startDate, endDate);
        }

        Task nonFloatingByDate() throws Exception {
            Name name = new Name(" non floating task by XXXX");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("tag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            TaskDate startDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
            TaskDate endDate = new TaskDate("20 oct 11am");
            return new Task(name, tags, startDate, endDate);
        }        
        
        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();
            cmd.append("add ");
            cmd.append(p.getName().toString());
            if(p.getType().equals(TaskType.NON_FLOATING)){
                generateAddNonFloatingCommand(p, cmd);
            }
            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }
            System.out.println(cmd.toString());
            return cmd.toString();
        }

        private void generateAddNonFloatingCommand(Task p, StringBuffer cmd) {            
            if (p.hasOnlyDateLine()) {
                generateAddNonFloatingCommandByDate(p, cmd);
            } else {
                generateAddNonFloatingCommandFromDateToDate(p, cmd);   
            }
        }

        private void generateAddNonFloatingCommandFromDateToDate(Task p, StringBuffer cmd) {
            cmd.append(" from ");
            cmd.append(p.getStartDate().getInputDate());
            cmd.append(" to ");
            cmd.append(p.getEndDate().getInputDate());
        }

        private void generateAddNonFloatingCommandByDate(Task p, StringBuffer cmd) {
            cmd.append(" by ").append(p.getEndDate().getInputDate());
        }
        
        /** Generates the correct block command based on the task given */
        String generateBlockCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("block ");
            
            cmd.append("from ");
            cmd.append(p.getStartDate().getFormattedDate());
            cmd.append(" to ");
            cmd.append(p.getEndDate().getFormattedDate());

            UniqueTagList tags = p.getTags();
            for(Tag t: tags){
                cmd.append(" t/").append(t.tagName);
            }
            System.out.println(cmd.toString());
            return cmd.toString();
        }

        /**
         * Generates an TaskList with auto-generated tasks.
         */
        TaskList generateTaskList(int numGenerated) throws Exception{
            TaskList taskList = new TaskList();
            addToTaskList(taskList, numGenerated);
            return taskList;
        }

        /**
         * Generates an TaskList based on the list of Tasks given.
         */
        TaskList generateTaskList(List<Task> tasks) throws Exception{
            TaskList taskList = new TaskList();
            addToTaskList(taskList, tasks);
            return taskList;
        }

        /**
         * Adds auto-generated Task objects to the given TaskList
         * @param taskList The TaskList to which the Tasks will be added
         */
        void addToTaskList(TaskList taskList, int numGenerated) throws Exception{
            addToTaskList(taskList, generateTasks(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskList
         */
        void addToTaskList(TaskList taskList, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                taskList.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception{
            addToModel(model, generateTasks(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
            for(Task p: tasksToAdd){
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        List<Task> generateTasks(int numGenerated) throws Exception{
            List<Task> tasks = new ArrayList<>();
            for(int i = 1; i <= numGenerated; i++){
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTasks(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
