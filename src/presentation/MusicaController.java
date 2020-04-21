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
import java.net.URL;
import javafx.scene.media.AudioClip;
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
    
    String cancion;
    
    private MediaPlayer mediaplayer;
    
    private String cancionActual;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cancion = null;//new Media(new File("Cancion1.mp3").toURI().toString());
        
    }    

    private String getMusica(){
       return cancionActual; 
    }
    
    @FXML
    private void volverAction(ActionEvent event) {
	stopAudio(cancion);
        exit();
    }

    @FXML
    private void elegirCancion1(ActionEvent event) {
        reproducirButton.setDisable(false);
        manejarCanciones(cancion1RadioButton);
        cancion = "/music/Cancion1.mp3";
        
        cancionActual = new File("Cancion1.mp3").toURI().toString();
    }
    @FXML
    private void elegirCancion2(ActionEvent event) {
        reproducirButton.setDisable(false);
        manejarCanciones(cancion2RadioButton);
        cancion = "/music/Cancion2.mp3";
        
         cancionActual = new File("Cancion2.mp3").toURI().toString();
    
    }

    @FXML
    private void elegirCancion3(ActionEvent event) {
        reproducirButton.setDisable(false);
        manejarCanciones(cancion3RadioButton);
        cancion = "/music/Cancion3.mp3";
        
         cancionActual = new File("Cancion3.mp3").toURI().toString();
    }

    @FXML
    private void elegirCancion4(ActionEvent event) {
        reproducirButton.setDisable(false);
        manejarCanciones(cancion4RadioButton);
        cancion = "/music/Cancion4.mp3";
        
         cancionActual = new File("Cancion4.mp3").toURI().toString();
    }

    @FXML
    private void reproducir(ActionEvent event) {
        detenerButton.setDisable(false);
        botonSeleccionado(true);
        stopAudio(cancion);
        playAudio(cancion);
    }

    @FXML
    private void elegirNoCancion(ActionEvent event) {
        if(reproducirButton.isDisabled())
        reproducirButton.setDisable(true);
        cancionActual = null;
        manejarCanciones(noMuiscaRadioButton);
    }

    @FXML
    private void detener(ActionEvent event) {
        detenerButton.setDisable(true);
        botonSeleccionado(false);
        stopAudio(cancion);
        
    }
    
     private void playAudio(String sonido){
        AudioClip note = new AudioClip(this.getClass().getResource(sonido).toString());
        note.play();
    }
     
     private void stopAudio(String sonido){
         AudioClip note = new AudioClip(this.getClass().getResource(sonido).toString());
         note.stop();
     }
     
     private void manejarCanciones(RadioButton boton){
         if(boton.isSelected()){
        cancion1RadioButton.setDisable(true);
        cancion2RadioButton.setDisable(true);
        cancion3RadioButton.setDisable(true);
        cancion4RadioButton.setDisable(true);
        noMuiscaRadioButton.setDisable(true);
        boton.setDisable(false);
        } else {cancion1RadioButton.setDisable(false);
        cancion2RadioButton.setDisable(false);
        cancion3RadioButton.setDisable(false);
        cancion4RadioButton.setDisable(false);
        noMuiscaRadioButton.setDisable(false);
         }
      }
     private void botonSeleccionado(Boolean bool){
         if(cancion1RadioButton.isSelected()) {cancion1RadioButton.setDisable(bool);}
         else if(cancion2RadioButton.isSelected()) {cancion2RadioButton.setDisable(bool);}
         else if(cancion3RadioButton.isSelected()) {cancion3RadioButton.setDisable(bool);}
         else cancion4RadioButton.setDisable(bool);
     }
}
