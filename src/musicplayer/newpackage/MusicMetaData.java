/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package musicplayer.newpackage;


/**
 *
 * @author Natnael
 */
public class MusicMetaData {
    private final String title;
    private final String artist;
    private final String duration;
    private final String album;
     private String path;
      private int id;

    public void setPath(String path) {
        this.path = path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }


   //Constructor
    public MusicMetaData(String title,String artist,String duration,String album){
        this.title=title;
        this.artist=artist;
        this.duration=duration;
        this.album=album;
        
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }
    
        public String getAlbum() {
        return album;
    }
    
    
    
}
