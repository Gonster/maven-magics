package io.github.gonster.maven.magics.routes.processor;

/**
 * <p>created on 2015/11/23</p>
 *
 * @author Gonster
 */
public class ProcessorFactory {

    private static final Processor springMvc = new SpringMvcRequestMappingProcessor();

    public static Processor getSpringMvcRequestMappingProcessor() {
        return springMvc;
    }
}
