/*
 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static presentation.JuegoLibreController.cancion;
import static presentation.MusicaController.cancionActual;

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
    private Stage winStage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //audio.stop();
        pauseMusic = new AudioClip(this.getClass().getResource("/music/cancionPausa.mp3").toString());
        pauseMusic.play(0.07);
        if(cancionActual != null /*&& cancionActual != ""*/) {
            setAudio(cancionActual);
            audio.stop();
        }
        
        cancionActual = cancion;
    }    

    @FXML
    private void resume_onClick(ActionEvent event) throws IOException {
       //try{
        cancion = cancionActual;
        if(cancionActual != null /*&& cancionActual != ""*/){
            setAudio(cancion);
            audio.play(0.3);
        }
        pauseMusic.stop();
        observPauseList.set(0, Boolean.TRUE);
       //} catch (Exception e){}
        winStage.hide(); 
    }

    @FXML
    private void exit_onClick(ActionEvent event) {
        System.exit(0);
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

    void initPausaWindow(Stage stage) {
        winStage = stage;
    }
    
}