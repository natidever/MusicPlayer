/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AlbumDetailsController implements Initializable {

   @FXML
    private TableColumn<Song, String> albumColumn;

    @FXML
    private TableColumn<Song, String> artistColumn;

    @FXML
    private TableView<Song> tblData;
    @FXML
private TableColumn<Song, String> durationColumn;

    @FXML
    private TableColumn<Song, String> titleColumn;
      private ObservableList<Song> songData;
       private MediaPlayer mediaPlayer;
    public void setSongData(List<File> albumFiles) {
    songData = FXCollections.observableArrayList();

    for (File file : albumFiles) {
        try {
            MP3File mp3file = (MP3File) AudioFileIO.read(file);
            Tag tag = mp3file.getTag();
            String artist = tag.getFirst(FieldKey.ARTIST);
            String album = tag.getFirst(FieldKey.ALBUM);
            String title = tag.getFirst(FieldKey.TITLE);
            

        // Calculate the duration of the song using MediaPlayer
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
            AtomicReference<String> formattedDuration = new AtomicReference<>(); // Add this line
        mediaPlayer.setOnReady(() -> {
          Duration duration = media.getDuration();
            int minutes = (int) duration.toMinutes();
            int seconds = (int) (duration.toSeconds() - minutes * 60);
            formattedDuration.set(String.format("%d:%02d", minutes, seconds)); // Update this line
            Song song = new Song(artist, album, title, file);
            song.setDuration(formattedDuration.get()); // Update this line
            songData.add(song);
        });
        mediaPlayer.setOnError(() -> {
            System.out.println("Error calculating duration");
        });
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.dispose();
        });
        } catch (Exception e) {
            System.out.println("Error reading song data: " + e);
        }
    }

    tblData.setItems(songData);
}
    @FXML
private void handlePlaySong() {
 Song selectedSong = tblData.getSelectionModel().getSelectedItem();
    if (selectedSong != null) {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop the currently playing song
        }
         String filePath = selectedSong.getFile().toURI().toString();
        Media media = new Media(filePath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> {
            System.out.println("Song has finished playing.");
            mediaPlayer.stop(); // Stop the media player
            tblData.getSelectionModel().clearSelection(); // Clear the song selection
        });
        mediaPlayer.play();
    }
}
//      private void stopCurrentSong() {
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//        }
//    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tblData.setOnMouseClicked(event -> {
    if (event.getClickCount() == 2) {
        System.out.println("intialized");
        handlePlaySong();
    }
});
    }    
    
}
