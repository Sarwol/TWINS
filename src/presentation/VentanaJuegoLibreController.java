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
    
    public static String mode;
    private Stage winStage;
    private Stage parentStage;

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
        mode = "PartidaEstandar.fxml";
        MenuPrincipalController.musicaInicial.stop();
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource(mode));
        Parent root = (Parent) myLoader.load();
        PartidaEstandarController partidaEstController = myLoader.<PartidaEstandarController>getController();
        Stage winStage = new Stage();
        partidaEstController.initWindow(winStage);
        Stage thisStage = (Stage) estandarButton.getScene().getWindow();
        thisStage.close();
        parentStage.close();
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
        //System.out.println(mode);
    }

    @FXML
    private void abrirParametros(ActionEvent event) {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("ParametrosPartida.fxml"));
        MenuPrincipalController.musicaInicial.stop();
        Parent root = null;
        try{
        root = (Parent) myLoader.load();
        } catch(IOException e){}
        ParametrosPartidaController ventanaJuegoLibreController = myLoader.<ParametrosPartidaController>getController();
        Stage winStage = new Stage();
        ventanaJuegoLibreController.initWindow(winStage);
        Stage thisStage = (Stage) estandarButton.getScene().getWindow();
        //thisStage.close();
        //parentStage.close();
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
    }

    @FXML
    private void abrirPorCategoria(ActionEvent event) throws IOException {
        mode = "PartidaCategoria.fxml";
        MenuPrincipalController.musicaInicial.stop();
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource(mode));
        Parent root = (Parent) myLoader.load();
        PartidaCategoriaController partidaCatController = myLoader.<PartidaCategoriaController>getController();
        Stage winStage = new Stage();
        partidaCatController.initWindow(winStage);
        Stage thisStage = (Stage) estandarButton.getScene().getWindow();
        thisStage.close();
        parentStage.close();
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
        //System.out.println(mode);
    }

    @FXML
    private void abrirPorCarta(ActionEvent event) throws IOException {
        mode = "PartidaPorCarta.fxml";
        MenuPrincipalController.musicaInicial.stop();
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource(mode));
        Parent root = (Parent) myLoader.load();
        PartidaPorCartaController partidaCartController = myLoader.<PartidaPorCartaController>getController();
        Stage winStage = new Stage();
        partidaCartController.initWindow(winStage);
        Stage thisStage = (Stage) estandarButton.getScene().getWindow();
        thisStage.close();
        parentStage.close();
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
         //System.out.println(mode);
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        //winStage.close();
        Stage thisStage = (Stage) estandarButton.getScene().getWindow();
        thisStage.close();
    }

    void initVentana(Stage stage, Stage pStage) {
        winStage = stage;
        parentStage = pStage;
    }
    
}
