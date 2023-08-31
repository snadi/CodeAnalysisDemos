import java.io.File;
import java.util.Set;

import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Mapping;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.gen.javaparser.JavaParserGenerator;
import com.github.gumtreediff.client.Run;
import com.github.gumtreediff.actions.EditScript;
import com.github.gumtreediff.actions.EditScriptGenerator;
import com.github.gumtreediff.actions.SimplifiedChawatheScriptGenerator;
import com.github.gumtreediff.actions.model.Action;


public class ProgramDifferencer {

    public static void main(String args[]){
        String srcFile = "examples/Version1.java";
        String dstFile = "examples/Version2.java";

        try{
            //Run.initClients();
            Run.initGenerators(); // registers the available parsers
            Tree src = new JavaParserGenerator().generateFrom().file(srcFile).getRoot(); // retrieves and applies the default parser for the file 
            Tree dst = new JavaParserGenerator().generateFrom().file(dstFile).getRoot(); // retrieves and applies the default parser for the file 
            Matcher defaultMatcher = Matchers.getInstance().getMatcher(); // retrieves the default matcher
            MappingStore mappings = defaultMatcher.match(src, dst); // computes the mappings between the trees

            EditScriptGenerator editScriptGenerator = new SimplifiedChawatheScriptGenerator(); // instantiates the simplified Chawathe script generator
            EditScript actions = editScriptGenerator.computeActions(mappings); // computes the edit script

            for (Action action : actions) {
                System.out.println(action);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}