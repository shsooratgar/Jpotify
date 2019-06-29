import java.io.*;
import java.util.ArrayList;

public class Library {
    File file;
    ArrayList<Song> songs;
    private BufferedReader br;
    private PrintWriter out;
    String name;
    //FileWriter fw;


    public Library(String path, String name) throws IOException {

        songs = new ArrayList<>();
        file = new File(path+"\\"+name+".txt");
        this.name = name;
        if (!file.exists()) {
            file.createNewFile();
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)), true);
            out.println(name);

        } else {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String songAddress = br.readLine();
            songAddress = br.readLine();
            while (songAddress != null) {
                songs.add(new Song(songAddress));
                songAddress = br.readLine();
            }

        }
    }

    public Library(String path, String name,boolean nothing) throws IOException {

        songs = new ArrayList<>();
        file = new File(path);
        this.name = name;
        if (!file.exists()) {
            file.createNewFile();
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)), true);
            out.println(name);

        } else {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String songAddress = br.readLine();
            songAddress = br.readLine();
            while (songAddress != null) {
                songs.add(new Song(songAddress));
                songAddress = br.readLine();
            }

        }
    }

    public void add(Song song) throws IOException {
        //if(!songs.contains(song)) {
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)), true);
        songs.add(song);
        out.println(name);
        for (Song songInArray : songs
        ) {
            out.println(songInArray);

        }



    }

    public void remove(Song song) throws IOException {
        // fw = new FileWriter(file, false);
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)), true);
        songs.remove(song);
        out.println(name);
        for (Song songInArray : songs
        ) {
            out.println(songInArray);

        }




    }



}

