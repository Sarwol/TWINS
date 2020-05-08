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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author davit
 */
public class VentanaJuegoLibreController extends MenuPrincipalController implements Initializable {

    @FXML
    private Button estandarButton;
    @FXML
    private Button parametrosButton;
    @FXML
    private Button categoriaButton;
    @FXML
    private Button cartaButton;
    @FXML
    private Button atrasButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //player.setOnPaused(() -> System.out.println("pausado"));
        
    }    

    @FXML
    private void abrirEstandar(ActionEvent event) throws IOException {
            cargarFXML("PartidaEstandar.fxml");
    }

    @FXML
    private void abrirParametros(ActionEvent event) {
    }

    @FXML
    private void abrirPorCategoria(ActionEvent event) throws IOException {
         cargarFXML("PartidaCategoria.fxml");
    }

    @FXML
    private void abrirPorCarta(ActionEvent event) throws IOException {
         cargarFXML("PartidaPorCarta.fxml");
        
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        cargarFXML("MenuPrincipal.fxml");
    }
    
}
