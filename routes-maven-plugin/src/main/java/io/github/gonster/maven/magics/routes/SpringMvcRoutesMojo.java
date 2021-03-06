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

    @Parameter(property = "print", defaultValue = "true")
    private Boolean print;

    @Parameter(property = "output", defaultValue = "${project.basedir}/routes.txt")
    private String output;

    @Parameter(property = "source", defaultValue = "${project.build.sourceDirectory}", readonly = true)
    private String source;

    @Parameter(property = "checkIsController", defaultValue = "false")
    private Boolean checkIsController;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if(bases == null || "".equals(bases))
            throw new MojoExecutionException("output file must not be null or empty.");

        Map<String, Set<Route>> result = new HashMap<>(32);

        List<File> classFiles = ClassResourceScanner.scan(source, bases, depth == null ? Integer.MAX_VALUE : depth);

        File out = null;
        FileWriter writer = null;
        try {
            if(!isEmpty(output)) {
                out = new File(output);
                try {
                    writer = new FileWriter(out);
                    writer.write("");
                    writer.flush();
                }
                catch (IOException e) {
                    getLog().warn("Failed to write to output file.");
                    closeWriter(writer);
                    writer = null;
                }
            }
            Processor processor = ProcessorFactory.getSpringMvcRequestMappingProcessor();
            for(File file : classFiles) {
                Route[] routes = processor.process(file);
                if(isEmpty(routes)) continue;
                for(Route route : routes) {
                    if(route == null) continue;
                    if(print != null && print) getLog().info(route.print(Route.CONSOLE));
                    if(writer != null) {
                        try {
                            writer.append(route.print());
                            writer.append(System.lineSeparator());
                        } catch (IOException e) {
                            getLog().warn("Failed to write to output file.");
                            closeWriter(writer);
                            writer = null;
                        }
                    }
                }
            }
        } finally {
            closeWriter(writer);
        }
    }

    private void closeWriter(FileWriter writer) {
        try {
            if(writer != null) {
                writer.flush();
                writer.close();
            }
        } catch (Exception ignored) {
        }
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

    public Boolean getCheckIsController() {
        return checkIsController;
    }

    public void setCheckIsController(Boolean checkIsController) {
        this.checkIsController = checkIsController;
    }

    public Boolean getPrint() {
        return print;
    }

    public void setPrint(Boolean print) {
        this.print = print;
    }
}
