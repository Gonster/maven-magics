package io.github.gonster.maven.magics.routes;

import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;

import static org.junit.Assert.*;

public class SpringMvcRoutesMojoTest {

    @Rule
    public MojoRule rule = new MojoRule();

    @Rule
    public TestResources resources = new TestResources();

    @Test
    public void testExecute() throws Exception {
        File base = resources.getBasedir("routes-mock");
        assertThat(base, is(notNullValue()));
        File pom = new File( base, "pom.xml" );
        assertThat(pom, is(notNullValue()));
        SpringMvcRoutesMojo mojo = (SpringMvcRoutesMojo) rule.lookupMojo("summary", pom);
        assertThat(mojo, is(notNullValue()));
        mojo.execute();
    }
}