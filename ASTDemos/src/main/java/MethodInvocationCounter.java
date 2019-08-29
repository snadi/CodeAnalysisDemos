import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by snadi on 2017-09-11.
 * Based on https://www.programcreek.com/2011/01/a-complete-standalone-example-of-astparser/
 */
public class MethodInvocationCounter {
    public static void main(String args[]) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        String fileContent = Utils.getFile("test-inv.java");


        parser.setSource(fileContent.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        List<AbstractTypeDeclaration> types = cu.types();

        HashMap<String,Integer> methodInvCountMap = new HashMap<String, Integer>();

        for(AbstractTypeDeclaration type : types) {
            if(type.getNodeType() == ASTNode.TYPE_DECLARATION) {
                // Class def found
                List<BodyDeclaration> bodies = type.bodyDeclarations();
                for(BodyDeclaration body : bodies) {
                    if(body.getNodeType() == ASTNode.METHOD_DECLARATION) {
                        MethodDeclaration methodDecl = (MethodDeclaration) body;

                        Block methodBody = methodDecl.getBody();
                        for (Object bodyStmt : methodBody.statements()){
                            Statement statement = (Statement) bodyStmt;

                            if(statement.getNodeType() == ASTNode.EXPRESSION_STATEMENT ) {
                                ExpressionStatement exprStmt = ((ExpressionStatement) statement);
                                Expression expression = exprStmt.getExpression();

                                if (expression.getNodeType() == ASTNode.METHOD_INVOCATION) {
                                    MethodInvocation methodInvocation = (MethodInvocation) expression;

                                    String fullyQualifiedMethod = methodInvocation.getName().getFullyQualifiedName();

                                    if (methodInvCountMap.containsKey(fullyQualifiedMethod)) {
                                        methodInvCountMap.put(fullyQualifiedMethod, methodInvCountMap.get(fullyQualifiedMethod) + 1);
                                    } else {
                                        methodInvCountMap.put(fullyQualifiedMethod, 1);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        for(String method : methodInvCountMap.keySet()){
            System.out.println(method + " is called " + methodInvCountMap.get(method) + " times");
        }
    }
}
