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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
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

    public MediaPlayer player;
    public static AudioClip musicaInicial;
    private Stage winStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //player = new MediaPlayer(cargarCancion("src/music/HOME-Resonance.mp3"));
        //player.setAutoPlay(true);
        //player.play();
        //audio = new AudioClip((this.getClass().getResource("src/music/HOME-Resonance.mp3").toString()));
        musicaInicial = new AudioClip(this.getClass().getResource("/music/HOME-Resonance.mp3").toString());
        musicaInicial.play(0.3);
    }

    @FXML
    private void abrirJuegoLibre(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("VentanaJuegoLibre.fxml"));
        Parent root = (Parent) myLoader.load();
        VentanaJuegoLibreController ventanaJuegoLibreController = myLoader.<VentanaJuegoLibreController>getController();
        Stage winStage = new Stage();
        Stage thisStage = (Stage) salirButton.getScene().getWindow();
        ventanaJuegoLibreController.initVentana(winStage, thisStage);
        Scene scene = new Scene(root);
        
        // winStage is the stage of VentanaJuegoLibre
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
    }

    @FXML
    private void abrirNiveles(ActionEvent event) throws IOException {
        /*
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("MenuSeleccionNiveles.fxml"));
        Parent root = (Parent) myLoader.load();
        MenuSeleccionNivelesController menuSeleccionNivelesController = myLoader.<MenuSeleccionNivelesController>getController();
        Stage winStage = new Stage();
        Stage thisStage = (Stage) salirButton.getScene().getWindow();
        menuSeleccionNivelesController.initWindow(winStage);
        Scene scene = new Scene(root);
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
        // From VentanaJuegoLibre
        musicaInicial.stop();
        thisStage.close();
        */
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("MenuSeleccionNiveles.fxml"));
        Parent root = (Parent) myLoader.load();
        MenuSeleccionNivelesController menuSeleccionNivelesController = myLoader.<MenuSeleccionNivelesController>getController();
        Stage winStage = new Stage();
        Stage thisStage = (Stage) salirButton.getScene().getWindow();
        menuSeleccionNivelesController.initWindow(winStage, thisStage);
        Scene scene = new Scene(root);
        
        // winStage is the stage of VentanaJuegoLibre
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();

    }

    @FXML
    private void abrirBarajas(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("EditorBarajas.fxml"));
        Parent root = (Parent) myLoader.load();
        EditorBarajasController editorBarajasController = myLoader.<EditorBarajasController>getController();
        Stage winStage = new Stage();
        Stage thisStage = (Stage) salirButton.getScene().getWindow();
        editorBarajasController.initWindow(winStage, thisStage);
        Scene scene = new Scene(root);
        
        // winStage is the stage of VentanaJuegoLibre
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
    }

    @FXML
    private void salir(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
        //System.exit(0);
    }

    public Media cargarCancion(String cancion) {
        return new Media(new File(cancion).toURI().toString());
    }

    /*public void stopMediaPlayer(){
        player.stop();
        player.setOnPaused(() -> System.out.println("pausado"));
        
    }
    
    public void playMediaPlayer(){
        player.setAutoPlay(true);
        player.play();
    }
     */
    public void play() {
        musicaInicial.play();
    }

    public void stop() {
        musicaInicial.stop();
    }

    void initWindow(Stage stage) {
        winStage = stage;
    }
}
