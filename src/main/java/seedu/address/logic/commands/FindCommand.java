package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameAndTagCombinedPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords and tags.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and/or their tags match any of the specified tags, \n"
            + "and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME_KEYWORD [MORE_NAME_KEYWORDS]... t/TAG_KEYWORD [MORE_TAG_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice bob t/friends family";

    private final NameAndTagCombinedPredicate predicate;

    public FindCommand(NameAndTagCombinedPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
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
