package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports the chosen details of the filtered participants to a CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the details of all filtered participants "
            + "identified by the command flags, only the fields specified by the flags will be exported.\n"
            + "Flags include: [n/] for Name, [p/] for Phone, [e/] for Email, [a/] for Address\n"
            + "Example: " + COMMAND_WORD + " n/"
            + "Example: " + COMMAND_WORD + " n/ p/ e/ a/";
    public static final String MESSAGE_EXPORT_SUCCESS = "Exported the specified participant data.";
    public static final String MESSAGE_FAILURE = "Failed to export to CSV file.";

    private final boolean shouldExportName;
    private final boolean shouldExportPhone;
    private final boolean shouldExportEmail;
    private final boolean shouldExportAddress;

    /**
     * Creates an ExportCommand to export the specified details of the filtered participants.
     */
    public ExportCommand(boolean shouldExportName, boolean shouldExportPhone,
                         boolean shouldExportEmail, boolean shouldExportAddress) {
        this.shouldExportName = shouldExportName;
        this.shouldExportPhone = shouldExportPhone;
        this.shouldExportEmail = shouldExportEmail;
        this.shouldExportAddress = shouldExportAddress;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            if (model.isAnEventSelected()) {
                model.exportEventPersonData(shouldExportName, shouldExportPhone,
                        shouldExportEmail, shouldExportAddress);
            } else {
                model.exportGlobalPersonData(shouldExportName, shouldExportPhone,
                        shouldExportEmail, shouldExportAddress);
            }

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

        return shouldExportName == e.shouldExportName
                && shouldExportPhone == e.shouldExportPhone
                && shouldExportEmail == e.shouldExportEmail
                && shouldExportAddress == e.shouldExportAddress;
    }
}
