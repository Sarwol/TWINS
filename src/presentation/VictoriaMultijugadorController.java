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
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import logic.Puntuacion;

/**
 * FXML Controller class
 *
 * @author User
 */
public class VictoriaMultijugadorController implements Initializable {
    
    @FXML
    protected Button resetBtn;
    
    //THIS IS THE WAY WE DO IT IN MY TOWN, HEADBUTTING THE KEYBOARD UNTIL I FIND
    //THE SOLUTION, A BUTTON EDITED IN CSS.
    @FXML
    protected Button result;
    
    @FXML
    protected Label puntuacionJugador1;
    @FXML
    protected Label puntuacionJugador2;
    
    @FXML 
    protected Label textoTitulo;
    
    protected Label tempo;
    
    private Stage winStage;
    private String modo;
    private AudioClip fanfarria;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fanfarria = new AudioClip(this.getClass().getResource("/music/fanfarriaV.mp3").toString());
        fanfarria.play(0.30);
    }    

    @FXML
    private void reset_onClick(ActionEvent event) throws IOException {
        winStage.close();
        //System.out.println(modo);
        Parent root = FXMLLoader.load(getClass().getResource(modo));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    private void exit_onClick(ActionEvent event) throws IOException{
        Stage thisStage = (Stage) resetBtn.getScene().getWindow();
        thisStage.close();
        //AudioClip vuelta_cancion = new AudioClip(this.getClass().getResource("/music/HOME-Resonance.mp3").toString());
        //vuelta_cancion.play(0.15);
        JuegoMultijugadorController.musica.play(0.05);
    }
    
    void initVictoriaWindow(Stage stage, Puntuacion puntJ1, Puntuacion puntJ2, int t, String m) {
        winStage = stage;
        puntuacionJugador1.setText(puntJ1.getPuntos() + "");
        puntuacionJugador2.setText(puntJ2.getPuntos() + "");
        modo = m;
        if(puntJ1.getPuntos() > puntJ2.getPuntos()){
            textoTitulo.setText("Del Jugador 1");
        } else if(puntJ2.getPuntos() > puntJ1.getPuntos()){
            textoTitulo.setText("Del Jugador 2");
        } else {
            //JUST TO CHANGE IT IN THE CSS
            result.setId("resultEmpate");
        }
    }
}
