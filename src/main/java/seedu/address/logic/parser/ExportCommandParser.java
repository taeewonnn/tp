package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_EXPORT_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        boolean shouldExportName = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean shouldExportPhone = argMultimap.getValue(PREFIX_PHONE).isPresent();
        boolean shouldExportEmail = argMultimap.getValue(PREFIX_EMAIL).isPresent();
        boolean shouldExportAddress = argMultimap.getValue(PREFIX_ADDRESS).isPresent();

        if (!(shouldExportName || shouldExportPhone || shouldExportEmail || shouldExportAddress)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_EXPORT_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        return new ExportCommand(shouldExportName, shouldExportPhone, shouldExportEmail, shouldExportAddress);
    }
}
