package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyNonFloatingTask;

public class NonFloatingTaskCard extends UiPart{

    private static final String FXML = "NonFloatingTaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML 
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label tags;

    private ReadOnlyNonFloatingTask task;
    private int displayedIndex;

    public NonFloatingTaskCard() {

    }

    public static NonFloatingTaskCard load(ReadOnlyNonFloatingTask task, int displayedIndex){
        NonFloatingTaskCard card = new NonFloatingTaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        startDate.setText("Start: " + task.getStartTaskDate().toString());
        endDate.setText("End: " + task.getEndTaskDate().toString());
        tags.setText(task.tagsString());
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
