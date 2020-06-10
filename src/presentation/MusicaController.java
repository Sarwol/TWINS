/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.File;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
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
    
   
    
    
    //public String cancion;
    public static String cancionActual;
    
    
    private MediaPlayer mediaplayer;
    protected List<String> lista = new ArrayList<String>();
   
    
    private Stage winStage;
    @FXML
    private ComboBox<String> desplegableMusica;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*if(cancionActual != null) 
            audio.stop();
        */
        setLista();
        ObservableList<String> items = FXCollections.observableArrayList(lista);
        desplegableMusica.setItems(items);
        File f = new File("./images/appImages/playG.png");
       
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
         
        seleccionarCancion();
        //cancionActual = cancion;
        winStage.hide(); 
        
    }

    private void elegirCancion1(ActionEvent event) {
        audio.stop();
       
        cancionActual = "/music/Cancion1.mp3";
        setAudio(cancionActual);
        audio.play(0.5);
        
        
        
    }
    private void elegirCancion2(ActionEvent event) {
        audio.stop();
        
       
        cancionActual = "/music/Cancion2.mp3";
        setAudio(cancionActual);
        audio.play(0.5);
         //cancion = new File("Cancion2.mp3").toURI().toString();
    
    }

    private void elegirCancion3(ActionEvent event) {
        //if(songIsPlaying(cancion))
            audio.stop();
        
        //manejarCanciones(cancion3RadioButton);
        cancionActual = "/music/Cancion3.mp3";
         setAudio(cancionActual);
        audio.play(0.5);
         //cancion = new File("Cancion3.mp3").toURI().toString();
    }

    private void elegirCancion4(ActionEvent event) {
        //if(songIsPlaying(cancion))
            audio.stop();
        
        //manejarCanciones(cancion4RadioButton);
        cancionActual = "/music/Cancion4.mp3";
         setAudio(cancionActual);
         audio.play(0.3);
         //cancion = new File("Cancion4.mp3").toURI().toString();
    }

  
     
    void initMusicaWindow(Stage stage) {
        winStage = stage;
    }
    
    public void setLista(){
        lista.add("Sin Música");
        lista.add("Golf It Music");
        lista.add("Zelda Remix");
        lista.add("Force Theme Star Wars");
        lista.add("Chill Music to play");
        
    }

    
    
    private void seleccionarCancion(){
        switch(desplegableMusica.getSelectionModel().getSelectedIndex()){
            case 0:
                cancionActual = null;
                break;
            case 1:
                cancionActual = "/music/Cancion1.mp3";
                break;
            case 2:
                cancionActual = "/music/Cancion2.mp3";
                break;
            case 3:
                cancionActual = "/music/Cancion3.mp3";
                break;
            case 4:
                cancionActual = "/music/Cancion4.mp3";
                break;
            default:
                cancionActual = "/music/Cancion1.mp3";   
        }
       
    }

    @FXML
    private void playMusic(ActionEvent event) {
         if(audio.isPlaying()) audio.stop();
         seleccionarCancion();
         setAudio(cancionActual);
         audio.play(0.4);
    }
}
