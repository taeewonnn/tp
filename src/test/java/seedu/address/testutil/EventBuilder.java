package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_NAME = "Sample Event";
    public static final String DEFAULT_EVENT_DATE = "05-07-2024";

    private EventName eventName;

    private EventDate eventDate;
    private List<Person> personList;

    /**
     * Creates an {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        eventName = new EventName(DEFAULT_EVENT_NAME);
        eventDate = new EventDate(DEFAULT_EVENT_DATE);
        personList = new ArrayList<>();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventName = eventToCopy.getEventName();
        eventDate = eventToCopy.getEventDate();
        personList = eventToCopy.getPersonList();
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withEventName(String eventName) {
        this.eventName = new EventName(eventName);
        this.eventDate = new EventDate(DEFAULT_EVENT_DATE);
        return this;
    }

    /**
     * Sets the {@code UniquePersonList} of the {@code Event} that we are building.
     */
    public EventBuilder withPersonList(List<Person> personList) {
        this.personList = personList;
        return this;
    }

    /**
     * Adds a person to the {@code UniquePersonList} of the {@code Event} that we are building.
     */
    public EventBuilder withPerson(Person person) {
        this.personList.add(person);
        return this;
    }

    /**
     * Builds the event based on the fields filled in earlier
     * @return event with fields
     */
    public Event build() {
        Event event = new Event(eventName, eventDate);
        event.setPersons(personList);
        return event;
    }
}
