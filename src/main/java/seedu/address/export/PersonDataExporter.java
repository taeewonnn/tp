package seedu.address.export;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Person;

/**
 * Exports participant data.
 */
public class PersonDataExporter implements PersonExporter {
    private Path filePath = Paths.get("exported_participant_data.csv");

    public PersonDataExporter() {
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void exportToCsv(FilteredList<Person> persons,
                            boolean exportName, boolean exportPhone,
                            boolean exportEmail, boolean exportAddress) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile(), false))) {
            StringBuilder header = new StringBuilder();
            if (exportName) {
                header.append("Name,");
            }
            if (exportPhone) {
                header.append("Phone,");
            }
            if (exportEmail) {
                header.append("Email,");
            }
            if (exportAddress) {
                header.append("Address,");
            }
            if (header.length() > 0) {
                header.setLength(header.length() - 1);
            }
            writer.println(header);

            for (Person person : persons) {
                StringBuilder line = new StringBuilder();

                if (exportName) {
                    line.append(person.getName()).append(",");
                }
                if (exportPhone) {
                    line.append(person.getPhone()).append(",");
                }
                if (exportEmail) {
                    line.append(person.getEmail()).append(",");
                }
                if (exportAddress) {
                    String addressWithReplacedCommas =
                            person.getAddress().toString().replace(",", ";");
                    line.append(addressWithReplacedCommas).append(",");
                }
                if (line.length() > 0) {
                    line.setLength(line.length() - 1); // Remove trailing comma
                }
                writer.println(line);
            }
        }
    }
}
