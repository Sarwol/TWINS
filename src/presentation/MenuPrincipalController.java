/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author davit
 */
public class MenuPrincipalController implements Initializable {

    @FXML
    private Button juegoLibreButton;
    @FXML
    private Button barajasButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button nivelesButton;
    @FXML
    private Button salirButton;

    private MediaPlayer player;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        player = new MediaPlayer(cargarCancion("src/music/HOME - Resonance.mp3"));
        player.setAutoPlay(true);
        player.play();
    }    

    @FXML
    private void abrirJuegoLibre(ActionEvent event) {
    }

    @FXML
    private void abrirNiveles(ActionEvent event) {
    }

    @FXML
    private void abrirBarajas(ActionEvent event) {
    }

    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
    
    public void cargarFXML(String fxml) throws IOException{
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource(fxml));
            Parent root = (Parent) miCargador.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);            
            stage.show();
    }
    
    public Media cargarCancion(String cancion){
        return new Media(new File(cancion).toURI().toString());
    }
}
