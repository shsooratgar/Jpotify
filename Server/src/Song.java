import com.mpatric.mp3agic.Mp3File;
public class Song extends Mp3File {
    private String name;
    private String address;
    public Song(String address){
        super();
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
