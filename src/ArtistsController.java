/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ArtistsController implements Initializable {
 @FXML
    private GridPane artistsGridPane;
@FXML
private ScrollPane scrollPane;
   @FXML
    private VBox contentVBox;
     private void adjustScrollPaneHeight() {
        // Adjust the height of the ScrollPane to be less than the height of the main FXML
        double remainingHeight = scrollPane.getScene().getWindow().getHeight() - scrollPane.getBoundsInParent().getMaxY() - 130; // Adjust the value 50 as needed
        scrollPane.setPrefHeight(remainingHeight);

        // Update the VBox's prefHeight to match the ScrollPane's height
        contentVBox.setPrefHeight(remainingHeight);
    }
     private List<AudioFile> loadSongsForUnknownArtist(String directoryPath) throws IOException {
    List<AudioFile> songsForUnknownArtist = new ArrayList<>();

    Set<Path> audioFiles = getAllAudioFiles(directoryPath, Integer.MAX_VALUE);

    for (Path file : audioFiles) {
        String currentArtistName;
        try {
            AudioFile audioFile = AudioFileIO.read(file.toFile());
            Tag tag = audioFile.getTag();
            if (tag != null) {
                currentArtistName = tag.getFirst(FieldKey.ARTIST);
                if (currentArtistName == null || currentArtistName.isEmpty()) {
                    songsForUnknownArtist.add(audioFile);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    return songsForUnknownArtist;
}
    private Set<Path> getAllAudioFiles(String dir, int depth) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(dir), depth)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toString().endsWith(".mp3") || file.toString().endsWith(".wav"))
                    .collect(Collectors.toSet());
        }
    }

    private List<Artists> getAllArtists(String directoryPath) throws IOException {
        Set<String> uniqueArtistNames = new HashSet<>();
        List<Artists> artists = new ArrayList<>();

        Set<Path> audioFiles = getAllAudioFiles(directoryPath, Integer.MAX_VALUE); // Call the method to get all audio files

        // Add an "Unknown Artist" object to the list
        Artists unknownArtist = new Artists("Unknown Artist", "C:\\Users\\user\\Pictures\\photo.jpg");
        artists.add(unknownArtist);

        for (Path file : audioFiles) {
            String artistName;
            try {
                AudioFile audioFile = AudioFileIO.read(file.toFile());
                Tag tag = audioFile.getTag();
                if (tag != null) {
                    artistName = tag.getFirst(FieldKey.ARTIST);
                    // Check if the artist's name is empty or null
                    if (artistName == null || artistName.isEmpty()) {
                        artistName = "Unknown Artist";
                    }
                } else {
                    artistName = "Unknown Artist";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                artistName = "Unknown Artist";
            }
            if (!artistName.equals("Unknown Artist")) {
                uniqueArtistNames.add(artistName);
            }
        }

        for (String artistName : uniqueArtistNames) {
            String artistImagePath = directoryPath + File.separator + artistName + ".jpg";
            if (!new File(artistImagePath).exists()) {
                artistImagePath = "C:\\Users\\user\\Pictures\\photo.jpg"; // Set the default image path for all artists
            }
            artists.add(new Artists(artistName, artistImagePath));
        }

        return artists;
    }
    private List<AudioFile> loadSongsForArtist(String artistName, String directoryPath) throws IOException {
    List<AudioFile> songsForArtist = new ArrayList<>();

    Set<Path> audioFiles = getAllAudioFiles(directoryPath, Integer.MAX_VALUE);

    for (Path file : audioFiles) {
        String currentArtistName;
        try {
            AudioFile audioFile = AudioFileIO.read(file.toFile());
            Tag tag = audioFile.getTag();
            if (tag != null) {
                currentArtistName = tag.getFirst(FieldKey.ARTIST);
                if (artistName.equals(currentArtistName)) {
                    songsForArtist.add(audioFile);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    return songsForArtist;
}
private void populateGridPane(List<Artists> artists) {
    int row = 0;
    int col = 0;

    for (Artists artist : artists) {
        VBox artistBox = new VBox();
        artistBox.setAlignment(Pos.CENTER);
        artistBox.setSpacing(10);
        artistBox.setMinSize(200, 200);
         artistBox.setMaxSize(200, 200);
        Image artistImage;
        try {
            artistImage = new Image(new File(artist.getImagePath()).toURI().toString());
        } catch (Exception e) {
            artistImage = new Image(new File("C:\\Users\\user\\Pictures\\photo.jpg").toURI().toString());
        }

        ImageView artistImageView = new ImageView(artistImage);
        artistImageView.setFitHeight(100);
        artistImageView.setPreserveRatio(true);
        Label artistNameLabel = new Label(artist.getName());
        artistNameLabel.setWrapText(true);

        artistBox.getChildren().addAll(artistImageView, artistNameLabel);

        // Add a click event listener to the artistBox
        artistBox.setOnMouseClicked(event -> handleArtistClick(artist, event));
  
        artistsGridPane.add(artistBox, col, row);

        col++;
        if (col == 5) {
            col = 0;
            row++;
        }
    }
}

private void handleArtistClick(Artists artist, MouseEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Artistdetail.fxml"));
        Node artistDetails = loader.load();
        ArtistdetailController controller = loader.getController();

        String directoryPath = "C:\\Users\\user\\Music";
        List<AudioFile> songsForArtist;
        if (artist.getName().equals("Unknown Artist")) {
            songsForArtist = loadSongsForUnknownArtist(directoryPath);
        } else {
            songsForArtist = loadSongsForArtist(artist.getName(), directoryPath);
        }
        
        controller.setArtistdetail(songsForArtist);
        scrollPane.setContent(artistDetails);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
     @Override
    public void initialize(URL url, ResourceBundle rb) {
          Platform.runLater(() -> {
            adjustScrollPaneHeight();
        });
      String directoryPath = "C:\\Users\\user\\Music";
        try {
            List<Artists> artists = getAllArtists(directoryPath);
            populateGridPane(artists);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load artists.");
        }
    }
    }


