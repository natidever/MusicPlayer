import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

public class AlbumsController implements Initializable {
    @FXML
    GridPane gridPaneController;
    @FXML
private ScrollPane scrollPane;
      @FXML
    private VBox contentVBox;
     private void adjustScrollPaneHeight() {
    if (scrollPane == null || contentVBox == null) {
        return;
    }

    // Adjust the height of the ScrollPane to be less than the height of the main FXML
    double remainingHeight = scrollPane.getScene().getWindow().getHeight() - scrollPane.getBoundsInParent().getMaxY() - 130; // Adjust the value 50 as needed
    scrollPane.setPrefHeight(remainingHeight);

    // Update the VBox's prefHeight to match the ScrollPane's height
    contentVBox.setPrefHeight(remainingHeight);
}
private static final String DEFAULT_ALBUM_TITLE = "Unkown Album";
private static final String DEFAULT_ALBUM_ART_PATH = "C:\\Users\\user\\Pictures\\bb.jpg";
    private void displayAlbums() {
        Map<String, List<File>> albumsByTitle = new HashMap<>();

        try {
            Files.walk(Paths.get("C:\\Users\\user\\Music"))
                .filter(Files::isRegularFile)
                .filter(file -> file.toString().endsWith(".mp3"))
                .forEach(file -> {
                    try {
                        MP3File mp3File = (MP3File) AudioFileIO.read(file.toFile());
                        Tag tag = mp3File.getTag();
                        String albumTitle = tag.getFirst(FieldKey.ALBUM);
                        if (!albumsByTitle.containsKey(albumTitle)) {
                            albumsByTitle.put(albumTitle, new ArrayList<>());
                        }
                        albumsByTitle.get(albumTitle).add(file.toFile());
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });
        } catch (IOException e) {
            System.out.println("Error reading files: " + e);
        }

        int column = 0;
        int row = 0;
        for (String albumTitle : albumsByTitle.keySet()) {
            try {
                List<File> albumFiles = albumsByTitle.get(albumTitle);
                File albumFile = albumFiles.get(0);
                MP3File mp3File = (MP3File) AudioFileIO.read(albumFile);
                Tag tag = mp3File.getTag();
                Image albumArt;
           if (albumTitle == null || albumTitle.isEmpty()) {
                albumTitle = DEFAULT_ALBUM_TITLE;
            }
                byte[] albumImageData=null;
               if (tag.getArtworkList() != null && !tag.getArtworkList().isEmpty()) {
                albumImageData = tag.getArtworkList().get(0).getBinaryData();
            }
              if (albumImageData != null && !albumTitle.equals(DEFAULT_ALBUM_TITLE)) {
                albumArt = new Image(new ByteArrayInputStream(albumImageData));
            } else {
                // Set a default image if there is no album art or if the album title is the default title
                albumArt = new Image(new FileInputStream(DEFAULT_ALBUM_ART_PATH));
              }
                ImageView albumArtView = new ImageView(albumArt);
                albumArtView.setFitHeight(100);
                albumArtView.setPreserveRatio(true);
                Label albumTitleLabel = new Label(albumTitle);
                VBox albumBox = new VBox(albumArtView, albumTitleLabel);
                albumBox.setAlignment(Pos.CENTER);
                albumBox.setSpacing(10);
                albumBox.setMinSize(180, 180);
                 albumBox.setMaxSize(180, 180);
             albumBox.setOnMouseClicked(event -> handleAlbumClick(event, albumFiles));
                gridPaneController.add(albumBox, column, row);
                column++;
                if (column == 6) {
                    column = 0;
                    row++;
                }
            } catch (Exception ex) {
                System.out.println("Error processing album: " + ex);
            }
        }
    }
    private void handleAlbumClick(MouseEvent event, List<File> albumFiles) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AlbumDetails.fxml"));
        Node albumDetails = loader.load();
        AlbumDetailsController controller = loader.getController();
        controller.setSongData(albumFiles);
        scrollPane.setContent(albumDetails);
    } catch (IOException e) {
        System.out.println("Error loading album details: " + e);
    }
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            Platform.runLater(() -> {
            adjustScrollPaneHeight();
        });
        System.out.println("intialized");
        gridPaneController.setStyle("-fx-padding: 0 0 100 0; -fx-background-color: gray;");
        displayAlbums();
        
    }}
