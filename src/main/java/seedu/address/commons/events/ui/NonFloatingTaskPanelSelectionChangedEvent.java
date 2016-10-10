package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyNonFloatingTask;

/**
 * Represents a selection change in the floating Task List Panel
 */
public class NonFloatingTaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyNonFloatingTask newSelection;

    public NonFloatingTaskPanelSelectionChangedEvent(ReadOnlyNonFloatingTask newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyNonFloatingTask getNewSelection() {
        return newSelection;
    }
}
