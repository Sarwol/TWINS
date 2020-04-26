/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twins;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import logic.Carta;
import logic.Tablero;


/**
 *
 * @author davit
 */
public class PartidaEstandarApplication extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        String partidaEstandarFXMLLocation = "." + File.separator + "src" + File.separator
                + "presentation" + File.separator + "PartidaEstandar.fxml";
        File partidaEstandarFXML = new File(partidaEstandarFXMLLocation);
        Parent root = FXMLLoader.load(partidaEstandarFXML.toURI().toURL());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
    
