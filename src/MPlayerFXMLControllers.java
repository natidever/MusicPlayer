
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class MPlayerFXMLController {
        @FXML
    private VBox existingVBox;
          @FXML
    Label album;
public void displayAlbum(MouseEvent event) throws IOException {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Albums.fxml"));
        ScrollPane newScrollPane = loader.load();

        // Set fitToHeight and fitToWidth to true

        // Adjust the height of the ScrollPane to be less than the height of the main FXML
        double remainingHeight = existingVBox.getScene().getWindow().getHeight() - existingVBox.getBoundsInParent().getMaxY() - 50; // Adjust the value 50 as needed
        newScrollPane.setPrefHeight(remainingHeight);

        // Replace the existing VBox with the new ScrollPane
        existingVBox.getChildren().setAll(newScrollPane);
    } catch (IOException e) {
        e.printStackTrace();
    }
}   
         public void displayArtist(MouseEvent event) throws IOException{
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Artists.fxml"));
        ScrollPane newScrollPane = loader.load();

        // Adjust the height of the ScrollPane to be less than the height of the main FXML
        double remainingHeight = existingVBox.getScene().getWindow().getHeight() - existingVBox.getBoundsInParent().getMaxY() - 50; // Adjust the value 50 as needed
        newScrollPane.setPrefHeight(remainingHeight);

        // Replace the existing VBox with the new ScrollPane
        existingVBox.getChildren().setAll(newScrollPane);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    }   
    