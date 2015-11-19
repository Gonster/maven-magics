package io.github.gonster.maven.magics.routes.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.github.gonster.maven.magics.routes.utils.ObjectUtils.isEmpty;

/**
 * <p>created on 2015/11/19</p>
 *
 * @author Gonster
 */
public class ClassResourceScanner {

    static final String DEFAULT_RESOURCE_PATTERN = "**" + File.separator + "*.java";

    private static final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    public static List<File> scan(String source, String bases) {
        List<File> classFiles = new ArrayList<>();
        System.out.println(source);
        for (String basePackage : bases.split(",")) {
            if(isEmpty(basePackage)) continue;
            try {
                String searchPath = source.replace("/", File.separator)
                        + File.separator + basePackage.replace(".", File.separator)
                        + File.separator + DEFAULT_RESOURCE_PATTERN;
                Resource[] resources = resolver.getResources(searchPath);
                for(Resource resource : resources) {
                    System.out.println(resource.getFile().toString());
                    File file = resource.getFile();
                    if(file == null) continue;
                    classFiles.add(file);
                }
            } catch (IOException e) {
                //continue;
            }
        }
        return classFiles;
    }
}
