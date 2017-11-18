package printserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrinterServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(5099);
            registry.rebind("printserver", new PrintServant());
            //initializePasswordTable();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /*private static void initializePasswordTable() throws NoSuchAlgorithmException {
        Authentication sph = new Authentication();
        sph.storeLoginInfo("Alice","m23948Jhl8");
        sph.storeLoginInfo("Bob","imthebestjanitorintheworld");
        sph.storeLoginInfo("Cecilia","o239avwe2");
        sph.storeLoginInfo("David","N2e43wI6");
        sph.storeLoginInfo("Erica","23ojpp8ewfs");
        sph.storeLoginInfo("Fred","98Jwjjeh288q");
        sph.storeLoginInfo("George","jwoifja238923J");
    }*/
}
