package seedu.address.logic.commands;

import seedu.address.logic.URManager.Context;
import seedu.address.logic.URManager.NoAvailableCommandException;

public class UndoCommand extends Command{

	public static final String COMMAND_WORD = "u";
	public static final String MESSAGE_SUCCESS = "Undo successfully.";
    public static final String MESSAGE_FAIL = "No command to undo.";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
    	
    	try {
    		Context contextToUndo = urManager.getContextToUndo();
    		model.resetData(contextToUndo.getData());
    		model.updateFilteredListToShowAll();
    		return new CommandResult(MESSAGE_SUCCESS);
        } catch (NoAvailableCommandException nace){
        	return new CommandResult(MESSAGE_FAIL);
        }
    }

}
