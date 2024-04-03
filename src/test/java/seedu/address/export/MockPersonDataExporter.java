package seedu.address.export;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * A mock PersonDataExporter for testing purposes that does not perform actual file operations.
 */
public class MockPersonDataExporter implements PersonExporter {
    private Path filePath;
    private boolean shouldThrowIoException = false;
    private List<ObservableList<Person>> exportedLists = new ArrayList<>();
    private List<Boolean> shouldExportNameFlags = new ArrayList<>();
    private List<Boolean> shouldExportPhoneFlags = new ArrayList<>();
    private List<Boolean> shouldExportEmailFlags = new ArrayList<>();
    private List<Boolean> shouldExportAddressFlags = new ArrayList<>();

    public void setThrowIoException(boolean shouldThrowIoException) {
        this.shouldThrowIoException = shouldThrowIoException;
    }

    @Override
    public void exportToCsv(ObservableList<Person> persons, boolean shouldExportName, boolean shouldExportPhone,
                            boolean shouldExportEmail, boolean shouldExportAddress) throws IOException {
        if (shouldThrowIoException) {
            throw new IOException("Mock IOException");
        }
        exportedLists.add(persons);
        shouldExportNameFlags.add(shouldExportName);
        shouldExportPhoneFlags.add(shouldExportPhone);
        shouldExportEmailFlags.add(shouldExportEmail);
        shouldExportAddressFlags.add(shouldExportAddress);
    }

    /**
     * Checks if the given list of persons has been exported with the specified export settings.
     *
     * @param persons              The filtered list of persons to check for export.
     * @param shouldExportName     A boolean indicating whether names should be exported.
     * @param shouldExportPhone    A boolean indicating whether phone numbers should be exported.
     * @param shouldExportEmail    A boolean indicating whether email addresses should be exported.
     * @param shouldExportAddress  A boolean indicating whether addresses should be exported.
     * @return                     {@code true} if the specified list of persons has been exported with
     *                             the exact same export settings as provided; {@code false} otherwise.
     */
    public boolean hasExported(ObservableList<Person> persons, boolean shouldExportName, boolean shouldExportPhone,
                               boolean shouldExportEmail, boolean shouldExportAddress) {
        for (int i = 0; i < exportedLists.size(); i++) {
            if (exportedLists.get(i) == persons
                    && shouldExportNameFlags.get(i) == shouldExportName
                    && shouldExportPhoneFlags.get(i) == shouldExportPhone
                    && shouldExportEmailFlags.get(i) == shouldExportEmail
                    && shouldExportAddressFlags.get(i) == shouldExportAddress) {
                return true;
            }
        }
        return false;
    }

    public int getNumberOfExports() {
        return exportedLists.size();
    }

    @Override
    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }
}
