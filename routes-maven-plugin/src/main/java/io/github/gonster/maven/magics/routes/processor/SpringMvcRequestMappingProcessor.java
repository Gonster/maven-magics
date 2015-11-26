package io.github.gonster.maven.magics.routes.processor;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.github.gonster.maven.magics.routes.Route;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static io.github.gonster.maven.magics.routes.utils.ObjectUtils.*;

/**
 * <p>created on 2015/11/23</p>
 *
 * @author Gonster
 */
@SuppressWarnings("unchecked")
public class SpringMvcRequestMappingProcessor implements Processor {

    public static final String MAPPING_AND_REST_WILD_CARD_IMPORT = "org.springframework.web.bind.annotation.*";
    public static final String REQUEST_MAPPING_WHOLE_NAME = "org.springframework.web.bind.annotation.RequestMapping";
    public static final String REQUEST_MAPPING = "RequestMapping";
    public static final String REST_CONTROLLER_WHOLE_NAME = "org.springframework.web.bind.annotation.RestController";
    public static final String REST_CONTROLLER = "RestController";
    public static final String CONTROLLER_WILD_CARD_IMPORT = "org.springframework.stereotype.*";
    public static final String CONTROLLER_WHOLE_NAME = "org.springframework.stereotype.Controller";
    public static final String CONTROLLER = "Controller";
    public static final String VALUE = "value";
    public static final String METHOD = "method";

    private static final String ROUTES = "routes";
    private static final String IMPORT_FLAG = "importFlag";
    private static final String CURRENT_ROUTE = "currentRoute";
    private static final String CURRENT_PACKAGE = "currentPackage";
    private static final String CURRENT_FILE = "currentFile";


