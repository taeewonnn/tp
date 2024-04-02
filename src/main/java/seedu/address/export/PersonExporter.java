package seedu.address.export;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
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
     * @param shouldExportName indicates whether names should be exported.
     * @param shouldExportPhone indicates whether phone numbers should be exported.
     * @param shouldExportEmail indicates whether email addresses should be exported.
     * @param shouldExportAddress indicates whether addresses should be exported.
     * @throws IOException if an I/O error occurs.
     */
    void exportToCsv(ObservableList<Person> persons,
                     boolean shouldExportName, boolean shouldExportPhone,
                     boolean shouldExportEmail, boolean shouldExportAddress) throws IOException;

    boolean equals(Object other);
}
