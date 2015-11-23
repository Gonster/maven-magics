package io.github.gonster.maven.magics.routes.processor;

import io.github.gonster.maven.magics.routes.Route;

import java.io.File;

/**
 * <p>created on 2015/11/23</p>
 *
 * @author Gonster
 */
public interface Processor {
    Route[] process(File file);
}
