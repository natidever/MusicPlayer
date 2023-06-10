
package musicplayer.newpackage;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class MPlayerFX extends Application {
    private MPlayerFXMLController controller;
    
public static Class<?> forName(String name, boolean initialize, ClassLoader loader)
    throws ClassNotFoundException
{
    return Class.forName(name, initialize, loader);
}
    @Override
    public void start(Stage primaryStage) throws IOException {
         //loading and staging
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("MPlayerFXML.fxml"));
//          System.out.println("FXML URL: " + getClass().getResource("MPlayerFXML.fxml"));
        Parent root = load.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Desing2.css").toExternalForm());
        primaryStage.setTitle("Ambasel");
//        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
         controller = load.getController();
//        System.out.println("Controller instance: " + controller);
        controller.setPrimaryStage(primaryStage);
           
        //ALL Music code in start method
    
 


  
    }
    public static void main(String[] args) {
        launch(args);
    }
}
