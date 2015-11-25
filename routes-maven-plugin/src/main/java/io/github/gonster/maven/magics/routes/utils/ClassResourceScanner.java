package io.github.gonster.maven.magics.routes.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static io.github.gonster.maven.magics.routes.utils.ObjectUtils.isEmpty;

/**
 * <p>created on 2015/11/19</p>
 *
 * @author Gonster
 */
public class ClassResourceScanner {

    public static final String DEFAULT_RESOURCE_PATTERN = ".java";
    public static final String FILE_EXTENSION_SEPARATOR = ".";

    public static List<File> scan(String source, String bases, int depth) {
        final List<File> classFiles = new ArrayList<>();
        for (String basePackage : bases.split(",")) {
            if(isEmpty(basePackage)) continue;
            try {
                String searchPath = source.replace("/", File.separator)
                        + File.separator + basePackage.replace(".", File.separator)
                        + File.separator;
                Path start = Paths.get(searchPath);
                Files.walkFileTree(start, EnumSet.noneOf(FileVisitOption.class), depth, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if(file != null) {
                            File f = file.toFile();
                            if(isJavaFile(f))
                                classFiles.add(f);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                //continue;
            }
        }
        return classFiles;
    }

    private static String getFileExtension(File f) {
        String name = f.getName();
        int i = name.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        return i == -1 ? "" : name.substring(i);
    }

    private static boolean isJavaFile(File f) {
        return DEFAULT_RESOURCE_PATTERN.equals(getFileExtension(f));
    }
}
