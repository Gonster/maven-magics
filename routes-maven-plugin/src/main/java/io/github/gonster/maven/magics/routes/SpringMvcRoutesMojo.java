package io.github.gonster.maven.magics.routes;

import io.github.gonster.maven.magics.routes.processor.Processor;
import io.github.gonster.maven.magics.routes.processor.ProcessorFactory;
import io.github.gonster.maven.magics.routes.utils.ClassResourceScanner;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.gonster.maven.magics.routes.utils.ObjectUtils.*;

/**
 * A mojo that aggregates all spring MVC request mapping routes.
 *
 * <p>created on 2015/11/18</p>
 *
 * @author Gonster
 */

@Mojo(name = "summary", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class SpringMvcRoutesMojo extends AbstractMojo {

    @Parameter(property = "bases", required = true)
    private String bases;

    @Parameter(property = "encoding", defaultValue = "UTF-8")
    private String encoding;

    @Parameter(property = "depth", defaultValue = "256")
    private Integer depth;

    @Parameter(property = "output", defaultValue = "${project.basedir}\\routes.txt")
    private String output;

    @Parameter(property = "source", defaultValue = "${project.build.sourceDirectory}", readonly = true)
    private String source;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if(bases == null || "".equals(bases))
            throw new MojoExecutionException("output file must not be null or empty.");

        Map<String, Set<Route>> result = new HashMap<>(32);

        List<File> classFiles = ClassResourceScanner.scan(source, bases, depth);

        Processor processor = ProcessorFactory.getSpringMvcRequestMappingProcessor();
        for(File file : classFiles) {
            Route[] routes = processor.process(file);
            if(isEmpty(routes)) continue;
            for(Route route : routes) {
                if(route == null) continue;
                getLog().info(route.toString());
            }
        }
//        File out = new File(output);
//        try (FileWriter writer = new FileWriter(out)) {
//            if(out.exists()) {
//                final boolean fs = out.delete();
//            }
//            final boolean fc = out.createNewFile();
//            for (String basePackage : bases.split(",")) {
//                if(isEmpty(basePackage)) continue;
//
//            }
//        } catch (IOException e) {
//            getLog().error("failed to create the output file.");
//        }
    }

    private Map<String, Set<Route>> resolve() {
        return null;
    }

    public String getBases() {
        return bases;
    }

    public void setBases(String bases) {
        this.bases = bases;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
