package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.AddressBook;
import seedu.address.model.person.FloatingTask;
import seedu.address.model.person.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder(AddressBook addressBook){
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(FloatingTask person) throws UniqueTaskList.DuplicatePersonException {
        addressBook.addTask(person);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public AddressBook build(){
        return addressBook;
    }
}