    @Override
    public Route[] process(File file) {
        CompilationUnit cu;
        AnnotationVisitor visitor = new AnnotationVisitor();
        Set<Route> routeSet = new HashSet<>(4);
        Map<String, Object> params = new HashMap<>(4);
        params.put(ROUTES, routeSet);
        params.put(CURRENT_FILE, file);
        try {
            cu = JavaParser.parse(file);
            visitor.visit(cu, params);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return removeEmptyRoute(routeSet);
    }

    private Route[] removeEmptyRoute(Set<Route> routeSet) {
        for(Iterator<Route> i = routeSet.iterator(); i.hasNext();) {
            Route route = i.next();
            if(route == null) {
                i.remove();
                continue;
            }
            int childrenNum = route.getChildren() == null ? 0 : route.getChildren().length;
            boolean isUrlPresent = !isEmpty(route.getUrl());
            if(childrenNum < 0 && !isUrlPresent) i.remove();
        }
        return routeSet.toArray(new Route[routeSet.size()]);
    }

    private boolean isImportRequestMapping(String n) {
        return MAPPING_AND_REST_WILD_CARD_IMPORT.equals(n) || REQUEST_MAPPING_WHOLE_NAME.equals(n);
    }

    private boolean isRequestMapping(String a, Boolean flag) {
        flag = flag == null ? false : flag;
        return (flag && REQUEST_MAPPING.equals(a)) || REQUEST_MAPPING_WHOLE_NAME.equals(a);
    }

    private class AnnotationVisitor extends VoidVisitorAdapter {

        private String getStringLiteralExpression(Expression e) {
            return (e instanceof StringLiteralExpr) ? ((StringLiteralExpr) e).getValue() : e.toStringWithoutComments();
        }

        private Route resolveNormalRequestMapping(AnnotationExpr anno, Route route) {
            if(route == null) route = new Route();
            if(anno instanceof SingleMemberAnnotationExpr) {
                Expression e = ((SingleMemberAnnotationExpr) anno).getMemberValue();
                route.setUrl(getStringLiteralExpression(e));
            }
            else if(anno instanceof NormalAnnotationExpr) {
                NormalAnnotationExpr a = (NormalAnnotationExpr)anno;
                for(MemberValuePair pair : a.getPairs()) {
                    if (METHOD.equals(pair.getName())) {
                        Expression value = pair.getValue();
                        List<Node> nodeList = value.getChildrenNodes();
                        if (nodeList.isEmpty() || nodeList.size() == 1) {
                            String s = value.toStringWithoutComments();
                            s = s.substring(s.indexOf(".") + 1);
                            route.setHttpVerb(new String[]{s});
                        } else if (value instanceof ArrayInitializerExpr) {
                            ArrayInitializerExpr arrayInitializerExpr = (ArrayInitializerExpr) value;
                            List<Expression> expressionList = arrayInitializerExpr.getValues();
                            String[] verbs = new String[expressionList.size()];
                            for (int i = 0; i < expressionList.size(); i++) {
                                verbs[i] = expressionList.get(i).toStringWithoutComments();
                                verbs[i] = verbs[i].substring(verbs[i].lastIndexOf(".") + 1);
                            }
                            route.setHttpVerb(verbs);
                        }
                    }
                    else if (VALUE.equals(pair.getName())) {
                        Expression e = pair.getValue();
                        route.setUrl(getStringLiteralExpression(e));
                    }
                }
            }
            return route;
        }

        @Override
        public void visit(PackageDeclaration n, Object arg) {
            String packageName = n.getName().toStringWithoutComments();
            Map m = (Map)arg;
            m.putIfAbsent(CURRENT_PACKAGE, packageName);
            super.visit(n, m);
        }

        @Override
        public void visit(ImportDeclaration n, Object arg) {
            String name = n.getName().toStringWithoutComments();
            Map m = (Map)arg;
            if(isImportRequestMapping(name))
                m.putIfAbsent(IMPORT_FLAG, true);
            super.visit(n, m);
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
            if(n != null) {
                String className = n.getName();
                List<AnnotationExpr> annotations = n.getAnnotations();
                Map m = (Map)arg;
                Boolean importFlag = (Boolean)m.get(IMPORT_FLAG);
                Route route = new Route();
                AnnotationExpr anno = null;
                for(AnnotationExpr annotation : annotations) {
                    String name = annotation.getName().toStringWithoutComments();
                    if(isRequestMapping(name, importFlag)) {
                        anno = annotation;
                        break;
                    }
                }
                route.setSomeClass(m.get(CURRENT_PACKAGE) + "." + className);
                route.setSource((File)m.get(CURRENT_FILE));
                route.setChildren(new Route[]{});
                if(anno != null) route = resolveNormalRequestMapping(anno, route);
                m.put(CURRENT_ROUTE, route);
                Set routes = (Set)m.get(ROUTES);
                routes.add(route);
            }
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            if(n != null) {
                String methodName = n.getName();
                Map m = (Map)arg;

                AnnotationExpr anno = null;
                List<AnnotationExpr> annotations = n.getAnnotations();
                Boolean importFlag = (Boolean)m.get(IMPORT_FLAG);
                for(AnnotationExpr annotation : annotations) {
                    String name = annotation.getName().toStringWithoutComments();
                    if(isRequestMapping(name, importFlag)) {
                        anno = annotation;
                        break;
                    }
                }

                if(anno != null) {
                    Route parent = (Route)m.get(CURRENT_ROUTE);
                    Route current = new Route();
                    current.setMethod(methodName);
                    current.setSource(parent.getSource());
                    current.setParent(parent);
                    current.setSomeClass(parent.getSomeClass());
                    current = resolveNormalRequestMapping(anno, current);
                    if(!isEmpty(current.getUrl())) {
                        List<Route> routeList = new LinkedList<>(Arrays.asList(parent.getChildren()));
                        routeList.add(current);
                        parent.setChildren(routeList.toArray(parent.getChildren()));
                    }
                }

            }
            super.visit(n, arg);
        }
    }
}
