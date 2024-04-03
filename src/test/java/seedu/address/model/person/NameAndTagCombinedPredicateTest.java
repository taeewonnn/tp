package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class NameAndTagCombinedPredicateTest {


    @Test
    public void testEquals() {
        List<String> nameKeywords1 = Arrays.asList("Alice", "Bob");
        List<String> nameKeywords2 = Arrays.asList("Alice", "Bob", "Charlie");
        Set<Tag> tagKeywords1 = new HashSet<>(Arrays.asList(new Tag("friend"), new Tag("family")));
        Set<Tag> tagKeywords2 = new HashSet<>(Collections.singletonList(new Tag("friend")));

        NameAndTagCombinedPredicate predicate1 = new NameAndTagCombinedPredicate(nameKeywords1, tagKeywords1);
        NameAndTagCombinedPredicate predicate2 = new NameAndTagCombinedPredicate(nameKeywords1, tagKeywords1);
        NameAndTagCombinedPredicate predicate3 = new NameAndTagCombinedPredicate(nameKeywords2, tagKeywords1);
        NameAndTagCombinedPredicate predicate4 = new NameAndTagCombinedPredicate(nameKeywords1, tagKeywords2);

        // same object -> returns true
        assertTrue(predicate1.equals(predicate1));

        // same values -> returns true
        assertTrue(predicate1.equals(predicate2));

        // different types -> returns false
        assertFalse(predicate1.equals(1));

        // null -> returns false
        assertFalse(predicate1.equals(null));

        // different nameKeywords -> returns false
        assertFalse(predicate1.equals(predicate3));

        // different tagKeywords -> returns false
        assertFalse(predicate1.equals(predicate4));
    }

    @Test
    public void testToString() {
        // Create a predicate with a non-null name predicate and a null tag predicate
        NameAndTagCombinedPredicate predicate = new NameAndTagCombinedPredicate(
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice")), null);

        // Expected output string
        String expected = "NameAndTagCombinedPredicate{namePredicate=seedu.address.model.person."
                + "NameContainsKeywordsPredicate{keywords=[Alice]}, tagPredicate=null}";

        // Check if the actual toString output matches the expected string
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void testTest_nameFilterOnly() {
        // Test for a person that matches the name filter only
        List<String> nameKeywords = Collections.singletonList("Alice");
        NameAndTagCombinedPredicate predicate = new NameAndTagCombinedPredicate(nameKeywords, null);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("friend").build()));

        // Test for a person that does not match the name filter
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withTags("friend").build()));
    }

    @Test
    public void testTest_tagFilterOnly() {
        // Test for a person that matches the tag filter only
        Set<Tag> tagKeywords = new HashSet<>(Collections.singletonList(new Tag("friend")));
        NameAndTagCombinedPredicate predicate = new NameAndTagCombinedPredicate(null, tagKeywords);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("friend").build()));
    }

}
