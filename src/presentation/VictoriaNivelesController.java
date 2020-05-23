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
import logic.Puntuacion;

/**
 * FXML Controller class
 *
 * @author User
 */
public class VictoriaNivelesController implements Initializable {

    @FXML
    private Button resetBtn;
    @FXML
    private Label puntu;
    @FXML
    private Button nextBtn;
    
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
    private void exit_onClick(ActionEvent event) throws IOException {
        Stage thisStage = (Stage) puntu.getScene().getWindow();
        thisStage.close();
        MenuSeleccionNivelesController.musica.play(0.15);
    }

    @FXML
    private void nextLevel_onClick(ActionEvent event) {
    }

    void initVictoriaWindow(Stage stage, Puntuacion p, int t, String m) {
        winStage = stage;
        puntu.setText(p.getPuntos() + "");
        //tempo.setText(t + "");
        modo = m;
    }
    
}
