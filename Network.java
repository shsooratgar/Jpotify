import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Network {
    private String name;
    private static final int PORT = 2000;
    private Socket client;
    private PrintWriter out;
    private BufferedReader br;
    private Status status;
    private ArrayList<String> IPLists;
    private SS ss;


    public Network(String name) throws Exception {
        this.name = name;
        IPLists = new ArrayList<>();
        client = new Socket("127.0.0.1", PORT);
        ss = new SS(client);
        status = Status.Online;

    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum Status {
        Online, Offline;
    }

    public void sendStatus() throws IOException {
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        out.println(status);

    }

    public void addFriend(String IP) throws IOException {
        IPLists.add(IP);
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        out.println("Add Friend1");
        out.println("Add Friend2");
        out.println("Add Friend3");
        out.println("Add Friend4");
        out.println("Add Friend5");


        out.println(IP);


        while (true) {
        }
    }

    public void addIP(String IP) throws IOException {
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        out.println(IP);

    }

    public void addToSharedPaltlist(File file) throws IOException {
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        out.println("Add To Shared Playlist");
        ss.sendFile(file);

    }
    public void removeFromSharedPaltlist(String name) throws IOException {
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        out.println("Remove From Playlist");
        out.println(name);

    }

}

