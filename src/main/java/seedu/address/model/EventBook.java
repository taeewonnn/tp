package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the event-book level.
 * Duplicates are not allowed (by .isSameEvent comparison).
 */
public class EventBook implements ReadOnlyEventBook {

    private final UniqueEventList events;
    private final UniquePersonList personsOfSelectedEvent;
    private final SimpleObjectProperty<Event> selectedEventObservable;
    private Event selectedEvent;

    // Non-static initialization block
    {
        events = new UniqueEventList();
        personsOfSelectedEvent = new UniquePersonList();
        selectedEventObservable = new SimpleObjectProperty<>(selectedEvent);
    }

    /**
     * Creates an EventBook
     */
    public EventBook() {
    }

    /**
     * Creates an EventBook using the Events in the {@code toBeCopied}.
     */
    public EventBook(ReadOnlyEventBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Returns true if the selected event with the same identity as {@code event} exists in the event book.
     *
     * @param event The event to check.
     * @return {@code true} if the selected event exists; {@code false} otherwise.
     */
    public boolean isSameSelectedEvent(Event event) {
        assert event != null;
        return event.equals(selectedEvent);
    }

    // List overwrite operations

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     *
     * @param events The new list of events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code EventBook} with {@code newData}.
     *
     * @param newData The new data to be set.
     */
    public void resetData(ReadOnlyEventBook newData) {
        requireNonNull(newData);
        setEvents(newData.getEventList());
    }

    // Event-level operations

    /**
     * Returns true if an event with the same identity as {@code event} exists in the event book.
     *
     * @param event The event to check.
     * @return {@code true} if the event exists; {@code false} otherwise.
     */
    public boolean hasEvent(Event event) {
        assert event != null;
        return events.contains(event);
    }

    /**
     * Adds an event to the event book.
     *
     * @param event The event to be added.
     */
    public void addEvent(Event event) {
        assert event != null;
        events.add(event);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     *
     * @param target      The event to be replaced.
     * @param editedEvent The replacement event.
     */
    public void setEvent(Event target, Event editedEvent) {
        assert target != null;
        assert editedEvent != null;
        events.setEvent(target, editedEvent);
    }

    /**
     * Removes an event from the event book.
     *
     * @param event The event to be removed.
     */
    public void removeEvent(Event event) {
        assert event != null;;
        events.remove(event);
    }

    // Select Event Methods

    /**
     * Checks if an event is currently selected.
     *
     * @return {@code true} if an event is selected; {@code false} otherwise.
     */
    public boolean isAnEventSelected() {
        return selectedEvent != null;
    }

    /**
     * Selects an event from the event book.
     *
     * @param event The event to be selected.
     */
    public void selectEvent(Event event) {
        assert event != null;
        selectedEvent = event;
        selectedEventObservable.set(event);

        updatePersonListOfSelectedEvent();
    }

    /**
     * Deselects the currently selected event.
     */
    public void deselectEvent() {
        selectedEvent = null;
        selectedEventObservable.set(null);

        updatePersonListOfSelectedEvent();
    }

    /**
     * Checks if a person is part of the selected event.
     *
     * @param person The person to check.
     * @return {@code true} if the person is part of the selected event; {@code false} otherwise.
     */
    public boolean isPersonInSelectedEvent(Person person) {
        if (!isAnEventSelected()) {
            return false;
        }
        return selectedEvent.hasPerson(person);
    }

    /**
     * Adds a person to the selected event if an event is currently selected.
     *
     * @param person The person to be added.
     */
    public void addPersonToSelectedEvent(Person person) {
        assert(isAnEventSelected());

        if (isAnEventSelected()) {
            selectedEvent.addPerson(person);
            updatePersonListOfSelectedEvent();
        }

    }

    /**
     * Deletes a person from the selected event if an event is currently selected.
     *
     * @param person The person to be deleted.
     */
    public void deletePersonFromSelectedEvent(Person person) {
        assert(isAnEventSelected());

        if (isAnEventSelected()) {
            selectedEvent.deletePerson(person);
            updatePersonListOfSelectedEvent();
        }
    }

    /**
     * Replaces an existing person in all events with a new person.
     *
     * @param target      The person to be replaced.
     * @param editedPerson The replacement person.
     */
    public void editPersonInAllEvents(Person target, Person editedPerson) {
        for (Iterator<Event> it = events.iterator(); it.hasNext();) {
            Event event = it.next();
            if (event.hasPerson(target)) {
                event.setPerson(target, editedPerson);
            }
        }
        updatePersonListOfSelectedEvent();
    }

    private void updatePersonListOfSelectedEvent() {
        if (selectedEvent != null) {
            personsOfSelectedEvent.setPersons(selectedEvent.getPersonList());
        } else {
            personsOfSelectedEvent.setPersons(new ArrayList<>());
        }
    }

    // Util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("events", events)
                .toString();
    }

    @Override
    public ReadOnlyObjectProperty<Event> getSelectedEvent() {
        return selectedEventObservable;
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Person> getPersonsOfSelectedEventList() {
        return personsOfSelectedEvent.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventBook)) {
            return false;
        }

        EventBook otherEventBook = (EventBook) other;
        return events.equals(otherEventBook.events);
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}

