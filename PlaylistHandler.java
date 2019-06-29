import java.io.*;
import java.util.ArrayList;

public class PlaylistHandler {
    File file;
    BufferedReader br1;
    BufferedReader br2;
    String playlistAddress;
    ArrayList<Playlist> playlists;
    PrintWriter out;

    public PlaylistHandler(String path) throws IOException {
        file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        playlists = new ArrayList<>();
        br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        playlistAddress = br1.readLine();
        while (playlistAddress != null) {
            br2 = new BufferedReader(new InputStreamReader(new FileInputStream(playlistAddress)));
            new Playlist(playlistAddress, br2.readLine(), this,true);
            playlistAddress = br1.readLine();

        }

    }

    public void add(Playlist playlist) throws FileNotFoundException {
        playlists.add(playlist);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)), true);
        for (Playlist playlistInHandler : playlists
        ) {
            out.println(playlistInHandler.file.getAbsolutePath());
        }

    }

    public void remove(Playlist playlist) {
        playlists.remove(playlist);
    }
}
