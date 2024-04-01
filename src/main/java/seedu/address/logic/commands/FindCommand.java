package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names or tags contain any of "
            + "the specified keywords (name: case-insensitive) / (tag: case-sensitive) and displays them "
            + "as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_NAME + "KEYWORD [MORE_KEYWORDS]...\n"
            + "or\n"
            + PREFIX_TAG + "KEYWORD\n"
            + PREFIX_TAG + "KEYWORD " + PREFIX_TAG + "KEYWORD ...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + " alice bob charlie\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + " friends\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + " friends " + PREFIX_TAG + " colleagues";

    private NameContainsKeywordsPredicate predicate;

    private TagContainsKeywordsPredicate tagPredicate;

    private int type;

    /**
     * Constructor with NameContainsKeywordsPredicate
     * @param predicate
     */
    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        type = 1;
    }

    /**
     * Constructor with TagContainsKeywordsPredicate
     * @param tagPredicate
     */
    public FindCommand(TagContainsKeywordsPredicate tagPredicate) {
        this.tagPredicate = tagPredicate;
        type = 2;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        CommandResult commandResult = null;
        if (type == 1) {
            model.updateFilteredPersonList(predicate);
            commandResult = new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
        } else if (type == 2) {
            model.updateFilteredPersonList(tagPredicate);
            commandResult = new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
        }
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
