package br.com.rodrigo.pipeline.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    static List<String> matchesList = new ArrayList<String>();

    public static List<String> findFilesStartWith(String folderPath, String prefix) throws IOException {
            matchesList.clear();

            FileVisitor<Path> matcherVisitor = new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + prefix);
                    Path name = file.getFileName();

                    if (matcher.matches(name)) {
                        matchesList.add(name.toString());
                    }
                    return FileVisitResult.CONTINUE;
                }
            };
            Files.walkFileTree(Path.of(folderPath), matcherVisitor);
            return matchesList;
    }
}
