package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Set;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        FindCommand findCommand = null;

        if (argMultimap.getValue(PREFIX_NAME).isPresent() && !argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_NAME).get().trim();

            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            String[] nameKeywords = trimmedArgs.split("\\s+");

            findCommand = new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else if (!argMultimap.getValue(PREFIX_NAME).isPresent()) {

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            if (tagList.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            findCommand = new FindCommand(new TagContainsKeywordsPredicate(tagList));
        }
        return findCommand;
    }
}

