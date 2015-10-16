package io.github.gonster.maven.magics.cmfm;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * a mojo that print "spell" to console, ๛ก(ｰ̀ωｰ́ก)biu~
 *
 * <p>created on 2015/10/14</p>
 *
 * @author Gonster
 */

@Mojo(name="cast")
public class CastMyFirstMojo extends AbstractMojo {

    public static final String DEFAULT_CAST = "My first maven mojo cast, SHA-KA-LA-KA!";

    @Parameter(property = "spell", defaultValue = DEFAULT_CAST)
    private String spell;

    public void setSpell(String spell) {
        this.spell = spell;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(spell);
    }
}
