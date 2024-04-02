package seedu.address.model.person;

import seedu.address.model.person.Person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Predicate that combines checks for a {@code Person}'s name and tags.
 */
public class NameAndTagCombinedPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final Set<Tag> tagKeywords;

    public NameAndTagCombinedPredicate(List<String> nameKeywords, Set<Tag> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(Person person) {
        boolean nameMatch = nameKeywords == null || nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        boolean tagMatch = tagKeywords == null || person.getTags().containsAll(tagKeywords);
        return nameMatch && tagMatch;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NameAndTagCombinedPredicate)) {
            return false;
        }

        NameAndTagCombinedPredicate otherPredicate = (NameAndTagCombinedPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords) && tagKeywords.equals(otherPredicate.tagKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("tagKeywords", tagKeywords)
                .toString();
    }
}
