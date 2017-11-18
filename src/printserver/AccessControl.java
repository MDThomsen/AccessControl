package printserver;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class AccessControl {
    public static final String OPAUTH = "Operation authorized";
    public static final String OPAUTHFAIL = "Operation not authorized";
    private static File file;
    private static final String FILENAME = "AccessControlList.txt";

    AccessControl() {
        file = new File(FILENAME);
    }

    String hasRightToPerformOperation(String username, String operation) throws Exception {
        if(!Operations.contains(operation))
            throw new Exception(operation + " is not a defined operation.");

        String res = null;
        try {
            res = Files.lines(file.toPath()).filter(s -> s.contains(username)).findFirst().orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] lineContents = res.split("\\s+");

        //Second string in the line contains the permissions for the user.
        String[] permissions = lineContents[1].split(",");

        return Arrays.asList(permissions).contains(operation) ? OPAUTH : OPAUTHFAIL;
    }
}
