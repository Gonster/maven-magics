package io.github.gonster.maven.magics.routes.processor;

import io.github.gonster.maven.magics.routes.Route;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SpringMvcRequestMappingProcessorTest {

    private final File file = new File(getClass()
            .getResource("/projects/routes-mock/src/main/java/routes/test/TestController.java")
            .getPath());

    @Test
    public void testProcess() throws Exception {
        Processor processor = new SpringMvcRequestMappingProcessor();
        Route[] routes = processor.process(file);
        assertThat(routes.length, equalTo(2));
        Route route = routes[0];

        assertThat(route.getUrl(), equalTo("{ \"/test\", \"/test0\" }"));
        assertThat(route.getChildren().length, equalTo(3));

        assertThat(route.getChildren()[0].getHttpVerb().length,
                is(equalTo(1)));
        assertThat(Arrays.asList(route.getChildren()[0].getHttpVerb()),
                everyItem(is(equalTo("GET"))));

        assertThat(route.getChildren()[1].getHttpVerb().length,
                is(equalTo(2)));
        assertThat(Arrays.asList(route.getChildren()[1].getHttpVerb()),
                is(everyItem(is(anyOf(equalTo("GET"), equalTo("POST"))))));

        route = routes[1];

        assertThat(route.getUrl(), equalTo("/test2"));
        assertThat(route.getChildren().length, equalTo(2));

        assertThat(route.getChildren()[0].getUrl(),
                is(equalTo("/{id}")));

        assertThat(route.getChildren()[1].getUrl(),
                is(equalTo("/del")));


    }
}