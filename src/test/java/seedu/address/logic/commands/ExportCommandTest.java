package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.export.MockPersonDataExporter;
import seedu.address.export.PersonDataExporter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class ExportCommandTest {
    @Test
    public void equals() {
        ExportCommand exportAllCommand =
                new ExportCommand(true, true, true, true);
        assertEquals(exportAllCommand, exportAllCommand);

        ExportCommand exportAllCommandCopy =
                new ExportCommand(true, true, true, true);
        assertEquals(exportAllCommand, exportAllCommandCopy);

        assertNotNull(exportAllCommand);

        ExportCommand exportNameAndEmailCommand =
                new ExportCommand(true, false, true, false);
        assertNotEquals(exportAllCommand, exportNameAndEmailCommand);
    }

    @Test
    public void execute_export_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                getTypicalEventBook(), new PersonDataExporter());

        ExportCommand exportCommand =
                new ExportCommand(true, false, false, false);

        CommandResult expectedCommandResult = new CommandResult(ExportCommand.MESSAGE_EXPORT_SUCCESS);

        assertCommandSuccess(exportCommand, model, expectedCommandResult, model);
    }

    @Test
    public void execute_nullModel_failure() {
        ExportCommand exportCommand =
                new ExportCommand(true, true, true, true);
        assertThrows(NullPointerException.class, () -> exportCommand.execute(null));
    }

    @Test
    public void execute_exportFailure_throwsCommandException() {
        MockPersonDataExporter mockExporter = new MockPersonDataExporter();
        mockExporter.setThrowIoException(true);
        ModelManager model = new ModelManager(new AddressBook(), new UserPrefs(), new EventBook(), mockExporter);

        // Create the export command with any combination of flags
        ExportCommand exportCommand = new ExportCommand(true, true, true, true);

        // Assert that executing the export command with a failing exporter throws a CommandException
        assertThrows(CommandException.class, () -> exportCommand.execute(model),
                "Expected execute to throw CommandException, but it didn't.");
    }
}
