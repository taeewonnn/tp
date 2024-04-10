package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameAndTagCombinedPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords and tags.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and their tags include all of the specified tags, \n"
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "KEYWORD MORE_KEYWORDS...] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " n/alice bob t/friends t/family";

    private final NameAndTagCombinedPredicate predicate;

    public FindCommand(NameAndTagCombinedPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (model.isAnEventSelected()) {
            return findInPersonListOfSelectedEvent(model);
        } else {
            return findInGlobalPersonList(model);
        }
    }

    /**
     * Finds persons in the filtered person list of the selected event in the model based on a given predicate.
     * Updates the filtered person list accordingly.
     *
     * @param model The model containing the filtered person list of the selected event.
     * @return The result of finding persons in the filtered person list of the selected event.
     */
    public CommandResult findInPersonListOfSelectedEvent(Model model) {
        model.updateFilteredPersonListOfSelectedEvent(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        model.getFilteredPersonListOfSelectedEvent().size()));
    }

    /**
     * Finds persons in the global filtered person list in the model based on a given predicate.
     * Updates the global filtered person list accordingly.
     *
     * @param model The model containing the global filtered person list.
     * @return The result of finding persons in the global filtered person list.
     */
    public CommandResult findInGlobalPersonList(Model model) {
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
