import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Library mainLibrary = new Library("H:\\Courses\\Advanced Programming\\Project\\Files", "MainLibrary");
        Library favoritePlaylist = new Library("H:\\Courses\\Advanced Programming\\Project\\Files", "FavoritePlaylist");
        Library sharedPlaylist = new Library("H:\\Courses\\Advanced Programming\\Project\\Files", "SharedPlaylist");
        PlaylistHandler playlistHandler = new PlaylistHandler("H:\\Courses\\Advanced Programming\\Project\\Files\\PlaylistHandler.txt");



    }
}
