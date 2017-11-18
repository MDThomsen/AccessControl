package printserver;

public enum Role {
    Administrator,Technician,PowerUser,User;

    public static boolean contains(String role) {
        for( Operations ops : Operations.values()) {
            if(ops.name().equals(role))
                return true;
        }
        return false;
    }
}
