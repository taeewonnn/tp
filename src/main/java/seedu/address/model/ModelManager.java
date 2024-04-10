package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.export.PersonDataExporter;
import seedu.address.export.PersonExporter;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final EventBook eventBook;
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final PersonExporter personExporter;
    private final FilteredList<Event> filteredEvents;
    private final FilteredList<Person> filteredPersonsOfSelectedEvent;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyEventBook eventBook, PersonExporter personExporter) {
        requireAllNonNull(addressBook, userPrefs, eventBook, personExporter);

        logger.fine("Initializing with address book: " + addressBook
                + " and user prefs " + userPrefs + " and event book ");

        this.eventBook = new EventBook(eventBook);
        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.personExporter = personExporter;

        filteredEvents = new FilteredList<>(this.eventBook.getEventList());
        filteredPersonsOfSelectedEvent = new FilteredList<>(this.eventBook.getPersonsOfSelectedEventList());
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new EventBook(), new PersonDataExporter());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public Path getEventBookFilePath() {
        return userPrefs.getEventBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setEventBookFilePath(Path eventBookFilePath) {
        requireNonNull(eventBookFilePath);
        userPrefs.setEventBookFilePath(eventBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
        eventBook.editPersonInAllEvents(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredEvents.equals(otherModelManager.filteredEvents)
                && personExporter.equals(otherModelManager.personExporter);
    }

    //=========== EventBook ================================================================================

    @Override
    public void setEventBook(ReadOnlyEventBook eventBook) {
        this.eventBook.resetData(eventBook);
    }

    @Override
    public ReadOnlyEventBook getEventBook() {
        return eventBook;
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return eventBook.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) {
        requireNonNull(target);
        eventBook.removeEvent(target);
    }

    @Override
    public void addEvent(Event event) {
        requireNonNull(event);
        eventBook.addEvent(event);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);
        eventBook.setEvent(target, editedEvent);
    }

    @Override
    public boolean isAnEventSelected() {
        return eventBook.isAnEventSelected();
    }

    @Override
    public boolean isSameSelectedEvent(Event event) {
        requireNonNull(event);
        return eventBook.isSameSelectedEvent(event);
    }

    @Override
    public void selectEvent(Event event) {
        requireNonNull(event);
        eventBook.selectEvent(event);
        updateFilteredPersonListOfSelectedEvent(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void deselectEvent() {
        eventBook.deselectEvent();
        updateFilteredPersonListOfSelectedEvent(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean isPersonInSelectedEvent(Person person) {
        requireNonNull(person);
        return eventBook.isPersonInSelectedEvent(person);
    }

    @Override
    public void addPersonToSelectedEvent(Person person) {
        requireNonNull(person);
        eventBook.addPersonToSelectedEvent(person);
        updateFilteredPersonListOfSelectedEvent(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void deletePersonFromSelectedEvent(Person person) {
        requireNonNull(person);
        eventBook.deletePersonFromSelectedEvent(person);
    }

    //=========== Filtered Event List and Person List of Selected Event Accessors ====================================

    @Override
    public ObservableValue<Event> getSelectedEvent() {
        return eventBook.getSelectedEvent();
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public ObservableList<Person> getFilteredPersonListOfSelectedEvent() {
        return filteredPersonsOfSelectedEvent;
    }


    @Override
    public void updateFilteredPersonListOfSelectedEvent(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersonsOfSelectedEvent.setPredicate(predicate);
    }


    //=========== Exports ============================================================================================

    @Override
    public void exportEventPersonData(boolean shouldExportName, boolean shouldExportPhone,
                                      boolean shouldExportEmail, boolean shouldExportAddress) throws IOException {
        personExporter.exportToCsv(filteredPersonsOfSelectedEvent,
                shouldExportName, shouldExportPhone, shouldExportEmail, shouldExportAddress);
    }

    @Override
    public void exportGlobalPersonData(boolean shouldExportName, boolean shouldExportPhone,
                                       boolean shouldExportEmail, boolean shouldExportAddress) throws IOException {
        personExporter.exportToCsv(filteredPersons,
                shouldExportName, shouldExportPhone, shouldExportEmail, shouldExportAddress);
    }

}
