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
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class PausaController implements Initializable {    
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
        setAudio(cancion);
        audio.play(0.3);
        pauseMusic.stop();
        observPauseList.set(0, Boolean.TRUE);
        if(countdownPartida != null && countdownTurno != null){
            countdownPartida.play();
            countdownTurno.play();
        }
        winStage.hide(); 
    }

    @FXML
    private void exit_onClick(ActionEvent event) throws IOException {
        pauseMusic.stop();
        // close timelines to avoid end screen
        if(countdownPartida != null && countdownTurno != null){
            countdownPartida.stop();
            countdownTurno.stop();
        }
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
        wStage.getIcons().add(new Image("/buttons/twinslogo.png"));
        pauseMusic.stop();
        
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
    
    public void setAudio(String sonido) {	
        audio = new AudioClip(this.getClass().getResource(sonido).toString());	
    }
}
