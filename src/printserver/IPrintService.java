package printserver;

import java.rmi.*;

public interface IPrintService extends Remote {

    String login(String username, String password) throws RemoteException;
    String logout(String username) throws RemoteException;
    String print(String filename, String printer, String token) throws RemoteException;
    String queue(String token) throws RemoteException;
    String topQueue(int job, String token) throws RemoteException;
    String start(String token) throws RemoteException;
    String stop(String token) throws RemoteException;
    String restart(String token) throws RemoteException;
    String status(String token) throws RemoteException;
    String readConfig(String parameter, String token) throws RemoteException;
    String setConfig(String parameter, String value, String token) throws RemoteException;
}
