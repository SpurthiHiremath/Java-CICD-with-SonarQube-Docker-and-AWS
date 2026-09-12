package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private final Path outputFile = Path.of("target/index.html");

    @AfterEach
    void cleanUp() throws Exception {
        Files.deleteIfExists(outputFile);
    }

    @Test
    void shouldCreateHtmlFile() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
        assertTrue(Files.exists(outputFile));
    }

    @Test
    void shouldGenerateExpectedHtmlContent() throws Exception {
        App.main(new String[]{});

        String content = Files.readString(outputFile);

        assertTrue(content.contains("<title>Maven Project</title>"));
        assertTrue(content.contains("<h1>Hello Spurthi!</h1>"));
    }
}
