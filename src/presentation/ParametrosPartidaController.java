/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.media.AudioClip;
import static presentation.MusicaController.cancionActual;

/**
 * FXML Controller class
 *
 * @author Jesús Yoel
 */
public class ParametrosPartidaController extends JuegoLibreController implements Initializable {

    @FXML
    private Tab barajasPane;
    @FXML
    private Button defaultBarajasButton;
    @FXML
    private RadioButton pajarosNormal;
    @FXML
    private ToggleGroup seleccionBaraja;
    @FXML
    private RadioButton frutasNormal;
    @FXML
    private RadioButton baraja3Normal;
    @FXML
    private RadioButton baraja4Normal;
    @FXML
    private RadioButton pajarosRotacion;
    @FXML
    private RadioButton frutasRotacion;
    @FXML
    private RadioButton baraja3Rotacion;
    @FXML
    private RadioButton baraja4Rotacion;
    @FXML
    private Tab parametrosPane;
    @FXML
    private Label verParErrorBox;
    @FXML
    private RadioButton eHorizontal;
    @FXML
    private ToggleGroup rotacionButtons;
    @FXML
    private RadioButton eVertical;
    @FXML
    private RadioButton rDerecha;
    @FXML
    private RadioButton rIzquierda;
    @FXML
    private RadioButton normal;
    @FXML
    private Button defaultParametrosButton;
    @FXML
    private Spinner<Integer> volteoCartaBox;
    @FXML
    private Spinner<Integer> cartasInicioBox;
    @FXML
    private ComboBox<Integer> largoBox;
    @FXML
    private ComboBox<Integer> anchoBox;
    @FXML
    private Tab efectosPane;
    @FXML
    private ComboBox<?> visualOKBox;
    @FXML
    private ComboBox<AudioClip> soundOKBox;
    @FXML
    private ComboBox<?> visualFlipBox;
    @FXML
    private ComboBox<AudioClip> soundFlipBox;
    @FXML
    private ComboBox<?> visualFailBox;
    @FXML
    private ComboBox<AudioClip> soundFailBox;
    @FXML
    private Tab msuicaPane;
    @FXML
    private ComboBox<String> desplegableMusica;
    @FXML
    private Spinner<Integer> exposicionParErrorBox;
    
     //Música de la Partida   
     protected List<String> gameSongList = new ArrayList<String>();
     public static String cancionActual;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Música de la partida
         setLista();
        ObservableList<String> items = FXCollections.observableArrayList(gameSongList);
        desplegableMusica.setItems(items);
        
       
        cancionActual = "/music/Cancion1.mp3" ;//new Media(new File("Cancion1.mp3").toURI().toString());
        setAudio(cancionActual);
        audio.stop();
    }    

    @FXML
    private void setDefaultBarajas(ActionEvent event) {
    }

    @FXML
    private void elegirTablero(ActionEvent event) {
    }

    @FXML
    private void setDefaultParametros(ActionEvent event) {
    }

    
    @FXML
    private void playGameMusic(ActionEvent event) {
        seleccionarCancion();
        if(this.cancionActual != null){
            if(audio.isPlaying()) audio.stop();
            seleccionarCancion();
            setAudio(cancionActual);
            audio.play(0.4);
        } else if(audio.isPlaying()) audio.stop();
    }
    
    
     public void setLista(){
        gameSongList.add("Sin Música");
        gameSongList.add("Golf It Music");
        gameSongList.add("Zelda Remix");
        gameSongList.add("Force Theme Star Wars");
        gameSongList.add("Chill Music to play");
        
    }
     
     private void seleccionarCancion(){
        switch(desplegableMusica.getSelectionModel().getSelectedIndex()){
            case 0:
                cancionActual = null;
                break;
            case 1:
                cancionActual = "/music/Cancion1.mp3";
                break;
            case 2:
                cancionActual = "/music/Cancion2.mp3";
                break;
            case 3:
                cancionActual = "/music/Cancion3.mp3";
                break;
            case 4:
                cancionActual = "/music/Cancion4.mp3";
                break;
            default:
                cancionActual = "/music/Cancion1.mp3";   
        }
        
    }
}
