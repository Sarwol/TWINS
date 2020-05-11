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
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author User
 */
public class DerrotaController implements Initializable {
    
    @FXML
    protected Button resetBtn;
    
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
        
        Parent root = FXMLLoader.load(getClass().getResource(modo));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        //System.out.println("RELOADED FXML: " + modo);
    }
    
    @FXML
    private void exit_onClick(ActionEvent event) {
        System.exit(0);
    }
    
    void initDerrotaWindow(Stage stage, String m) {
        winStage = stage;
        modo = m;
    }
    
}
