package seedu.address.ui;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.controlsfx.control.textfield.TextFields;

public class AutoCompleteTextField extends TextField
{

	private final SortedSet<String> dictionary;
	
	private ContextMenu dictionaryPopup;

	public AutoCompleteTextField() {
		super();
		dictionary = new TreeSet<>();
		setDictionary();
		dictionaryPopup = new ContextMenu();
		textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String oldString, String newString) {				
				if (getText().length() == 0){
					dictionaryPopup.hide();
				} else {
					LinkedList<String> searchResult = new LinkedList<>();
					searchResult.addAll(dictionary.subSet(getCurrentWord(), getCurrentWord() + Character.MAX_VALUE));
					if (dictionary.size() > 0){
						populatePopup(searchResult);
						if (!dictionaryPopup.isShowing()){
							dictionaryPopup.show(AutoCompleteTextField.this, Side.BOTTOM, getText().length()*8, 0);
						}
					} else {
						dictionaryPopup.hide();
					}
				}
			}
		});

		focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
				dictionaryPopup.hide();
			}
		});
	}

	/**
	 * Pop out the entry set with the given search results.
	 */
	private void populatePopup(List<String> searchResult) {
		
		List<CustomMenuItem> menuItems = new LinkedList<>();

		for (int i = 0; i < searchResult.size(); i++){
			final String result = searchResult.get(i);
			Label entryLabel = new Label(result);
			CustomMenuItem item = new CustomMenuItem(entryLabel, true);
			item.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent actionEvent) {
					setText(getPreviousWords() + result + " ");
					dictionaryPopup.hide();
					positionCaret(getText().length());
				}
			});
			menuItems.add(item);
		}
		dictionaryPopup.getItems().clear();
		dictionaryPopup.getItems().addAll(menuItems);

	}
	
	/**
	 * Sets the data of the dictionary used. Naive method. 
	 */
	private void setDictionary(){
		
		//syntax words
		dictionary.add("from");
		dictionary.add("to");
		dictionary.add("by");
		dictionary.add("t/");
		//command word
		String[] commandWords = {"add","block","cd","delete","done","help","u","r","find","list","select","exit"};
		for(String s: commandWords) dictionary.add(s);
		//date
		String[] dateWords = {"jan","feb","mar","apr","may","jun","jul",
							  "aug","sep","oct","nov","dec","today","tomorrow",
							  "mon","tue","wed","thur","fri","sat","sun",
							  "daily", "weekly", "monthly"};
		for(String s: dateWords) dictionary.add(s);
	}
	
	/**
	 * Returns current word that should be provided suggestions.
	 */
	private String getCurrentWord(){
		String[] enteredWords = getText().split(" ");
		return enteredWords[enteredWords.length - 1];
	}
	
	/**
	 * Returns existing text for future text modification. 
	 */
	private String getPreviousWords(){
		return getText().substring(0, getText().length() - getCurrentWord().length());
	}
}