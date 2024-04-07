package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' event book file path.
     */
    Path getEventBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Sets the user prefs' event book file path.
     */
    void setEventBookFilePath(Path eventBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Replaces event book data with the data in {@code eventBook}.
     */
    void setEventBook(ReadOnlyEventBook eventBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the EventBook */
    ReadOnlyEventBook getEventBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Checks if the specified event exists in Event Book.
     *
     * @param event The event to check for existence.
     * @return {@code true} if the event exists; {@code false} otherwise.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the specified event from Event Book.
     *
     * @param target The event to be deleted.
     */
    void deleteEvent(Event target);

    /**
     * Adds a new event to Event Book.
     *
     * @param event The event to be added.
     */
    void addEvent(Event event);

    /**
     * Updates the information of an existing event.
     *
     * @param target      The event to be updated.
     * @param editedEvent The updated event information.
     */
    void setEvent(Event target, Event editedEvent);

    /**
     * Checks if an event is currently selected.
     *
     * @return {@code true} if an event is selected; {@code false} otherwise.
     */
    boolean isAnEventSelected();

    /**
     * Checks if the specified event is the currently selected event.
     *
     * @param event The event to compare with the selected event.
     * @return {@code true} if the specified event is selected; {@code false} otherwise.
     */
    boolean isSameSelectedEvent(Event event);

    /**
     * Selects the specified event.
     *
     * @param event The event to be selected.
     */
    void selectEvent(Event event);

    /**
     * Deselects the currently selected event.
     */
    void deselectEvent();

    /**
     * Checks if a person is part of the currently selected event.
     *
     * @param person The person to check for in the selected event.
     * @return {@code true} if the person is in the selected event; {@code false} otherwise.
     */
    boolean isPersonInSelectedEvent(Person person);

    /**
     * Adds a person to the currently selected event.
     *
     * @param person The person to be added to the selected event.
     */
    void addPersonToSelectedEvent(Person person);

    /**
     * Deletes a person from the currently selected event.
     *
     * @param person The person to be removed from the selected event.
     */
    void deletePersonFromSelectedEvent(Person person);

    /**
     * Returns an unmodifiable view of the selected event
     *
     * @return An unmodifiable view of the selected event.
     */
    ObservableValue<Event> getSelectedEvent();

    /**
     * Returns an unmodifiable view of the filtered event list.
     *
     * @return An unmodifiable view of the filtered event list.
     */
    ObservableList<Event> getFilteredEventList();

    /**
     * Returns an unmodifiable view of the filtered person list of the selected event.
     * If no event is selected, returns an empty list.
     *
     * @return An unmodifiable view of the filtered person list of the selected event,
     *         or an empty list if no event is selected.
     */
    ObservableList<Person> getFilteredPersonListOfSelectedEvent();

    /**
     * Updates the filter of the filtered person list of selected event to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonListOfSelectedEvent(Predicate<Person> predicate);


    void exportEventPersonData(boolean shouldExportName, boolean shouldExportPhone,
                               boolean shouldExportEmail, boolean shouldExportAddress) throws IOException;

    void exportGlobalPersonData(boolean shouldExportName, boolean shouldExportPhone,
                                boolean shouldExportEmail, boolean shouldExportAddress) throws IOException;

}
