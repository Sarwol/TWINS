/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twins;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 *
 * @author davit
 */
public class PartidaEstandarApplication extends Application{
    //public static String mode = "PartidaEstandar.fxml";
    
    @Override
    public void start(Stage stage) throws Exception {
        /*String partidaEstandarFXMLLocation = "." + File.separator + "src" + File.separator
                + "presentation" + File.separator + mode;
        File partidaEstandarFXML = new File(partidaEstandarFXMLLocation);
        Parent root = FXMLLoader.load(partidaEstandarFXML.toURI().toURL());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
        Parent root = FXMLLoader.load(getClass().getResource("/presentation/MenuPrincipal.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("/css/menuprincipal.css").toExternalForm());

        stage.setTitle("TWINS");
        stage.setScene(scene);
        stage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
    
