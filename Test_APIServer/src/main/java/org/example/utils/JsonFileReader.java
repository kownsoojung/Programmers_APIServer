package org.example.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFileReader {
	
	private final String basePath = "/home/programmers/project/data/input";
	
	public String readJsonFile(String filename) throws IOException {
		Path path = Paths.get(basePath + filename);
		
        String content = new String(Files.readAllBytes(path));
        return content;
    }
}
