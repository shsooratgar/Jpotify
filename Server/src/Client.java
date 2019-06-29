import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Client implements Runnable {
    private Server server;
    private String name;
    private Socket clientSocket;
    private Status status;
    private PrintWriter out;
    private BufferedReader br;
    private HashMap<Client, String> friends;
    private String friendIP;
    private HashMap<String, Song> sharedPlaylist;
    private String songName;
    private ObjectInputStream oos;
    private SS ss;

    DataInputStream in;
    DataOutputStream out1;
    File file;
    int count;
    byte[] buffer;


    public Client(Socket clientSocket, Server server) throws IOException {
        sharedPlaylist = new HashMap<>();
        this.clientSocket = clientSocket;
        status = Status.Online;
        friends = new HashMap<>();
        ss = new SS(clientSocket);

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    public void run() {
        boolean isRunning = true;
        String incomingMesssege = null;

        try {
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (isRunning) {


            try {
                incomingMesssege = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(incomingMesssege);

            /*switch (incomingMesssege) {
                case "Add Friend":
                    try {
                        System.out.println("stage 1");
                        friendIP = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    addFriend(friendIP);
                    System.out.println("hello");
                    System.out.println(friends);
                case "b":
                    buffer = new byte[8192];
                    try {
                        in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out1 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("C:\\Users\\GITEX GROUP\\Desktop\\gg.mp3"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while (true) {
                        try {
                            if (!((count = in.read(buffer)) > 0)) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        out.write(String.valueOf(buffer), 0, count);
                    }
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                case "c":
                    try {
                        songName = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sharedPlaylist.remove(name);
                case "d":
                    try {
                        songName = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    listeningTo(songName);
                case "e":
                    stoppedListening();



        }*/
        }
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

    public void sendStatus(Status myStatus) {
        friends.forEach((friend, nickName) -> {
            if (friend.getStatus() == Status.Online) {
                try {
                    out = new PrintWriter(new OutputStreamWriter(friend.getClientSocket().getOutputStream()), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.println(String.format("%s Is Now %s", nickName, myStatus));
            }
        });
    }

    private void addFriend(String IP) {
        System.out.println(IP + "ssssssasfsdsd");
        System.out.println(server.getClients());
        if (server.getClients().containsKey(server.getClients().get(IP))) {
            friends.put(server.getClients().get(IP), server.getClients().get(IP).getName());
        }
        System.out.println("gg");
    }

    public void addToSharedPlaylist(Song song) {
        sharedPlaylist.put(name, song);
    }

    public void removeFromSharedPlaylist(Song song) {
        sharedPlaylist.remove(song);
    }

    public void listeningTo(String songName) {
        friends.forEach((friend, nickname) -> {
            if (friend.getStatus() == Status.Online) {
                try {
                    out = new PrintWriter(new OutputStreamWriter(friend.getClientSocket().getOutputStream()), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.println(nickname + " Is Listening To " + songName);
            }
        });
    }

    public void stoppedListening() {
        friends.forEach((friend, nickname) -> {
            if (friend.getStatus() == Status.Online) {
                try {
                    out = new PrintWriter(new OutputStreamWriter(friend.getClientSocket().getOutputStream()), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.println("Stop");
                ;
            }
        });
    }

    public void download() {

    }
}
