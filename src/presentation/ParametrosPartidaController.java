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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javax.swing.SpinnerNumberModel;
import static presentation.MusicaController.cancionActual;

/**
 * FXML Controller class
 *
 * @author Jesús Yoel
 */
public class ParametrosPartidaController extends JuegoLibreController implements Initializable {
    
    @FXML
    private Button saveButton;
    @FXML
    private Button saveButton2;
    @FXML
    private Button saveButton3;
    @FXML
    private Button saveButton4;
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
            //Parámetros que actualizarán los datos de la siguiente partida
                public static int nuevaLargura;
                public static int nuevaAnchura;
                public static int nuevoTiempoTurno;
                public static int nuevoTiempoPartida;
                public static int nuevoTiempoError;
            //Tipo de Tablero 
                //
        //Efectos
            protected List<String> sonidos = new ArrayList<String>();
            //Variables que setearán los sonidos en la partida
                public static String sonidoActualAcierto;
                public static String sonidoActualFallo;
                public static String sonidoActualGiro;
            
          
   //Barajas
     //Baraja barajaPartida;
     //Baraja barajaRotacion; 
   ////////////////////////////////////////////////////////////////////////////////////         
   
    
            
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Barajas
            defaultBaraja.setSelected(true);
            pajarosRotacion.setSelected(true);
            frutasRotacion.setSelected(true);
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
                setSonido("Acierto 1","Acierto 2", "Acierto 3");
                ObservableList<String> itemsSonidos = FXCollections.observableArrayList(sonidos);
                soundOKBox.setItems(itemsSonidos);
                setSonido("Giro 1", "Giro 2", "Giro 3");
                itemsSonidos = FXCollections.observableArrayList(sonidos);
                soundFlipBox.setItems(itemsSonidos);
                setSonido("Fallo 1","Fallo 2", "Fallo 3");
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
    private void saveAction(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
    }

    //Botones de restablecer a Valores Predeterminados
    @FXML
    private void setDefaultBarajas(ActionEvent event) {
        defaultBaraja.setSelected(true);
        pajarosRotacion.setSelected(true);
        frutasRotacion.setSelected(true);
        if(baraja3Rotacion.isSelected()) baraja3Rotacion.setSelected(false);
        if(baraja4Rotacion.isSelected()) baraja4Rotacion.setSelected(false);
    }

    @FXML
    private void setDefaultParametros(ActionEvent event) {
        largoBox.setValue(6);
        anchoBox.setValue(4);
        volteoCartaBox.setValue(5);
        exposicionParErrorBox.setValue(2);
        tiempoPartidaBox.setValue(60);
        normal.setSelected(true);
    }

    //////////////////////////////////////////////////////////////////////////////////////
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
    
    @FXML
    private void stopDesplegableMusic(MouseEvent event) {
        if(audio.isPlaying()) audio.stop();
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
     
      @FXML
    private void elegirTablero(ActionEvent event) {
    }
     
     //Métodos de Efectos de Partida
     
     
     //////////////////////////////////////////////////////////////////////////////////////
     //Métodos de los Efectos
    
     @FXML
    private void playOKSound(ActionEvent event) {
         seleccionarSonidoAcierto();
        if(this.sonidoActualAcierto != null){
            if(audio.isPlaying()) audio.stop();
            seleccionarSonidoAcierto();
            setAudio(sonidoActualAcierto);
            audio.play(0.1);
        } else if(audio.isPlaying()) audio.stop();
    }

    @FXML
    private void playFailSound(ActionEvent event) {
         seleccionarSonidoFallo();
        if(this.sonidoActualFallo != null){
            if(audio.isPlaying()) audio.stop();
            seleccionarSonidoFallo();
            setAudio(sonidoActualFallo);
            audio.play(0.1);
        } else if(audio.isPlaying()) audio.stop();
        
    }

    @FXML
    private void playFlipSound(ActionEvent event) {
         seleccionarSonidoGiro();
        if(this.sonidoActualGiro != null && this.sonidoActualGiro != ""){
            if(audio.isPlaying()) audio.stop();
            seleccionarSonidoGiro();
            setAudio(sonidoActualGiro);
            audio.play(0.4);
        } else if(audio.isPlaying()) audio.stop();
    }
    
    
    public void setSonido(String string1, String string2, String string3){
         sonidos.clear();
         sonidos.add(string1);
         sonidos.add(string2);
         sonidos.add(string3);
     } 
    
    
     private void seleccionarSonidoAcierto(){
        switch(soundOKBox.getSelectionModel().getSelectedIndex()){
            case 0:
                sonidoActualAcierto = "/music/correct.mp3"; 
                break;
            case 1:
                sonidoActualAcierto = "/music/correct2.mp3"; 
                break;
            case 2:
                sonidoActualAcierto = "/music/correct3.wav"; 
                break;
            
            default:
                sonidoActualAcierto = "/music/correct.mp3";   
        }
        
    }
     
     private void seleccionarSonidoFallo(){
        switch(soundFailBox.getSelectionModel().getSelectedIndex()){
            case 0:
                sonidoActualFallo = "/music/fail.mp3";
                break;
            case 1:
                sonidoActualFallo = "/music/fail2.mp3"; 
                break;
            case 2:
                sonidoActualFallo = "/music/fail3.wav";
                break;
            
            default:
                sonidoActualFallo = "/music/fail.mp3";  
        }
        
    }
     
    private void seleccionarSonidoGiro(){
        switch(soundFlipBox.getSelectionModel().getSelectedIndex()){ //insertar sonido de giro
            case 0:
                sonidoActualGiro = "/music/flip.wav";
                break;
            case 1:
                sonidoActualGiro = "/music/flip2.wav";
                break;
            case 2:
                sonidoActualGiro = "/music/flip3.wav";
                break;
            default:
                sonidoActualGiro = "/music/flip.wav";  
        }
        
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
