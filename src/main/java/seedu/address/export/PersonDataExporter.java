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
    public void exportToCsv(ObservableList<Person> persons,
                            boolean shouldExportName, boolean shouldExportPhone,
                            boolean shouldExportEmail, boolean shouldExportAddress) throws IOException {
        requireAllNonNull(persons, shouldExportName, shouldExportPhone, shouldExportEmail, shouldExportAddress);
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile(), false))) {
            StringBuilder header = new StringBuilder();
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
