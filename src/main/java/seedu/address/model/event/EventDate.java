package seedu.address.model.event;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents an Event's date.
 * Guarantees: immutable
 */
public class EventDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should be in dd-MM-yyyy format";

    public static final String VALIDATION_REGEX = "^([0-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-(\\d{4})$";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public final LocalDate eventDate;

    /**
     * Constructs a {@code EventDateTime}.
     *
     * @param eventDate A valid date.
     */
    public EventDate(String eventDate) {
        checkArgument(isValidDate(eventDate), MESSAGE_CONSTRAINTS);
        this.eventDate = parseEventDate(eventDate);
    }

    private static LocalDate parseEventDate(String eventDate) {
        try {
            // Directly parse the string with the formatter that supports both date and optional time
            return LocalDate.parse(eventDate, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Date format should be dd-MM-yyyy", ex);
        }
    }

    /**
     * Checks for date validity.
     *
     * @param test An input date.
     */
    public static boolean isValidDate(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            LocalDate.parse(test, DATE_TIME_FORMATTER);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EventDate)) {
            return false;
        }
        EventDate otherTime = (EventDate) other;
        return eventDate.equals(otherTime.eventDate);
    }

    @Override
    public String toString() {
        return eventDate.format(DATE_TIME_FORMATTER);
    }

    @Override
    public int hashCode() {
        return eventDate.hashCode();
    }
}
