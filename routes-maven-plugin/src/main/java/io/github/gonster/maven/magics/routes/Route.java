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
    private String someClass;
    private String method;
    private String url;
    private Route[] children;
    private Route parent;

    public Route[] getChildren() {
        return children;
    }

    public void setChildren(Route[] children) {
        this.children = children;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Route getParent() {
        return parent;
    }

    public void setParent(Route parent) {
        this.parent = parent;
    }

    public String getSomeClass() {
        return someClass;
    }

    public void setSomeClass(String someClass) {
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

    @Override
    public String toString() {
        return "Route{" +
                "children=" + Arrays.toString(children) +
                ", source=" + source +
                ", someClass='" + someClass + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", parent=" + parent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;

        Route route = (Route) o;

        if (!Arrays.equals(children, route.children)) return false;
        if (method != null ? !method.equals(route.method) : route.method != null) return false;
        if (parent != null ? !parent.equals(route.parent) : route.parent != null) return false;
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
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }
}
