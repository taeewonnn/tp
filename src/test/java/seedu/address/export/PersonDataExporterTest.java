package seedu.address.export;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Person;

public class PersonDataExporterTest {
    private Path testFile;

    @BeforeEach
    public void setUp() throws IOException {
        testFile = Files.createTempFile("test", ".csv");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(testFile);
    }

    private String readFileContents(Path filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        }
        return contentBuilder.toString();
    }

    @Test
    public void exportToCsv_allFields_success() throws IOException {
        PersonDataExporter exporter = new PersonDataExporter();
        exporter.setFilePath(testFile);

        FilteredList<Person> persons = new FilteredList<>(FXCollections.observableArrayList(ALICE));

        exporter.exportToCsv(persons, true, true, true, true);

        String expected = "Name,Phone,Email,Address\n"
                + "Alice Pauline,94351253,alice@example.com,123; Jurong West Ave 6; #08-111\n";
        assertEquals(expected, readFileContents(testFile));
    }

    @Test
    public void exportToCsv_selectedFields_success() throws IOException {
        PersonDataExporter exporter = new PersonDataExporter();
        exporter.setFilePath(testFile);

        FilteredList<Person> persons = new FilteredList<>(FXCollections.observableArrayList(ALICE));

        exporter.exportToCsv(persons, true, false, true, false);

        String expected = "Name,Email\nAlice Pauline,alice@example.com\n";
        assertEquals(expected, readFileContents(testFile));
    }
}
