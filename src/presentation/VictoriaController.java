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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import logic.Puntuacion;

/**
 * FXML Controller class
 *
 * @author User
 */
public class VictoriaController implements Initializable {
    
    @FXML
    protected Button resetBtn;
    
    @FXML
    protected Label puntu;
    
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
        Stage thisStage = (Stage) puntu.getScene().getWindow();
        thisStage.close();
    }
    
    void initVictoriaWindow(Stage stage, Puntuacion p, int t, String m) {
        winStage = stage;
        puntu.setText(p.getPuntos() + "");
        //tempo.setText(t + "");
        modo = m;
    }

}
