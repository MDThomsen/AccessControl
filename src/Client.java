
import printserver.IPrintService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client{

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        IPrintService service = (IPrintService) Naming.lookup("rmi://localhost:5099/printserver");

        String token = service.login("Cecilia","o239avwe2");

        System.out.println(token);
        System.out.println(service.start(token));
        System.out.println(service.print("IkeaCatelogue.pdf","P1",token));
        System.out.println(service.queue(token));
        System.out.println(service.topQueue(5,token));
        System.out.println(service.readConfig("MAX_CAPACITY",token));
        System.out.println(service.setConfig("MAX_CAPACITY","123098180",token));
        System.out.println(service.status(token));
        System.out.println(service.restart(token));
        System.out.println(service.stop(token));
        System.out.println(service.logout("Cecilia"));
    }
}
