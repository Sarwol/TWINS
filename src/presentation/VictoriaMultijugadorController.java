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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import logic.Puntuacion;

/**
 * FXML Controller class
 *
 * @author User
 */
public class VictoriaMultijugadorController implements Initializable {
    
    @FXML
    protected Button resetBtn;
    
    @FXML
    protected Label puntuacionJugador1;
    @FXML
    protected Label puntuacionJugador2;
    
    @FXML 
    protected Label textoTitulo;
    
    protected Label tempo;
    
    private Stage winStage;
    private String modo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
        Parent root = (Parent) myLoader.load();
        MenuPrincipalController menuPrincipalController = myLoader.<MenuPrincipalController>getController();
        Stage winStage = new Stage();
        menuPrincipalController.initWindow(winStage);
        Scene scene = new Scene(root);
        winStage.setScene(scene);
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
        winStage.setTitle("TWINS");
        //stopAudio(cancion);
        Stage thisStage = (Stage) puntuacionJugador1.getScene().getWindow();
        thisStage.close();
    }
    
    void initVictoriaWindow(Stage stage, Puntuacion puntJ1, Puntuacion puntJ2, int t, String m) {
        winStage = stage;
        puntuacionJugador1.setText(puntJ1.getPuntos() + "");
        puntuacionJugador2.setText(puntJ2.getPuntos() + "");
        modo = m;
        if(puntJ1.getPuntos() > puntJ2.getPuntos()){
            textoTitulo.setText("El Jugador 1 ha ganado");
        } else if(puntJ2.getPuntos() > puntJ1.getPuntos()){
            textoTitulo.setText("El Jugador 2 ha ganado");
        } else {
            textoTitulo.setText("Empate");
        }
    }

}
