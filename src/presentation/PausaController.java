/*
 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static presentation.ParametrosPartidaController.*;

/**
 * FXML Controller class
 *
 * @author User
 */
public class PausaController extends JuegoLibreController implements Initializable {    
    @FXML
    private Button resume;
    @FXML
    private Button exit;
    @FXML
    private Button musicOptions;
    protected static AudioClip pauseMusic;
    // Points to this stage
    private Stage winStage;
    // Points to parent Stage
    private Stage parentStage;
    // Used to update the in-game song
    private String cancion;
    // Used to update the in-game song
    private AudioClip audio;
    ObservableList<Boolean> observPauseList;
    // Used to stop the countdown when the game ends
    private Timeline countdownPartida;
    private Timeline countdownTurno;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //audio.stop();
        pauseMusic = new AudioClip(this.getClass().getResource("/music/cancionPausa.mp3").toString());
        pauseMusic.play(0.07);
        /*if(cancionActual != null) {
            setAudio(cancionActual);
            audio.stop();
        }
        
        cancionActual = cancion;*/
    }    

    @FXML
    private void resume_onClick(ActionEvent event) throws IOException {
       //try{
//        cancion = cancionActual;
//        if(cancionActual != null /*&& cancionActual != ""*/){
            setAudio(cancion);
            audio.play(0.3);
            System.out.println("SETUP GAME SONG: " + cancion);
//        }
        pauseMusic.stop();
        observPauseList.set(0, Boolean.TRUE);
        countdownPartida.play();
        countdownTurno.play();
       //} catch (Exception e){}
        winStage.hide(); 
    }

    @FXML
    private void exit_onClick(ActionEvent event) throws IOException {
        // close timelines to avoid end screen
        countdownPartida.stop();
        countdownTurno.stop();
        parentStage.close();
        Stage thisStage = (Stage) resume.getScene().getWindow();
        thisStage.close();
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
        Parent root = (Parent) myLoader.load();
        MenuPrincipalController menuPrincipalController = myLoader.<MenuPrincipalController>getController();
        Stage wStage = new Stage();
        menuPrincipalController.initWindow(winStage);
        Scene scene = new Scene(root);
        wStage.setScene(scene);
        wStage.initModality(Modality.APPLICATION_MODAL);
        wStage.show();
        wStage.setTitle("TWINS");
        //stopAudio(cancion);
        pauseMusic.stop();
        
    }

    @FXML
    private void musicOptions_onClick(ActionEvent event) throws IOException {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("Musica.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        */
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Musica.fxml"));
        Parent root = (Parent) myLoader.load();
        MusicaController musicaController = myLoader.<MusicaController>getController();
        pauseMusic.stop();
        Stage winStage = new Stage();
        musicaController.initMusicaWindow(winStage);
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        winStage.setScene(scene);
        winStage.setTitle("Pausa");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
    }

    void initPausaWindow(Stage stage, Stage pStage, String cancion, AudioClip audio, ObservableList<Boolean> observPauseList, Timeline countdownPartida, Timeline countdownTurno ) {
        winStage = stage;
        parentStage = pStage;
        this.cancion = cancion;
        this.audio = audio;
        this.observPauseList = observPauseList;
        this.countdownPartida = countdownPartida;
        this.countdownTurno = countdownTurno;
    }
    
}