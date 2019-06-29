import java.io.*;

class Playlist extends Library {

    private PlaylistHandler playlistHandler;
    PrintWriter out;
    public Playlist(String path, String name, PlaylistHandler playlistHandler) throws IOException {
        super(path,name);
        this.playlistHandler = playlistHandler;
        playlistHandler.add(this);

    }

    public Playlist(String path, String name, PlaylistHandler playlistHandler,boolean nothing) throws IOException {
        super(path,name,true);
        this.playlistHandler = playlistHandler;
        playlistHandler.add(this);

    }


    public void changeTrackNo(Song song, int track) {
        songs.remove(song);
        songs.add(track, song);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void delete() {
        file.delete();
        playlistHandler.remove(this);
    }
}