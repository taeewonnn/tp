package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * A predicate that enables checks for a {@code Person}'s name and tags, by combining the
 * NameContainsKeywordsPredicate and TagContainsKeywordsPredicate predicates.
 */
public class NameAndTagCombinedPredicate implements Predicate<Person> {
    private final NameContainsKeywordsPredicate namePredicate;
    private final TagContainsKeywordsPredicate tagPredicate;

    /**
     * Constructs a predicate that combines checks for a {@code Person}'s name and tags.
     *
     * @param nameKeywords The list of name keywords to search for, or {@code null} if no name filtering is required.
     * @param tagKeywords  The set of tag keywords to search for, or {@code null} if no tag filtering is required.
     */
    public NameAndTagCombinedPredicate(List<String> nameKeywords, Set<Tag> tagKeywords) {
        this.namePredicate = nameKeywords != null ? new NameContainsKeywordsPredicate(nameKeywords) : null;
        this.tagPredicate = tagKeywords != null ? new TagContainsKeywordsPredicate(tagKeywords) : null;
    }

    /**
     * Constructs a predicate that combines the given name and tag predicates.
     *
     * @param namePredicate The predicate for name filtering, or {@code null} if no name filtering is required.
     * @param tagPredicate  The predicate for tag filtering, or {@code null} if no tag filtering is required.
     */
    public NameAndTagCombinedPredicate(NameContainsKeywordsPredicate namePredicate,
                                       TagContainsKeywordsPredicate tagPredicate) {
        this.namePredicate = namePredicate;
        this.tagPredicate = tagPredicate;
    }

    /**
     * Tests whether the given {@code Person} matches the combined name and tag criteria.
     *
     * @param person The person to test.
     * @return {@code true} if the person matches the combined criteria, {@code false} otherwise.
     */
    @Override
    public boolean test(Person person) {
        boolean nameMatch = namePredicate == null || namePredicate.test(person);
        boolean tagMatch = tagPredicate == null || tagPredicate.test(person);
        return nameMatch && tagMatch;
    }

    /**
     * Checks if this predicate is equal to another object.
     * Two predicates are considered equal if they have the same internal predicates.
     *
     * @param other The object to compare to.
     * @return {@code true} if the predicates are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof NameAndTagCombinedPredicate)) {
            return false;
        }

        NameAndTagCombinedPredicate otherPredicate = (NameAndTagCombinedPredicate) other;
        if (namePredicate == null && otherPredicate.namePredicate != null) {
            return false;
        }
        if (tagPredicate == null && otherPredicate.tagPredicate != null) {
            return false;
        }
        return (namePredicate == null || namePredicate.equals(otherPredicate.namePredicate))
                && (tagPredicate == null || tagPredicate.equals(otherPredicate.tagPredicate));
    }

    /**
     * Returns a string representation of this predicate.
     *
     * @return A string representation of this predicate.
     */
    @Override
    public String toString() {
        return "NameAndTagCombinedPredicate{"
                + "namePredicate=" + (namePredicate != null ? namePredicate.toString() : "null")
                + ", tagPredicate=" + (tagPredicate != null ? tagPredicate.toString() : "null")
                + '}';
    }

}
