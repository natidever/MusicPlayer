
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class Artists {
     private String name;
    private String imagePath;
   private List<String> albums;
    public Artists(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
        this.albums = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    
    public List<String> getAlbums() {
        return albums;
    }

    public void addAlbum(String album) {
        if (!albums.contains(album)) {
            albums.add(album);
        }
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
