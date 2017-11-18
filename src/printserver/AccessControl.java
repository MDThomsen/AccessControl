package printserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

class AccessControl {
    static final String OPAUTH = "Operation authorized";
    private static final String OPAUTHFAIL = "Operation not authorized";
    private final File permissionsFile;
    private final File rolesFile;

    AccessControl() {
        String PERMISSIONSFILENAME = "RolePermissions.txt";
        permissionsFile = new File(PERMISSIONSFILENAME);

        String ROLESFILENAME = "Roles.txt";
        rolesFile = new File(ROLESFILENAME);
    }

    String hasRightToPerformOperation(String username, String operation) throws Exception {
        if(!Operations.contains(operation))
            throw new Exception(operation + " is not a defined operation.");

        Role userRole = getRoleForUser(username);

        //Second string in the line contains the permissions for the user.
        String[] permissions = findRowContaining(permissionsFile,userRole.name())[1].split(",");

        return Arrays.asList(permissions).contains(operation) ? OPAUTH : OPAUTHFAIL;
    }

    private Role getRoleForUser(String username) {
        return Role.valueOf(findRowContaining(rolesFile,username)[0]);
    }

    private String[] findRowContaining(File file, String phrase){
        String res = null;
        try {
            res = Files.lines(file.toPath()).filter(s -> s.contains(phrase)).findFirst().orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res.split("\\s+");
    }
}
