package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsArrayOfPersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(6, samplePersons.length); // Assuming there are 6 sample persons
        // Add more assertions if needed to verify the contents of the sample persons
    }

    @Test
    public void getSampleEvents_returnsArrayOfEvents() {
        Event[] sampleEvents = SampleDataUtil.getSampleEvents();
        assertEquals(1, sampleEvents.length); // Assuming there is 1 sample event
        // Add more assertions if needed to verify the contents of the sample events
    }

    @Test
    public void getSampleAddressBook_returnsReadOnlyAddressBook() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();
        assertTrue(sampleAddressBook instanceof AddressBook);
        // Add more assertions if needed to verify the contents of the sample address book
    }

    @Test
    public void getSampleEventBook_returnsReadOnlyEventBook() {
        ReadOnlyEventBook sampleEventBook = SampleDataUtil.getSampleEventBook();
        assertTrue(sampleEventBook instanceof EventBook);
        // Add more assertions if needed to verify the contents of the sample event book
    }

    @Test
    public void getTagSet_returnsSetOfTags() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet("friends", "colleagues");
        assertEquals(2, tagSet.size());
        assertTrue(tagSet.contains(new Tag("friends")));
        assertTrue(tagSet.contains(new Tag("colleagues")));
    }
}
