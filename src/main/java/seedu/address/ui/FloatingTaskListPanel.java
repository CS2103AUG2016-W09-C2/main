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
import seedu.address.commons.events.ui.FloatingTaskPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyFloatingTask;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class FloatingTaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(FloatingTaskListPanel.class);
    private static final String FXML = "FloatingTaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<ReadOnlyFloatingTask> floatingTaskListView;

    public FloatingTaskListPanel() {
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

    public static FloatingTaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ObservableList<ReadOnlyFloatingTask> taskList) {
        FloatingTaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new FloatingTaskListPanel());
        taskListPanel.configure(taskList);
        return taskListPanel;
    }

    private void configure(ObservableList<ReadOnlyFloatingTask> taskList) {
        setConnections(taskList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyFloatingTask> taskList) {
        floatingTaskListView.setItems(taskList);
        floatingTaskListView.setCellFactory(listView -> new FloatingTaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        floatingTaskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new FloatingTaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            floatingTaskListView.scrollTo(index);
            floatingTaskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class FloatingTaskListViewCell extends ListCell<ReadOnlyFloatingTask> {

        public FloatingTaskListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyFloatingTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(FloatingTaskCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }

}
