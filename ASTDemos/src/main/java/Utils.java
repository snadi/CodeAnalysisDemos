import org.apache.commons.io.IOUtils;

import java.io.InputStream;

/**
 * Created by snadi on 2019-08-29.
 */
public class Utils {

    /*
    Author of this method: Thomas Decaux
    Source: https://stackoverflow.com/questions/6192661/how-to-reference-a-resource-file-correctly-for-jar-and-debugging
    */
    static public String getFile(String fileName)
    {
        //Get file from resources folder
        ClassLoader classLoader = (new Utils()).getClass().getClassLoader();

        InputStream stream = classLoader.getResourceAsStream(fileName);

        try
        {
            if (stream == null)
            {
                throw new Exception("Cannot find file " + fileName);
            }

            return IOUtils.toString(stream);
        }
        catch (Exception e) {
            e.printStackTrace();

            System.exit(1);
        }

        return null;
    }


}
