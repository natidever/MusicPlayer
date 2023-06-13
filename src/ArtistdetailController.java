/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ArtistdetailController implements Initializable {
     @FXML
    private TableView<Music> musicTableView;
    @FXML
    private TableColumn<Music, String> artistColumn;
    @FXML
    private TableColumn<Music, String> albumColumn;
    @FXML
    private TableColumn<Music, String> titleColumn;
    @FXML
    private TableColumn<Music, String> durationColumn;
    @FXML
        private VBox vbox;

    public MediaView mediaView;
    private ImageView albumArtImageView;
    private MediaPlayer mediaPlayer;
     private Music currentSong;
public void setArtistdetail(List<AudioFile> audioFiles) {
    // Clear the TableView
    musicTableView.getItems().clear();

    // Set up cell value factories for each column
    artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
    albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty());

    // Use the audio files to populate the UI
    for (AudioFile audioFile : audioFiles) {
          Tag tag = audioFile.getTag();
            AudioHeader header = audioFile.getAudioHeader();
            File file = audioFile.getFile();

        if (tag != null && header != null) {
            String artist = tag.getFirst(FieldKey.ARTIST);
            String album = tag.getFirst(FieldKey.ALBUM);
            String title = tag.getFirst(FieldKey.TITLE);
            int duration = header.getTrackLength();

            Music songDetails = new Music(artist, album, title, duration,file);
            musicTableView.getItems().add(songDetails);
        }
    }
}
 public void setOnDoubleClick(TableView<Music> tableView, Music selectedSong) {
        mediaView = new MediaView();
        albumArtImageView = new ImageView();
        vbox.getChildren().addAll(mediaView, albumArtImageView);

        musicTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    Music newValue = musicTableView.getSelectionModel().getSelectedItem();
                    handlePlaySong(newValue);
                }
            }
        });
    }

    private void handlePlaySong(Music selectedSong) {
        if (selectedSong != null) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }

            currentSong = selectedSong;
            String filePath = selectedSong.getFile().toURI().toString();
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.setOnReady(() -> {
                mediaPlayer.play();
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                System.out.println("Song has finished playing.");
                mediaPlayer.stop();
                mediaPlayer.dispose();
                musicTableView.getSelectionModel().clearSelection();
                currentSong = null;
            });
        }
    }
    public void stopPlaying() {
    if (mediaPlayer != null) {
        mediaPlayer.stop();
    }
}
     @Override
     public void initialize(URL url, ResourceBundle rb){
  setOnDoubleClick(musicTableView, null);
     }
}

    
    
