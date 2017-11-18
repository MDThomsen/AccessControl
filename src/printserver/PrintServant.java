package printserver;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PrintServant extends UnicastRemoteObject implements IPrintService {
    private static final String NO_AUTH = "Not authenticated.";

    private Authentication authenticator;
    private HashMap<String,String> acceptedTokens;

    private AccessControl accessControl;

    PrintServant() throws RemoteException {
        super();
        acceptedTokens = new HashMap<>();
        accessControl = new AccessControl();

        try {
            authenticator = new Authentication();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        try {
            String token = authenticator.login(username, password);
            if (token != null){
                acceptedTokens.put(username,token);
                return token;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String logout(String username) throws RemoteException {
        if(acceptedTokens.containsKey(username)) {
            acceptedTokens.remove(username);
            return username + " was logged out.";
        } else {
            return "Can't log someone out, who isn't even logged in.";
        }

    }

    @Override
    public String print(String filename, String printer, String token) throws RemoteException {
        try {
            if (acceptedTokens.containsValue(token) && (accessControl.hasRightToPerformOperation(getUsernameForToken(token),Operations.print.name()).equals(AccessControl.OPAUTH)))
                return "Printing " + filename + " at printer: " + printer;
            else
                return NO_AUTH;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String queue(String token) throws RemoteException  {
        try {
            if (acceptedTokens.containsValue(token) && (accessControl.hasRightToPerformOperation(getUsernameForToken(token),Operations.queue.name()).equals(AccessControl.OPAUTH)))
                return "This is a queue";
            else
                return NO_AUTH;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String topQueue(int job, String token) throws RemoteException  {
        try {
            if (acceptedTokens.containsValue(token) && (accessControl.hasRightToPerformOperation(getUsernameForToken(token),Operations.topQueue.name()).equals(AccessControl.OPAUTH)))
                return "Job " + job + " moved to top of queue.";
            else
                return NO_AUTH;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String start(String token) throws RemoteException  {
        try {
            if (acceptedTokens.containsValue(token) && (accessControl.hasRightToPerformOperation(getUsernameForToken(token),Operations.start.name()).equals(AccessControl.OPAUTH)))
                return "Starting server";
            else
                return NO_AUTH;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String stop(String token) throws RemoteException  {
        try {
            if (acceptedTokens.containsValue(token) && (accessControl.hasRightToPerformOperation(getUsernameForToken(token),Operations.stop.name()).equals(AccessControl.OPAUTH)))
                return "Stopping Server";
            else
                return NO_AUTH;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String restart(String token) throws RemoteException  {
        try {
            if (acceptedTokens.containsValue(token) && (accessControl.hasRightToPerformOperation(getUsernameForToken(token),Operations.restart.name()).equals(AccessControl.OPAUTH)))
                return "The server is restarting.";
            else
                return NO_AUTH;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String status(String token) throws RemoteException  {
        try {
            if (acceptedTokens.containsValue(token) && (accessControl.hasRightToPerformOperation(getUsernameForToken(token),Operations.status.name()).equals(AccessControl.OPAUTH)))
                return "This is a status.";
            else
                return NO_AUTH;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String readConfig(String parameter, String token) throws RemoteException  {
        try {
            if (acceptedTokens.containsValue(token) && (accessControl.hasRightToPerformOperation(getUsernameForToken(token),Operations.readConfig.name()).equals(AccessControl.OPAUTH)))
                return "Config for: " + parameter;
            else
                return NO_AUTH;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String setConfig(String parameter, String value, String token) throws RemoteException  {
        try {
            if (acceptedTokens.containsValue(token) && (accessControl.hasRightToPerformOperation(getUsernameForToken(token),Operations.setConfig.name()).equals(AccessControl.OPAUTH)))
                return parameter + " set to " + value;
            else
                return NO_AUTH;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String getUsernameForToken(String token) {
        for (Object o : acceptedTokens.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            if (pair.getValue().equals(token))
                return (String) pair.getKey();
        }
        return null;
    }
}
