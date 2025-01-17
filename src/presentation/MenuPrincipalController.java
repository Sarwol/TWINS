/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Configuracion;
import static presentation.VentanaJuegoLibreController.mode;

/**
 * FXML Controller class
 *
 * @author davit
 */
public class MenuPrincipalController implements Initializable {

    @FXML
    private Button barajasButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button nivelesButton;
    @FXML
    private Button cartaButton;
    @FXML
    private Button estandarButton;
    @FXML
    private Button categoriaButton;
    @FXML
    private Button multijugadorButton;
    @FXML
    private ImageView muteView;

    @FXML
    private Button salirButton;

    public MediaPlayer player;
    public static AudioClip musicaInicial;
    private Stage winStage;
    public static String mode;
    public Configuracion parametros = Configuracion.getInstance();
    //Guardar el progreso de los niveles
    public static Boolean[] progress = new Boolean[10];
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        musicaInicial = new AudioClip(this.getClass().getResource("/music/HOME-Resonance.mp3").toString());
        musicaInicial.play(0.05);
        for (int i = 0; i < 10; i++){
            progress[i] = false; 
            System.out.println(progress[0]);
        }
        //Si no existe persistencia de barajas lo creará
        initDeckPersistence();
    }

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
        winStage.getIcons().add(new Image("/buttons/twinslogo.png"));
        winStage.show();
    }

    @FXML
    private void abrirNiveles(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("MenuSeleccionNiveles.fxml"));
        Parent root = (Parent) myLoader.load();
        MenuSeleccionNivelesController menuSeleccionNivelesController = myLoader.<MenuSeleccionNivelesController>getController();
        Stage winStage = new Stage();
        Stage thisStage = (Stage) salirButton.getScene().getWindow();
        menuSeleccionNivelesController.initWindow(winStage, thisStage, musicaInicial);
        Scene scene = new Scene(root);
        
        // winStage is the stage of VentanaJuegoLibre
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.getIcons().add(new Image("/buttons/twinslogo.png"));
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
        winStage.getIcons().add(new Image("/buttons/twinslogo.png"));
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
    }

    @FXML
    private void salir(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText("Estás a punto de salir");
        alert.setContentText("¿Seguro que quieres salir?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        } else {
            System.out.println("CANCEL");
            }
    }

    public Media cargarCancion(String cancion) {
        return new Media(new File(cancion).toURI().toString());
    }

    public void play() {
        musicaInicial.play();
    }

    public void stop() {
        musicaInicial.stop();
    }

    void initWindow(Stage stage) {
        winStage = stage;
    }

    @FXML
    private void abrirPorCarta(ActionEvent event) throws IOException {
        if (parametros.getBarajaNormal().size() >= (parametros.getAnchuraTablero() * parametros.getLarguraTablero())/2){
            mode = "PartidaPorCarta.fxml";
            MenuPrincipalController.musicaInicial.stop();
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(mode));
            Parent root = (Parent) myLoader.load();
            PartidaPorCartaController partidaCartController = myLoader.<PartidaPorCartaController>getController();
            Stage winStage = new Stage();
            partidaCartController.initWindow(winStage, mode);
            Stage thisStage = (Stage) estandarButton.getScene().getWindow();
            thisStage.close();
            winStage.close();
            //We create the scene foe win1
            Scene scene = new Scene(root);
            //we asign new scene to current stage/window
            winStage.setScene(scene);
            winStage.setTitle("TWINS");
            winStage.getIcons().add(new Image("/buttons/twinslogo.png"));
            winStage.initModality(Modality.APPLICATION_MODAL);
            winStage.show();
        } else barajaNoCompatible();
    }

    @FXML
    private void abrirEstandar(ActionEvent event) throws IOException {
        if (parametros.getBarajaNormal().size() >= (parametros.getAnchuraTablero() * parametros.getLarguraTablero())/2){
            mode = "PartidaEstandar.fxml";
            MenuPrincipalController.musicaInicial.stop();
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(mode));
            Parent root = (Parent) myLoader.load();
            PartidaEstandarController partidaEstController = myLoader.<PartidaEstandarController>getController();
            Stage winStage = new Stage();
            partidaEstController.initWindow(winStage, mode);
            Stage thisStage = (Stage) estandarButton.getScene().getWindow();
            thisStage.close();
            winStage.close();
            //We create the scene foe win1
            Scene scene = new Scene(root);
            //we asign new scene to current stage/window
            winStage.setScene(scene);
            winStage.setTitle("TWINS");
            winStage.getIcons().add(new Image("/buttons/twinslogo.png"));
            winStage.initModality(Modality.APPLICATION_MODAL);
            winStage.show();
        } else barajaNoCompatible();
    }

    @FXML
    private void abrirPorCategoria(ActionEvent event) throws IOException {
        if(parametros.getBarajaCategoria().getCategorias().size() >= 2 && 
                parametros.getBarajaCategoria().size() >= (parametros.getAnchuraTablero() * parametros.getLarguraTablero())/2 ){
            mode = "PartidaCategoria.fxml";
            MenuPrincipalController.musicaInicial.stop();
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(mode));
            Parent root = (Parent) myLoader.load();
            PartidaCategoriaController partidaCatController = myLoader.<PartidaCategoriaController>getController();
            Stage winStage = new Stage();
            partidaCatController.initWindow(winStage, mode);
            Stage thisStage = (Stage) estandarButton.getScene().getWindow();
            thisStage.close();
            winStage.close();
            //We create the scene foe win1
            Scene scene = new Scene(root);
            //we asign new scene to current stage/window
            winStage.setScene(scene);
            winStage.setTitle("TWINS");
            winStage.getIcons().add(new Image("/buttons/twinslogo.png"));
            winStage.initModality(Modality.APPLICATION_MODAL);
            winStage.show();
            //System.out.println(mode);
        } else {
            if(parametros.getBarajaCategoria().size() >= (parametros.getAnchuraTablero() * parametros.getLarguraTablero())/2){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atención");
                alert.setHeaderText("Baraja no compatible con Categoria");
                alert.setContentText("La baraja que ha seleccionadono es compatible "
                        + "con el modo de juego de Categoria, ya que solo tiene una categoría. Seleccione una compatible o cree una baraja con más de una categoría");
                alert.showAndWait();
            } else barajaNoCompatible();
        }
    }

    @FXML
    private void abrirMultijugador(ActionEvent event) {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("JuegoMultijugador.fxml"));
        MenuPrincipalController.musicaInicial.stop();
        Parent root = null;
        try{
        root = (Parent) myLoader.load();
        } catch(IOException e){e.printStackTrace();}
        JuegoMultijugadorController juegoMultijugadorController = myLoader.<JuegoMultijugadorController>getController();
        Stage winStage = new Stage();
        juegoMultijugadorController.initWindow(winStage, musicaInicial);
        Stage thisStage = (Stage) estandarButton.getScene().getWindow();
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.getIcons().add(new Image("/buttons/twinslogo.png"));
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
    }


    @FXML
    private void changeMusic(MouseEvent event) throws URISyntaxException {
        if(musicaInicial.isPlaying()) {
            musicaInicial.stop();   
        } else {
            musicaInicial.play(0.05);    
        }
    }

    @FXML
    private void abrirParametros(MouseEvent event) {
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
        //We create the scene foe win1
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        winStage.setScene(scene);
        winStage.setTitle("TWINS");
        winStage.getIcons().add(new Image("/buttons/twinslogo.png"));
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
    }
    
    private void barajaNoCompatible(){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Baraja incompatible");
            alert.setContentText("La baraja que ha seleccionado no es adecuada para "
                    + "el tablero que ha escogido. Por favor, ajuste el tamaño del tablero o cambie la baraja seleccionada");
            alert.showAndWait();
      }
    
    public void initDeckPersistence(){
        File coleccion = new File(System.getProperty("user.home") + File.separator + "coleccion.xml");
        if(!coleccion.exists()){ // create the file
            String initialSetup = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<coleccion>\n</coleccion>";

            PrintWriter out = null; 
            try{
               out = new PrintWriter(System.getProperty("user.home") + File.separator + "coleccion.xml");
               out.println(initialSetup);
            } catch(FileNotFoundException fnfe){
                System.out.println("Cannot create file 'coleccion.xml'");
                fnfe.printStackTrace();
            }
            if(out != null) out.close();
        }
    }
}
