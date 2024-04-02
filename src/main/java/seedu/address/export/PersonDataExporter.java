package seedu.address.export;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * A class responsible for exporting participant data to a CSV (Comma-Separated Values) file.
 * It implements the {@link PersonExporter} interface.
 *
 * <p>This exporter allows customizing which fields of the participant data are exported, such as
 * name, phone number, email, and address.</p>
 *
 * <p>The exported CSV file will contain a header row indicating the exported fields,
 * followed by rows containing the details of each participant.</p>
 */
public class PersonDataExporter implements PersonExporter {
    private Path filePath = Paths.get("exported_participant_data.csv");

    public PersonDataExporter() {
    }

    /**
     * Sets the file path for exporting participant data.
     *
     * @param filePath The file path to set for exporting participant data.
     */
    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Exports participant data to a CSV file with specified export options.
     *
     * @param persons           The list of persons to export.
     * @param shouldExportName  Whether to export participant names.
     * @param shouldExportPhone Whether to export participant phone numbers.
     * @param shouldExportEmail Whether to export participant email addresses.
     * @param shouldExportAddress Whether to export participant addresses.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    @Override
    public void exportToCsv(ObservableList<Person> persons,
                            boolean shouldExportName, boolean shouldExportPhone,
                            boolean shouldExportEmail, boolean shouldExportAddress) throws IOException {
        requireAllNonNull(persons, shouldExportName, shouldExportPhone, shouldExportEmail, shouldExportAddress);
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile(), false))) {
            StringBuilder header = new StringBuilder();

            writeHeader(shouldExportName, shouldExportPhone, shouldExportEmail,
                    shouldExportAddress, writer, header);
            writePersonDetails(persons, shouldExportName, shouldExportPhone,
                    shouldExportEmail, shouldExportAddress, writer);
        }
    }

    /**
     * Writes the header row for the CSV file based on export options.
     *
     * @param shouldExportName     Whether to export participant names.
     * @param shouldExportPhone    Whether to export participant phone numbers.
     * @param shouldExportEmail    Whether to export participant email addresses.
     * @param shouldExportAddress  Whether to export participant addresses.
     * @param writer               The PrintWriter to write to the CSV file.
     * @param header               The StringBuilder to construct the header row.
     */
    private void writeHeader(boolean shouldExportName, boolean shouldExportPhone, boolean shouldExportEmail,
                             boolean shouldExportAddress, PrintWriter writer, StringBuilder header) {
        if (shouldExportName) {
            header.append("Name,");
        }
        if (shouldExportPhone) {
            header.append("Phone,");
        }
        if (shouldExportEmail) {
            header.append("Email,");
        }
        if (shouldExportAddress) {
            header.append("Address,");
        }
        if (header.length() > 0) {
            header.setLength(header.length() - 1);
        }
        writer.println(header);
    }

    /**
     * Writes the details of each person to the CSV file based on export options.
     *
     * @param persons           The list of persons whose details are to be exported.
     * @param shouldExportName  Whether to export participant names.
     * @param shouldExportPhone Whether to export participant phone numbers.
     * @param shouldExportEmail Whether to export participant email addresses.
     * @param shouldExportAddress Whether to export participant addresses.
     * @param writer            The PrintWriter to write to the CSV file.
     */
    private void writePersonDetails(
            ObservableList<Person> persons, boolean shouldExportName, boolean shouldExportPhone,
            boolean shouldExportEmail, boolean shouldExportAddress, PrintWriter writer) {
        for (Person person : persons) {
            StringBuilder line = new StringBuilder();

            if (shouldExportName) {
                line.append(person.getName()).append(",");
            }
            if (shouldExportPhone) {
                line.append(person.getPhone()).append(",");
            }
            if (shouldExportEmail) {
                line.append(person.getEmail()).append(",");
            }
            if (shouldExportAddress) {
                String addressWithReplacedCommas =
                        person.getAddress().toString().replace(",", ";");
                line.append(addressWithReplacedCommas).append(",");
            }
            if (line.length() > 0) {
                line.setLength(line.length() - 1);
            }
            writer.println(line);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PersonDataExporter)) {
            return false;
        }

        PersonDataExporter e = (PersonDataExporter) other;

        return filePath.equals(e.filePath);
    }
}
