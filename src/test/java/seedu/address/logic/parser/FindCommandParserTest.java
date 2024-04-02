package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameAndTagCombinedPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommandBasedOnName() {
        // Test for finding by name
        NameAndTagCombinedPredicate predicate = new NameAndTagCombinedPredicate(
                Collections.singletonList("Alice"), null);
        FindCommand expectedFindCommandByName = new FindCommand(predicate);
        assertParseSuccess(parser, VALID_NAME_DESC, expectedFindCommandByName);
    }

    @Test
    public void parse_validArgs_returnsFindCommandBasedOnTag() {
        // Test for finding by tag
        NameAndTagCombinedPredicate predicate = new NameAndTagCombinedPredicate(
                null, Collections.singleton(new Tag("friend")));
        FindCommand expectedFindCommandByTag = new FindCommand(predicate);
        assertParseSuccess(parser, VALID_TAG_DESC, expectedFindCommandByTag);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test for invalid argument (empty string)
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Test for invalid argument (only whitespaces)
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
