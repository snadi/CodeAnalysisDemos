import ch.uzh.ifi.seal.changedistiller.ChangeDistiller;
import ch.uzh.ifi.seal.changedistiller.distilling.FileDistiller;
import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeChange;

import java.io.File;
import java.util.List;

/**
 *
 * Example from https://bitbucket.org/sealuzh/tools-changedistiller/wiki/Home
 * Used under the Apache 2.0 License
 */
public class ChangeDistillerDemo {

    public static void main(String args[]){
        File left = new File("examples/Test.java_v1");
        File right = new File("examples/Test.java_v2");

        FileDistiller distiller = ChangeDistiller.createFileDistiller(ChangeDistiller.Language.JAVA);
        try {
            distiller.extractClassifiedSourceCodeChanges(left, right);
        } catch(Exception e) {
    /* An exception most likely indicates a bug in ChangeDistiller. Please file a
       bug report at https://bitbucket.org/sealuzh/tools-changedistiller/issues and
       attach the full stack trace along with the two files that you tried to distill. */
            System.err.println("Warning: error while change distilling. " + e.getMessage());
        }

        List<SourceCodeChange> changes = distiller.getSourceCodeChanges();
        System.out.println("size: " + changes.size());
        if(changes != null) {
            for(SourceCodeChange change : changes) {
                // see Javadocs for more information
                System.out.println(change.getChangeType() + " " + change.getChangedEntity());
            }
        }
    }
}
