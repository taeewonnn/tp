package seedu.address.export;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Person;

/**
 * Interface for exporting participant data.
 */
public interface PersonExporter {
    void setFilePath(Path path);
    /**
     * Exports a list of persons to a file.
     *
     * @param persons the list of persons to export.
     * @param exportName indicates whether names should be exported.
     * @param exportPhone indicates whether phone numbers should be exported.
     * @param exportEmail indicates whether email addresses should be exported.
     * @param exportAddress indicates whether addresses should be exported.
     * @throws IOException if an I/O error occurs.
     */
    void exportToCsv(FilteredList<Person> persons,
                     boolean exportName, boolean exportPhone,
                     boolean exportEmail, boolean exportAddress) throws IOException;
}
