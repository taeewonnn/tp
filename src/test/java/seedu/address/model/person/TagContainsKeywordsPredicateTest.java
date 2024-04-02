package seedu.address.model.person;

import org.junit.jupiter.api.Test;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<Tag> firstPredicateKeywordSet = new HashSet<>(Collections.singletonList(new Tag("first")));
        Set<Tag> secondPredicateKeywordSet = new HashSet<>(Set.of(new Tag("first"), new Tag("second")));

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordSet);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> tags = new HashSet<>(Set.of(new Tag("friends"), new Tag("colleagues")));
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(tags);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + tags + "}";
        assertEquals(expected, predicate.toString());
    }
}
