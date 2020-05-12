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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static presentation.MenuPrincipalController.musicaInicial;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MenuSeleccionNivelesController implements Initializable {

    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    @FXML
    private Button b5;
    @FXML
    private Button b6;
    @FXML
    private Button b7;
    @FXML
    private Button b8;
    @FXML
    private Button b9;
    @FXML
    private Button b10;

    private Stage winStage;
    private Stage parentStage;
    public static String nivel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void openLevel() throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("JuegoNiveles.fxml"));
        Parent root = (Parent) myLoader.load();
        JuegoNivelesController juegoNivelesController = myLoader.<JuegoNivelesController>getController();
        Stage winStage = new Stage();
        Stage thisStage = (Stage) b1.getScene().getWindow();
        juegoNivelesController.initWindow(winStage);
        Scene scene = new Scene(root);
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
        // From VentanaJuegoLibre
        musicaInicial.stop();
        thisStage.close();
        parentStage.close();
        
    }
    @FXML
    private void selectLevel1_onClick(ActionEvent event) {
        nivel = "nivel1.ser";
        try {
            openLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectLevel2_onClick(ActionEvent event) {
        nivel = "nivel2.ser";
        try {
            openLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectLevel3_onClick(ActionEvent event) {
        nivel = "nivel3.ser";
        try {
            openLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectLevel4_onClick(ActionEvent event) {
        nivel = "nivel4.ser";
        try {
            openLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectLevel5_onClick(ActionEvent event) {
        nivel = "nivel5.ser";
        try {
            openLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectLevel6_onClick(ActionEvent event) {
        nivel = "nivel6.ser";
        try {
            openLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectLevel7_onClick(ActionEvent event) {
        nivel = "nivel7.ser";
        try {
            openLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectLevel8_onClick(ActionEvent event) {
        nivel = "nivel8.ser";
        try {
            openLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectLevel9_onClick(ActionEvent event) {
        nivel = "nivel9.ser";
        try {
            openLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectLevel10_onClick(ActionEvent event) {
        nivel = "nivel10.ser";
        try {
            openLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void volver_onClick(ActionEvent event) {
        Stage thisStage = (Stage) b1.getScene().getWindow();
        thisStage.close();
    }

    void initWindow(Stage stage, Stage pStage) {
        winStage = stage;
        parentStage = pStage;
    }
}
