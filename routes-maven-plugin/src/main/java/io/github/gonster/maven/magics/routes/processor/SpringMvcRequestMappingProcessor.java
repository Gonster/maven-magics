package io.github.gonster.maven.magics.routes.processor;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import;
import io.github.gonster.maven.magics.routes.Route;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>created on 2015/11/23</p>
 *
 * @author Gonster
 */
public class SpringMvcRequestMappingProcessor implements Processor {

    public static final String MAPPING_AND_REST_WILD_CARD_IMPORT = "org.springframework.web.bind.annotation.*";
    public static final String REQUEST_MAPPING_WHOLE_NAME = "org.springframework.web.bind.annotation.RequestMapping";
    public static final String REQUEST_MAPPING = "RequestMapping";
    public static final String REST_CONTROLLER_WHOLE_NAME = "org.springframework.web.bind.annotation.RestController";
    public static final String REST_CONTROLLER = "RestController";
    public static final String CONTROLLER_WILD_CARD_IMPORT = "org.springframework.stereotype.*";
    public static final String CONTROLLER_WHOLE_NAME = "org.springframework.stereotype.Controller";
    public static final String CONTROLLER = "Controller";

    private static final String ROUTES = "routes";
    private static final String IMPORT_FLAG = "importFlag";
    private static final String CURRENT_ROUTE = "currentRoute";
    private static final String CURRENT_PACKAGE = "currentPackage";


    @Override
    public Route[] process(File file) {
        CompilationUnit cu;
        AnnotationVisitor visitor = new AnnotationVisitor();
        Map<String, Route> routeMap = new HashMap<>(4);
        Map<String, Object> params = new HashMap<>(4);
        params.put(ROUTES, routeMap);

        try {
            cu = JavaParser.parse(file);
            visitor.visit(cu, null);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return new Route[]{};
    }

    private boolean hasImportRequestMapping(String n) {
        return MAPPING_AND_REST_WILD_CARD_IMPORT.equals(n) || REQUEST_MAPPING_WHOLE_NAME.equals(n);
    }

    private boolean isRequestMapping(String a) {
        return REQUEST_MAPPING.equals(a) || REQUEST_MAPPING_WHOLE_NAME.equals(a);
    }

    private class AnnotationVisitor extends VoidVisitorAdapter {

        @Override
        public void visit(PackageDeclaration n, Object arg) {
            String packageName = n.getName().getName();
            Map<String, Object> m = (Map<String, Object>)arg;
            m.putIfAbsent(CURRENT_PACKAGE, packageName);
            super.visit(n, m);
        }

        @Override
        public void visit(ImportDeclaration n, Object arg) {
            String name = n.getName().getName();
            Map<String, Object> m = (Map<String, Object>)arg;
            if(hasImportRequestMapping(name))
                m.putIfAbsent(IMPORT_FLAG, true);
            super.visit(n, arg);
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
            if(n != null) {
                List<AnnotationExpr> annotations = n.getAnnotations();
                Route route = new Route();
                AnnotationExpr anno = null;
                for(AnnotationExpr annotation : annotations) {
                    String name = annotation.getName().getName();
                    if(isRequestMapping(name)) {
                        anno = annotation;
                        break;
                    }
                }
//                route.setSomeClass();
//                if(anno != null) {
//                    route.s
//                }

                super.visit(n, arg);
            }
        }

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            if(n != null)
                System.out.println(n.getAnnotations());
            super.visit(n, arg);
        }
    }
}
