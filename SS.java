import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SS implements Serializable {
    private Socket client;
    private ObjectOutputStream out;
    private int count;
    private DataInputStream in;
    private DataOutputStream out1;



    public SS(Socket client) {
        this.client = client;
    }

    public void sendObj(Object obj) throws IOException {
        out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(obj);
        out.close();
    }
    public void sendFile(File file) throws IOException {
        byte[] buffer = new byte[8192];
        in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        out1 = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
        while ((count = in.read(buffer)) > 0)
        {
            out.write(buffer, 0, count);
        }
        out.close();
        in.close();
    }
}
