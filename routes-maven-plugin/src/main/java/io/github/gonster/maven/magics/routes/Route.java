package io.github.gonster.maven.magics.routes;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * <p>created on 2015/11/18</p>
 *
 * @author Gonster
 */
public class Route {
    private File source;
    private Class someClass;
    private Method method;
    private String url;
    private Route[] children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;

        Route route = (Route) o;

        if (!Arrays.equals(children, route.children)) return false;
        if (method != null ? !method.equals(route.method) : route.method != null) return false;
        if (someClass != null ? !someClass.equals(route.someClass) : route.someClass != null) return false;
        if (source != null ? !source.equals(route.source) : route.source != null) return false;
        if (url != null ? !url.equals(route.url) : route.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (someClass != null ? someClass.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (children != null ? Arrays.hashCode(children) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
                "children=" + Arrays.toString(children) +
                ", source=" + source +
                ", someClass=" + someClass.getName() +
                ", method=" + method.getName() +
                ", url='" + url + '\'' +
                '}';
    }

    public Route[] getChildren() {
        return children;
    }

    public void setChildren(Route[] children) {
        this.children = children;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class getSomeClass() {
        return someClass;
    }

    public void setSomeClass(Class someClass) {
        this.someClass = someClass;
    }

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
