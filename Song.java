import com.mpatric.mp3agic.Mp3File;

import java.io.File;

public class Song extends Mp3File {
    private String name;
    private String address;
    private File songFile;
    public Song(String address){
        super();
        this.address = address;
        songFile = new File(address);
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return address;
    }

    public File getSongFile() {
        return songFile;
    }

    public String getName() {
        return name;
    }
}
