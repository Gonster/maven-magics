package io.github.gonster.maven.magics.routes;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
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

@Mojo(name = "summary", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class SpringMvcRoutesMojo extends AbstractMojo {

    @Parameter(property = "bases", required = true)
    private String basePackages;

    @Parameter(property = "output", defaultValue = "${project.basedir}/routes.txt")
    private String output;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if(basePackages == null || "".equals(basePackages))
            throw new MojoExecutionException("output file must not be null or empty.");

        Map<String, Set<Route>> result = new HashMap<>(32);

        for (String basePackage : basePackages.split(",")) {
            if(isEmpty(basePackage)) continue;
        }

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
//        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(new SimpleBeanDefinitionRegistry(), false);
//        scanner.setIncludeAnnotationConfig(true);
//        scanner.addIncludeFilter(new AnnotationTypeFilter(RequestMapping.class));
//        scanner.scan(basePackages.split(","));
    }


    private Map<String, Set<Route>> resolve() {
        return null;
    }

    public String getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
