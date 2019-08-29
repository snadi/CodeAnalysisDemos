/**
 * Example from on https://www.programcreek.com/2011/01/a-complete-standalone-example-of-astparser/
 */

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.*;


public class VarDeclCounter {
    public static void main(String args[]){
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        String fileContent = Utils.getFile("test.java");

        parser.setSource(fileContent.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);



        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

        cu.accept(new ASTVisitor() {

            Set names = new HashSet();

            public boolean visit(VariableDeclarationFragment node) {
                SimpleName name = node.getName();
                this.names.add(name.getIdentifier());
                System.out.println("Declaration of '"+name+"' at line"+cu.getLineNumber(name.getStartPosition()));
                return false; // do not continue to avoid usage info
            }

        });
    }
}
