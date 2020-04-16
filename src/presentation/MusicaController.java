/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import static java.awt.PageAttributes.MediaType.C;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.Platform.exit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import twins.TWINS;
import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class MusicaController implements Initializable {

    @FXML
    private Button volerButton;
    @FXML
    private RadioButton cancion1RadioButton;
    @FXML
    private RadioButton cancion2RadioButton;
    @FXML
    private RadioButton cancion3RadioButton;
    @FXML
    private RadioButton cancion4RadioButton;
    @FXML
    private Button reproducirButton;
    @FXML
    private RadioButton noMuiscaRadioButton;
    @FXML
    private Button detenerButton;
    
    private Media cancion;
    
    private MediaPlayer mediaplayer;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void volverAction(ActionEvent event) {
        exit();
    }

    @FXML
    private void elegirCancion1(ActionEvent event) {
        reproducirButton.setDisable(false);
        
        cancion = new Media(new File("Cancion1.mp3").toURI().toString());
        
         //logic.Partida.setMusica(new File("Cancion1.mp3").toURI().toString());
    }
    @FXML
    private void elegirCancion2(ActionEvent event) {
        reproducirButton.setDisable(false);
        
        cancion = new Media(new File("Cancion2.mp3").toURI().toString());
        
         //logic.Partida.setMusica(new File("Cancion2.mp3").toURI().toString());
    
    }

    @FXML
    private void elegirCancion3(ActionEvent event) {
        reproducirButton.setDisable(false);
        
        cancion = new Media(new File("Cancion3.mp3").toURI().toString());
        
         //logic.Partida.setMusica(new File("Cancion3.mp3").toURI().toString());
    }

    @FXML
    private void elegirCancion4(ActionEvent event) {
        reproducirButton.setDisable(false);
        
        cancion = new Media(new File("Cancion4.mp3").toURI().toString());
        
         //logic.Partida.setMusica(new File("Cancion4.mp3").toURI().toString());
    }

    @FXML
    private void reproducir(ActionEvent event) {
        mediaplayer = new MediaPlayer(cancion);
        detenerButton.setDisable(false);
        mediaplayer.play();
    }

    @FXML
    private void elegirNoCancion(ActionEvent event) {
        reproducirButton.setDisable(true);
        //logic.Partida.setMusica(null);
    }

    @FXML
    private void detener(ActionEvent event) {
        mediaplayer.stop();
        detenerButton.setDisable(true);
    }
    
}
