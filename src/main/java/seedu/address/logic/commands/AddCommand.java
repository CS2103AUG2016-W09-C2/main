package seedu.address.logic.commands;

public abstract class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static String MESSAGE_USAGE;

    public static String MESSAGE_SUCCESS;
    public static String MESSAGE_DUPLICATE_TASK;    
}
