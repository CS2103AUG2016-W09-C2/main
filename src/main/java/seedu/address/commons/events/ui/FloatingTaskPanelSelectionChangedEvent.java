package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyFloatingTask;

/**
 * Represents a selection change in the floating Task List Panel
 */
public class FloatingTaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyFloatingTask newSelection;

    public FloatingTaskPanelSelectionChangedEvent(ReadOnlyFloatingTask newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyFloatingTask getNewSelection() {
        return newSelection;
    }
}
