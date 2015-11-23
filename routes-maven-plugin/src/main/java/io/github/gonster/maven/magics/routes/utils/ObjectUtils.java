package io.github.gonster.maven.magics.routes.utils;

/**
 * <p>created on 2015/11/18</p>
 *
 * @author Gonster
 */
public class ObjectUtils {

    static public boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }
    static public <T> boolean isEmpty(T[] ts) {
        return ts == null || ts.length < 1;
    }
}
