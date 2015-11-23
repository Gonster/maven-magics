package io.github.gonster.maven.magics.routes.processor;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class SpringMvcRequestMappingProcessorTest {

    private final File file = new File("F:\\MyDocuments\\md\\GitHub\\maven-magics\\routes-maven-plugin\\src\\test\\projects\\routes-mock\\src\\main\\java\\routes\\test\\TestController.java");

    @Test
    public void testProcess() throws Exception {
        Processor processor = new SpringMvcRequestMappingProcessor();
        processor.process(file);
    }
}