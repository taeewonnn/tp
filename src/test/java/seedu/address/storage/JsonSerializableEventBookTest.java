package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EventBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableEventBookTest {

    @Test
    public void toModelType_validEventDetails_success() throws IllegalValueException {
        // Setup
        UniquePersonList a = new UniquePersonList();
        a.setPersons(TypicalPersons.getTypicalPersons());
        JsonAdaptedEvent jsonEvent1 = new JsonAdaptedEvent(new Event(new EventName("Event"),
                new EventDate("05-07-2024"), a));
        JsonAdaptedEvent jsonEvent2 = new JsonAdaptedEvent(new Event(new EventName("Event2"),
                new EventDate("05-07-2024"), a));
        List<JsonAdaptedEvent> jsonEvents = new ArrayList<>();
        jsonEvents.add(jsonEvent1);
        jsonEvents.add(jsonEvent2);
        JsonSerializableEventBook jsonEventBook = new JsonSerializableEventBook(jsonEvents);

        // Execute
        EventBook eventBook = jsonEventBook.toModelType();

        // Verify
        assertEquals(2, eventBook.getEventList().size());
        assertEquals(new Event(new EventName("Event"), new EventDate("05-07-2024"), a),
                eventBook.getEventList().get(0));
        assertEquals(new Event(new EventName("Event2"), new EventDate("05-07-2024"), a),
                eventBook.getEventList().get(1));
    }

    @Test
    public void toModelType_duplicateEvents_throwsIllegalValueException() {
        // Setup
        UniquePersonList a = new UniquePersonList();
        a.setPersons(TypicalPersons.getTypicalPersons());
        JsonAdaptedEvent jsonEvent1 = new JsonAdaptedEvent(new Event(new EventName("Event"),
                new EventDate("05-07-2024"), a));
        List<JsonAdaptedEvent> jsonEvents = new ArrayList<>();
        jsonEvents.add(jsonEvent1);
        jsonEvents.add(jsonEvent1); // Duplicate event
        JsonSerializableEventBook jsonEventBook = new JsonSerializableEventBook(jsonEvents);

        // Execute and Verify
        assertThrows(IllegalValueException.class, jsonEventBook::toModelType);
    }
}
