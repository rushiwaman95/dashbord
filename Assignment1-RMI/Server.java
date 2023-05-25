import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements Service {
    private ArrayList<String> messages = new ArrayList<>();

    public Server() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
    }

    public void receiveMessage(String message) throws RemoteException {
        System.out.println("Received message: " + message);
        messages.add(message);
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            Registry registry = LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost/Service", server);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.out.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

interface Service extends java.rmi.Remote {
    void receiveMessage(String message) throws RemoteException;
}


// **********Assignment 1**********
// OS Required : Ubuntu
// Installations :- java jdk
// link: https://www.oracle.com/in/java/technologies/downloads/#jdk20-windows
// Steps
// 1)open cmd prompt 
// 2) `javac Server.java`
// 3)`java Server`
// 4)Open another cmd prompt 
// 5)`javac Client.java`
// 6)`java Client`

//for ass 6 "Set-ExecutionPolicy Unrestricted" command
