import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Library mainLibrary = new Library("H:\\Courses\\Advanced Programming\\Project\\Files", "MainLibrary");
        Library favoritePlaylist = new Library("H:\\Courses\\Advanced Programming\\Project\\Files", "FavoritePlaylist");
        Library sharedPlaylist = new Library("H:\\Courses\\Advanced Programming\\Project\\Files", "SharedPlaylist");
        PlaylistHandler playlistHandler = new PlaylistHandler("H:\\Courses\\Advanced Programming\\Project\\Files\\PlaylistHandler.txt");
        Network network = new Network("Shayan");
        sharedPlaylist.setNetwork(network);
        Song song1 = new Song("5");
        Song song2 = new Song("6");
        Song song3 = new Song("7");
        Song song4 = new Song("8");
        Song song5 = new Song("9");
        mainLibrary.add(song1);
        mainLibrary.add(song2);
        mainLibrary.add(song3);
        mainLibrary.add(song4);
        mainLibrary.add(song5);
        mainLibrary.remove(song1);
        mainLibrary.remove(mainLibrary.songs.get(2));
        Player player = new Player();
        Playlist playlist1 = new Playlist("H:\\Courses\\Advanced Programming\\Project\\Files\\Playlists", "hi", playlistHandler);
        playlist1.add(song1);
        playlist1.add(song2);
        playlist1.add(song3);
        playlist1.add(song4);
        playlistHandler.playlists.get(0).add(song1);
        playlistHandler.playlists.get(0).add(song2);
        playlistHandler.playlists.get(0).add(song3);
        playlistHandler.playlists.get(0).add(song4);


    }
}
