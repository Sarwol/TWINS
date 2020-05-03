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
import javafx.scene.control.ToggleGroup;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jesus
 */
public class MusicaController extends JuegoLibreController implements Initializable {

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
    private RadioButton noMuiscaRadioButton;
    
    
    //public String cancion;
    public static String cancionActual;
    
    
    private MediaPlayer mediaplayer;
    
   
    
    private Stage winStage;
    @FXML
    private ToggleGroup musicaButtons;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*if(cancionActual != null) 
            audio.stop();
        */
         cancionActual = "/music/Cancion1.mp3" ;//new Media(new File("Cancion1.mp3").toURI().toString());
         setAudio(cancionActual);
         audio.stop();
    }    

    
    
    @FXML
    private void volverAction(ActionEvent event) {
	if(cancionActual != null){
            audio.stop();
            setAudio(cancionActual);
        }
        //cancionActual = cancion;
        winStage.hide(); 
        
    }

    @FXML
    private void elegirCancion1(ActionEvent event) {
        audio.stop();
       
        cancionActual = "/music/Cancion1.mp3";
        setAudio(cancionActual);
        audio.play(0.5);
        
        
        
    }
    @FXML
    private void elegirCancion2(ActionEvent event) {
        audio.stop();
        
       
        cancionActual = "/music/Cancion2.mp3";
        setAudio(cancionActual);
        audio.play(0.5);
         //cancion = new File("Cancion2.mp3").toURI().toString();
    
    }

    @FXML
    private void elegirCancion3(ActionEvent event) {
        //if(songIsPlaying(cancion))
            audio.stop();
        
        //manejarCanciones(cancion3RadioButton);
        cancionActual = "/music/Cancion3.mp3";
         setAudio(cancionActual);
        audio.play(0.5);
         //cancion = new File("Cancion3.mp3").toURI().toString();
    }

    @FXML
    private void elegirCancion4(ActionEvent event) {
        //if(songIsPlaying(cancion))
            audio.stop();
        
        //manejarCanciones(cancion4RadioButton);
        cancionActual = "/music/Cancion4.mp3";
         setAudio(cancionActual);
         audio.play(0.5);
         //cancion = new File("Cancion4.mp3").toURI().toString();
    }

  

    @FXML
    private void elegirNoCancion(ActionEvent event) {
        audio.stop();
        cancionActual = null;
        //manejarCanciones(noMuiscaRadioButton);
    }

  
     
    void initMusicaWindow(Stage stage) {
        winStage = stage;
    }
}
