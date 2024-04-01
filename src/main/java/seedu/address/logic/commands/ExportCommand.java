package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports the chosen details of the filtered participants to a CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the details of all persons identified "
            + "by the command flags. "
            + "Flags: n/ (name), p/ (phone), e/ (email), a/ (address)\n"
            + "Example: " + COMMAND_WORD + " n/ p/";
    public static final String MESSAGE_EXPORT_SUCCESS = "Exported the specified participant data.";
    public static final String MESSAGE_FAILURE = "Failed to export to CSV file.";

    private final boolean exportName;
    private final boolean exportPhone;
    private final boolean exportEmail;
    private final boolean exportAddress;

    /**
     * Creates an ExportCommand to export the specified details of the filtered participants.
     */
    public ExportCommand(boolean exportName, boolean exportPhone, boolean exportEmail, boolean exportAddress) {
        this.exportName = exportName;
        this.exportPhone = exportPhone;
        this.exportEmail = exportEmail;
        this.exportAddress = exportAddress;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            model.exportPersonData(exportName, exportPhone, exportEmail, exportAddress);
            return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        ExportCommand e = (ExportCommand) other;

        return exportName == e.exportName
                && exportPhone == e.exportPhone
                && exportEmail == e.exportEmail
                && exportAddress == e.exportAddress;
    }
}
