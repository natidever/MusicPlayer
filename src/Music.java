import java.io.File;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Music {
    private String artist;
    private String album;
    private String title;
    private StringProperty duration;
    private File file;

    public Music(String artist, String album, String title, int duration,File file) {
        this.artist = artist;
        this.album = album;
        this.title = title;
        this.duration = new SimpleStringProperty(formatDuration(duration));
         this.file=file;
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

    public String getDuration() {
        return duration.get();
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    private String formatDuration(int durationInSeconds) {
        int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
