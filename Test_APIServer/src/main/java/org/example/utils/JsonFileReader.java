package org.example.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileReader {
	public String readJsonFile(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return content;
    }
}
