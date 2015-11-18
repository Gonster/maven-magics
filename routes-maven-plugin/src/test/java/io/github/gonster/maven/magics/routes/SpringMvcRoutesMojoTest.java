package io.github.gonster.maven.magics.routes;

import org.apache.maven.plugin.testing.MojoRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpringMvcRoutesMojoTest {

    @Rule
    public MojoRule rule = new MojoRule()
    {
        @Override
        protected void before() throws Throwable
        {
        }

        @Override
        protected void after()
        {
        }
    };

    @Test
    public void testExecute() throws Exception {

    }
}