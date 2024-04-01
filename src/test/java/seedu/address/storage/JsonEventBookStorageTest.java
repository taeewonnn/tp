package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;

public class JsonEventBookStorageTest {

    @TempDir
    public Path testFolder;

    private Path filePath;
    private JsonEventBookStorage jsonEventBookStorage;

    @BeforeEach
    public void setUp() {
        filePath = Paths.get(testFolder.toString(), "testEventBook.json");
        jsonEventBookStorage = new JsonEventBookStorage(filePath);
    }

    @Test
    public void getEventBookFilePath() {
        assertEquals(filePath, jsonEventBookStorage.getEventBookFilePath());
    }

    @Test
    public void readEventBook_noSuchFile_returnsEmpty() throws DataLoadingException {
        assertFalse(jsonEventBookStorage.readEventBook().isPresent());
    }

    // Add more tests as needed
}

