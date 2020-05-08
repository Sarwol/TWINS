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
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javax.swing.SpinnerNumberModel;
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
    private RadioButton defaultBaraja;
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
    private ComboBox<Integer> volteoCartaBox;
    @FXML
    private ComboBox<Integer> largoBox;
    @FXML
    private ComboBox<Integer> anchoBox;
    @FXML
    private Tab efectosPane;
    @FXML
    private ComboBox<?> visualOKBox;
    @FXML
    private ComboBox<String> soundOKBox;
    @FXML
    private ComboBox<?> visualFlipBox;
    @FXML
    private ComboBox<String> soundFlipBox;
    @FXML
    private ComboBox<?> visualFailBox;
    @FXML
    private ComboBox<String> soundFailBox;
    @FXML
    private Tab msuicaPane;
    @FXML
    private ComboBox<String> desplegableMusica;
    @FXML
    private ComboBox<Integer> exposicionParErrorBox;
    @FXML
    private ComboBox<Integer> tiempoPartidaBox;
    
    
     //Música de la Partida   
     protected List<String> gameSongList = new ArrayList<String>();
     public static String cancionActual;
     ////////////////////////////////////////////////////////////////////////////////
     
    //Parámetro de la Partida
        //Parámetros
            protected List<Integer> tamañoTablero = new ArrayList<Integer>();
        //Efectos
            protected List<String> sonidos = new ArrayList<String>();
            protected static String sonidoActual;
    
    
            
            
   //Barajas
     //Baraja barajaPartida;
     //Baraja barajaRotacion;       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Barajas
        defaultBaraja.setSelected(true);
        //pajarosRotacion.setSelected(true);
        //frutasRotacion.setSelected(true);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Parámetros de la Partida (tamaño tablero)
        setComboBox(2,4,6,8);
        ObservableList<Integer> itemsTablero = FXCollections.observableArrayList(tamañoTablero);
        largoBox.setItems(itemsTablero);
        tamañoTablero.remove(3);
        itemsTablero = FXCollections.observableArrayList(tamañoTablero);
        anchoBox.setItems(itemsTablero);
        //Parámetros de Partida (tiempo volteo carta)
        setComboBox(5,10,15);
        itemsTablero = FXCollections.observableArrayList(tamañoTablero);
        volteoCartaBox.setItems(itemsTablero);
        //Parámetros de Partida (tiempo pareja errónea)
        setComboBox(1,2,3,4);
        itemsTablero = FXCollections.observableArrayList(tamañoTablero);
        exposicionParErrorBox.setItems(itemsTablero);
        //Parámetros de Partida (tiempo de partida)
        setComboBox(45,60,90,120);
        itemsTablero = FXCollections.observableArrayList(tamañoTablero);
        tiempoPartidaBox.setItems(itemsTablero);
        //Parámetros de Partida (tipo de tablero)
        normal.setSelected(true);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Efectos de Partida
        //Sonidos
        setSonido("Acierto 1");
        ObservableList<String> itemsSonidos = FXCollections.observableArrayList(sonidos);
        soundOKBox.setItems(itemsSonidos);
        setSonido("Giro 1");
        itemsSonidos = FXCollections.observableArrayList(sonidos);
        soundFlipBox.setItems(itemsSonidos);
        setSonido("Fallo 1");
        itemsSonidos = FXCollections.observableArrayList(sonidos);
        soundFailBox.setItems(itemsSonidos);
        //A falta de añadir los Efectos Visuales
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Música de la partida
        setListaCanciones();
        ObservableList<String> itemsSong = FXCollections.observableArrayList(gameSongList);
        desplegableMusica.setItems(itemsSong);
        cancionActual = "/music/Cancion1.mp3" ;
        setAudio(cancionActual);
        audio.stop();
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    //Métodos Música
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
    
    
     public void setListaCanciones(){
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
    ///////////////////////////////////////////////////////////////////////////////////
     
    //Métodos Parámetros de Partida
     public void setComboBox(int i, int j, int k){
        tamañoTablero.clear();
        tamañoTablero.add(i);
        tamañoTablero.add(j);
        tamañoTablero.add(k);
     }
     public void setComboBox(int i, int j, int k, int m){
        tamañoTablero.clear();
        tamañoTablero.add(i);
        tamañoTablero.add(j);
        tamañoTablero.add(k);
        tamañoTablero.add(m);
     }
     
     //Métodos de Efectos de Partida
     public void setSonido(String string){
         sonidos.clear();
         sonidos.add(string);
     }
     
     //////////////////////////////////////////////////////////////////////////////////////
     //Métodos de Barajas
     
    @FXML
    private void elegirBarajaEstandar(ActionEvent event) {
        
    }
    
     @FXML
    private void pajarosNormal1(MouseEvent event) {
         if(pajarosNormal.isSelected()) {
            pajarosNormal.setSelected(false);
        } else pajarosNormal.setSelected(true);
    }

    @FXML
    private void frutasNormal2(MouseEvent event) {
        if(frutasNormal.isSelected()) {
            frutasNormal.setSelected(false);
        } else frutasNormal.setSelected(true);
    }

    @FXML
    private void barajaNormal3(MouseEvent event) {
        if(baraja3Normal.isSelected()) {
            baraja3Normal.setSelected(false);
        } else baraja3Normal.setSelected(true);
    }

    @FXML
    private void barajaNormal4(MouseEvent event) {
        if(baraja4Normal.isSelected()) {
            baraja4Normal.setSelected(false);
        } else baraja4Normal.setSelected(true);
    }
    
    @FXML
    private void pajarosRotacion1(MouseEvent event) {
        if(pajarosRotacion.isSelected()) {
            pajarosRotacion.setSelected(false);
        } else pajarosRotacion.setSelected(true);
    
    }

    @FXML
    private void frutasRotacion2(MouseEvent event) {
        if(frutasRotacion.isSelected()) {
            frutasRotacion.setSelected(false);
        } else frutasRotacion.setSelected(true);
    }
    

    @FXML
    private void barajaRotacion3(MouseEvent event) {
        if(baraja3Rotacion.isSelected()) {
            baraja3Rotacion.setSelected(false);
        } else baraja3Rotacion.setSelected(true);
    }


    @FXML
    private void barajaRotacion4(MouseEvent event) {
        if(baraja4Rotacion.isSelected()) {
            baraja4Rotacion.setSelected(false);
        } else baraja4Rotacion.setSelected(true);
    }

   

     
}