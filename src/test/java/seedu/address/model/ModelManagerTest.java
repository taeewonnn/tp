package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.getBingoEvent;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.export.MockPersonDataExporter;
import seedu.address.export.PersonDataExporter;
import seedu.address.export.PersonExporter;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.EventBookBuilder;
import seedu.address.testutil.EventBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new EventBook(), new EventBook(modelManager.getEventBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void setEventBookFilePath_validPath_success() {
        Path path = Paths.get("event/book/file/path");
        modelManager.setEventBookFilePath(path);
        assertEquals(path, modelManager.getEventBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredEventList().remove(0));
    }
    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInEventBook_returnsFalse() {
        assertFalse(modelManager.hasEvent(getBingoEvent()));
    }


    @Test
    public void hasEvent_eventInEventBook_returnsTrue() {
        modelManager.addEvent(getBingoEvent());
        assertTrue(modelManager.hasEvent(getBingoEvent()));
    }

    @Test
    public void deleteEvent_nullEvent_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> modelManager.deleteEvent(null));
    }

    @Test
    public void deleteEvent_eventNotInEventBook_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> modelManager.deleteEvent(getBingoEvent()));
    }

    @Test
    public void deleteEvent_eventInEventBook_removesEvent() {
        modelManager.addEvent(getBingoEvent());
        modelManager.deleteEvent(getBingoEvent());
        assertFalse(modelManager.hasEvent(getBingoEvent()));
    }

    @Test
    public void setEventBook_nullEventBook_throwsNullPointerException() {
        ModelManager modelManager = new ModelManager();
        assertThrows(NullPointerException.class, () -> modelManager.setEventBook(null));
    }

    @Test
    public void setEventBook_validEventBook_success() {
        ModelManager modelManager = new ModelManager();
        ReadOnlyEventBook newEventBook = new EventBook();
        modelManager.setEventBook(newEventBook);
        assertEquals(newEventBook, modelManager.getEventBook());
    }

    @Test
    public void setEvent_nullTargetAndEditedEvent_throwsNullPointerException() {
        ModelManager modelManager = new ModelManager();
        assertThrows(NullPointerException.class, () -> modelManager.setEvent(null, null));
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        ModelManager modelManager = new ModelManager();
        Event editedEvent = new EventBuilder().build();
        assertThrows(NullPointerException.class, () -> modelManager.setEvent(null, editedEvent));
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        ModelManager modelManager = new ModelManager();
        Event targetEvent = new EventBuilder().build();
        assertThrows(NullPointerException.class, () -> modelManager.setEvent(targetEvent, null));
    }

    @Test
    public void setEvent_validTargetAndEditedEvent_success() {
        ModelManager modelManager = new ModelManager();
        Event targetEvent = new EventBuilder().build();
        Event editedEvent = new EventBuilder().withEventName("Edited Event").build();
        modelManager.addEvent(targetEvent);
        modelManager.setEvent(targetEvent, editedEvent);
        assertTrue(modelManager.getFilteredEventList().contains(editedEvent));
    }

    @Test
    public void isEqualSelectedEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.isSameSelectedEvent(null));
    }

    @Test
    public void exportEventPersonData_success() {
        MockPersonDataExporter mockExporter = new MockPersonDataExporter();
        ModelManager modelManager = new ModelManager(
                new AddressBook(), new UserPrefs(), new EventBook(), mockExporter);

        Event event = getBingoEvent();
        modelManager.addEvent(event);
        modelManager.selectEvent(event);

        modelManager.addPersonToSelectedEvent(ALICE);
        modelManager.addPersonToSelectedEvent(BENSON);

        // Execute export
        try {
            modelManager.exportEventPersonData(
                    true, true, true, true);
        } catch (IOException e) {
            fail("Should not have thrown IOException.");
        }

        // Verify that the export was attempted with the correct parameters
        assertTrue(mockExporter.hasExported(modelManager.getFilteredPersonListOfSelectedEvent(),
                true, true, true, true));
    }

    @Test
    public void exportGlobalPersonData_success() {
        MockPersonDataExporter mockExporter = new MockPersonDataExporter();
        ModelManager modelManager = new ModelManager(
                new AddressBook(), new UserPrefs(), new EventBook(), mockExporter);

        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);

        try {
            modelManager.exportGlobalPersonData(
                    true, true, true, true);
        } catch (IOException e) {
            fail("Should not have thrown IOException.");
        }

        assertTrue(mockExporter.hasExported(modelManager.getFilteredPersonList(),
                true, true, true, true));
    }

    @Test
    public void exportEventPersonData_failure_throwsIoException() {
        MockPersonDataExporter mockExporter = new MockPersonDataExporter();
        mockExporter.setThrowIoException(true);
        ModelManager modelManager = new ModelManager(
                new AddressBook(), new UserPrefs(), new EventBook(), mockExporter);

        Event event = getBingoEvent();
        modelManager.addEvent(event);
        modelManager.selectEvent(event);
        modelManager.addPersonToSelectedEvent(ALICE);

        assertThrows(IOException.class, () -> modelManager.exportEventPersonData(
                true, false, false, false));
    }

    @Test
    public void exportGlobalPersonData_failure_throwsIoException() {
        MockPersonDataExporter mockExporter = new MockPersonDataExporter();
        mockExporter.setThrowIoException(true);
        ModelManager modelManager = new ModelManager(
                new AddressBook(), new UserPrefs(), new EventBook(), mockExporter);

        modelManager.addPerson(ALICE);

        assertThrows(IOException.class, () -> modelManager.exportGlobalPersonData(
                false, true, false, false));
    }


    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        EventBook eventBook = new EventBookBuilder().withEvent(getBingoEvent()).build();
        AddressBook differentAddressBook = new AddressBook();
        EventBook differentEventBook = new EventBook();
        UserPrefs userPrefs = new UserPrefs();
        PersonExporter personExporter = new PersonDataExporter();
        PersonExporter differentPersonExporter = new PersonDataExporter();
        differentPersonExporter.setFilePath(Path.of("otherFilePath"));

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, eventBook, personExporter);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, eventBook, personExporter);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs, eventBook, personExporter)));

        // different personExporter -> returns false
        assertFalse(modelManager.equals(
                new ModelManager(differentAddressBook, userPrefs, eventBook, differentPersonExporter)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, eventBook, personExporter)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, eventBook, personExporter)));
    }
}
