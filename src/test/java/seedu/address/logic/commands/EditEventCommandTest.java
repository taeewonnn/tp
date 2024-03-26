package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getBingoEvent;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.testutil.EventBuilder;


public class EditEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        model.addEvent(getBingoEvent());
        Event editedEvent = new EventBuilder().withEventName("New Event Name").build();
        EditEventDescriptor descriptor = new EditEventDescriptor();
        descriptor.withName(new EventName("New Event Name"));
        EditEventCommand editCommand = new EditEventCommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent.getEventName());

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addEvent(editedEvent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

}
