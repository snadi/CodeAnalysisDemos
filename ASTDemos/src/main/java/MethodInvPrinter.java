/**
 * Created by snadi on 2017-09-11.
 * Based on https://www.programcreek.com/2011/01/a-complete-standalone-example-of-astparser/
 */

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.*;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class MethodInvPrinter {
    public static void main(String args[]) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        String fileContent = Utils.getFile("test-inv.java");


        parser.setSource(fileContent.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        cu.accept(new ASTVisitor() {

            public boolean visit(MethodDeclaration methodDecl) {
                SimpleName methodName = methodDecl.getName();
                System.out.println(methodName + ":");
                return true;
            }

            public boolean visit(MethodInvocation methodInv) {
                SimpleName methodCallName = methodInv.getName();
                List arguments = methodInv.arguments();
                System.out.println("\t" + methodInv.getExpression() + "." + methodCallName + "(" + arguments.toString() + ")");
                return false;
            }
        });
    }
}
