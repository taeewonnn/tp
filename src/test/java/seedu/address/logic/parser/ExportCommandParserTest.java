package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {
    @Test
    public void parse_validArgs_success() {
        ExportCommand expectedExportAllCommand =
                new ExportCommand(true, true, true, true);
        String userInputAll = " " + PREFIX_NAME + " " + PREFIX_PHONE + " "
                + PREFIX_EMAIL + " " + PREFIX_ADDRESS;
        assertParseSuccess(new ExportCommandParser(), userInputAll, expectedExportAllCommand);

        ExportCommand expectedExportNameEmailCommand =
                new ExportCommand(true, false, true, false);
        String userInputNameEmail = " " + PREFIX_NAME + " " + PREFIX_EMAIL;
        assertParseSuccess(new ExportCommandParser(), userInputNameEmail, expectedExportNameEmailCommand);

        ExportCommand expectedExportAddressCommand =
                new ExportCommand(false, false, false, true);
        String userInputAddress = " " + PREFIX_ADDRESS;
        assertParseSuccess(new ExportCommandParser(), userInputAddress, expectedExportAddressCommand);
    }

    @Test
    public void parse_noFieldsProvided_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

        assertParseFailure(new ExportCommandParser(), "", expectedMessage);

        assertParseFailure(new ExportCommandParser(), "     ", expectedMessage);
    }
}
