package printserver;

public enum Operations {
    print,queue,topQueue,start,stop,restart,status,readConfig,setConfig;

    public static boolean contains(String operation) {
        for( Operations ops : Operations.values()) {
            if(ops.name().equals(operation))
                return true;
        }
        return false;
    }
}
