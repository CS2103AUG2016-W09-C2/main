package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.events.ui.NonFloatingTaskPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyNonFloatingTask;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class NonFloatingTaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(NonFloatingTaskListPanel.class);
    private static final String FXML = "NonFloatingTaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyNonFloatingTask> nonFloatingTaskListView;

    public NonFloatingTaskListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static NonFloatingTaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyNonFloatingTask> taskList) {
        NonFloatingTaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new NonFloatingTaskListPanel());
        taskListPanel.configure(taskList);
        return taskListPanel;
    }
 
    private void configure(ObservableList<ReadOnlyNonFloatingTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyNonFloatingTask> taskList) {
        nonFloatingTaskListView.setItems(taskList);
        nonFloatingTaskListView.setCellFactory(listView -> new NonFloatingTaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        nonFloatingTaskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new NonFloatingTaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            nonFloatingTaskListView.scrollTo(index);
            nonFloatingTaskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class NonFloatingTaskListViewCell extends ListCell<ReadOnlyNonFloatingTask> {

        public NonFloatingTaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyNonFloatingTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(NonFloatingTaskCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }

}
