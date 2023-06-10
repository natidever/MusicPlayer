/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package musicplayer.newpackage;
import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

  
public class PlayList_dialogController implements Initializable {
    
    //Importind components from the FXML
       @FXML
private TextField name_playlist;

 
       
       private Popup popUp;
    void setPopUp(Popup popUp){    this.popUp=popUp;    }
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
  
 
    }    

//    void setPrimaryStage(Stage primaryStage) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    
    
    
    
    @FXML
    public void cancelbutton(ActionEvent event){     popUp.hide();  }
    
    
                 @FXML
  private VBox playlist_container;
           
                 
                 
      public void setVBox(VBox playlist_container){
           this.playlist_container=playlist_container;
      } 
      
      
      
      
      
      public VBox getVBox(){
          return playlist_container;
      }
      
      
      
       
     @FXML
    void createPlaylist(ActionEvent event){
        
         String  playlist_name_text=name_playlist.getText();
         int playlistId = PlaylistDatabase.addPlaylist(playlist_name_text);
         
    if (playlistId != -1) {
        addPlaylistToContainer(playlist_name_text, playlistId);
    }
        
        
        
        
        
//   String  playlist_name_text=name_playlist.getText();
//      Label playlist_name_label=new Label(playlist_name_text);
//     ImageView playlist_image=new ImageView(new Image(getClass().getResourceAsStream("/resource/playlist.png")));
       
//           playlist_image.setFitHeight(25);
//                  playlist_image.setFitWidth(30);
//      HBox playlist_hbox=new HBox(playlist_image,playlist_name_label);    
//     playlist_hbox.setSpacing(1);
//         playlist_container.getChildren().add(playlist_hbox);
//        
//               
//         popUp.hide();   



//    String playlist_name_text = name_playlist.getText();
//    addPlaylistToContainer(playlist_name_text);
//
//    List<String> playlistNames = loadPlaylistNames();
//    playlistNames.add(playlist_name_text);
//    savePlaylistNames(playlistNames);
//
//    popUp.hide();
//
//
//
//}
//    
//    private void savePlaylistNames(List<String> playlistNames) {
//    try {
//        Connection connection = DriverManager.getConnection("jdbc:sqlite:playlist.db");
//        PlaylistDatabase playlistDAO = new PlaylistDatabase(connection);
//
//        for (String playlistName : playlistNames) {
//            Playlist_Data playlist = new Playlist_Data(playlistName, null, null);
//            playlistDAO.addPlaylist(playlist);
//        }
//
//        connection.close();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }



}
  private MPlayerFXMLController mPlayerController;

  public void setMPlayerController(MPlayerFXMLController mPlayerController) {
    this.mPlayerController = mPlayerController;
}

  
  
  
private void playlistclicked(Integer playlistId, MPlayerFXMLController mPlayerController) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PlaylistContent.fxml"));
        Parent playlistroot = loader.load();
        PlaylistContentController playlistContentController = loader.getController();
        playlistContentController.updatePlaylistData(playlistId);

        // Set the MPlayerFXMLController instance in the PlaylistContentController
        playlistContentController.setMPlayerController(mPlayerController);

        mPlayerController.loadplaylist(playlistroot);
    } catch (IOException e) {
        e.printStackTrace();
    }
}


  
  
  
    
    
    
    public  void addPlaylistToContainer(String playlist_name_text, Integer playlistId) {
    // Create a label for the playlist name
  
      Label playlist_name_label=new Label(playlist_name_text);
     ImageView playlist_image=new ImageView(new Image(getClass().getResourceAsStream("/resource/playlist.png")));
      //when specific playlist is clicked the vbox is changed by the Plylistfxml
      
   playlist_name_label.setOnMouseClicked(e -> playlistclicked(playlistId,mPlayerController));
  playlist_image.setOnMouseClicked(e -> playlistclicked(playlistId,mPlayerController));

      
     
     
     playlist_image.setFitHeight(25);
            playlist_image.setFitWidth(30);
    
            
              HBox playlist_hbox=new HBox(playlist_image,playlist_name_label);    
    playlist_hbox.setSpacing(1);
         playlist_container.getChildren().add(playlist_hbox);
         
           popUp.hide();   
         
          playlist_container.setUserData(playlistId);
         
         playlist_name_label.setOnMouseClicked(e -> playlistclicked(playlistId,mPlayerController));
        playlist_image.setOnMouseClicked(e -> playlistclicked(playlistId,mPlayerController));

    
}

    
    
    
    
    
}
