package io.github.gonster.maven.magics.routes.processor;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import io.github.gonster.maven.magics.routes.Route;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>created on 2015/11/23</p>
 *
 * @author Gonster
 */
public class SpringMvcRequestMappingProcessor implements Processor {

    private final ThreadLocal<Map> routeMap = new ThreadLocal<>();

    @Override
    public Route[] process(File file) {
        CompilationUnit cu;
        AnnotationVisitor visitor = new AnnotationVisitor();
        routeMap.set(new HashMap<String, Route>(4));
        try {
            cu = JavaParser.parse(file);
            visitor.visit(cu, null);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return new Route[]{};
    }

    private boolean hasImportRequestMapping(CompilationUnit cu) {
        return true;
    }

    private static class AnnotationVisitor extends VoidVisitorAdapter {

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
            if(n != null)
                System.out.println(n.getAnnotations());
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            if(n != null)
                System.out.println(n.getAnnotations());
            super.visit(n, arg);
        }
    }
}
