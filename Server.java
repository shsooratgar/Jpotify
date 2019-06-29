import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private static final int PORT = 2000;
    private ServerSocket serverSocket;
    private PrintWriter out;
    private BufferedReader br;
    private Socket clientSocket;
    private HashMap<InetAddress, Client> clients;
    private ObjectInputStream ios;
    private Client aux;

    public Server() throws Exception {
        clients = new HashMap<>();
        serverSocket = new ServerSocket(PORT);
        while (true) {
            clientSocket = serverSocket.accept();
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            if (clients.containsKey(clientSocket.getInetAddress())) {
                clients.get(clientSocket.getInetAddress()).setStatus(Client.Status.Online);
                clients.get(clientSocket.getInetAddress()).sendStatus(Client.Status.Online);
                clients.get(clientSocket.getInetAddress()).setName(br.readLine());
                new Thread(clients.get(clientSocket.getInetAddress())).start();
            } else {
                aux = new Client(clientSocket,this);
                clients.put(clientSocket.getInetAddress(), aux);
                System.out.println(br.readLine());
                aux.setName(br.readLine());
                new Thread(aux).start();
            }
        }
    }

    public HashMap<InetAddress, Client> getClients() {
        return clients;
    }

}
//ios = new ObjectInputStream(client.getInputStream());
//clients.put(client, (ArrayList<Integer>) ios.readObject());

