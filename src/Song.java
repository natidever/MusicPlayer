
import java.io.File;
import java.time.Duration;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class Song {
      private String artist;
    private String album;
    private String title;
    private File file;
   private Duration duration;
    public Song(String artist, String album, String title,File file) {
        this.artist = artist;
        this.album = album;
        this.title = title;
        this.file=file;
        this.duration=duration;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getTitle() {
        return title;
    } 
     public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    private final StringProperty durationProperty = new SimpleStringProperty();
        public String getDuration() {
        return durationProperty.get();
    }
    

    public void setDuration(String duration) {
        durationProperty.set(duration);
    }

    public StringProperty durationProperty() {
        return durationProperty;
    }
}
