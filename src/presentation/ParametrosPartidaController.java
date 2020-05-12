/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.File;
import java.net.URISyntaxException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import logic.Baraja;
import logic.Carta;
import logic.Configuracion;

/**
 * FXML Controller class
 *
 * @author Jesús Yoel
 */
public class ParametrosPartidaController extends JuegoLibreController implements Initializable {

    @FXML
    private ImageView normal1;
    @FXML
    private ImageView normal2;
    @FXML
    private ImageView normal3;
    @FXML
    private ImageView normal4;
    @FXML
    private ImageView categ1;
    @FXML
    private ImageView categ2;
    @FXML
    private ImageView categ3;
    @FXML
    private ImageView categ4;
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
    @FXML
    private CheckBox limiteChekbox;
    @FXML
    private CheckBox showCardsBox;
    @FXML
    private ComboBox<Integer> showCardsTime;
    @FXML
    private ToggleGroup barajasCategoria;

    //Música de la Partida   
    protected List<String> gameSongList = new ArrayList<String>();
    public static String cancionActual;
    public static boolean sinMusica = false;
    ////////////////////////////////////////////////////////////////////////////////

    //Parámetro de la Partida
    //Parámetros
    protected List<Integer> tamañoTablero = new ArrayList<Integer>();
    //Parámetros que actualizarán los datos de la siguiente partida
    public static int nuevaLargura = 6;
    public static int nuevaAnchura = 4;
    public static int nuevoTiempoTurno = 5;
    public static int nuevoTiempoPartida = 60;
    public static int nuevoTiempoError = 2;

    //Tipo de Tablero 
    //
    //Efectos
    protected List<String> sonidos = new ArrayList<String>();
    public static int tiempoMostrarCartas = 2;
    //Variables que setearán los sonidos en la partida
    public static String sonidoActualAcierto = "/music/correct.mp3";
    public static String sonidoActualFallo = sonidoActualFallo = "/music/fail.mp3";
    public static String sonidoActualGiro = sonidoActualGiro = "/music/flip.wav";

    //Barajas
    Baraja barajaDefault = generarBaraja(24, "card", "Baraja1");
    Baraja baraja2 = generarBaraja(24, "fruit", "Baraja2");
    public static Baraja barajaNormalActual;
    public static Baraja barajaCategoriaActual;
    public static String imagenCarta = "fruit";
    ////////////////////////////////////////////////////////////////////////////////////         

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        defaultParameters();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Barajas
        setearImagenCartas();
        defaultBaraja.setSelected(true);
        pajarosRotacion.setSelected(true);
        barajaNormalActual = barajaDefault;
        barajaCategoriaActual = barajaDefault;

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Parámetros de la Partida (tamaño tablero)
        setComboBox(2, 4, 6, 8);
        ObservableList<Integer> itemsTablero = FXCollections.observableArrayList(tamañoTablero);
        largoBox.setItems(itemsTablero);
        tamañoTablero.remove(3);
        itemsTablero = FXCollections.observableArrayList(tamañoTablero);
        anchoBox.setItems(itemsTablero);
        //Parámetros de Partida (tiempo volteo carta)
        setComboBox(5, 10, 15);
        itemsTablero = FXCollections.observableArrayList(tamañoTablero);
        volteoCartaBox.setItems(itemsTablero);
        //Parámetros de Partida (tiempo pareja errónea)
        setComboBox(1, 2, 3, 4);
        itemsTablero = FXCollections.observableArrayList(tamañoTablero);
        exposicionParErrorBox.setItems(itemsTablero);
        //Parámetros de Partida (tiempo de partida)
        setComboBox(45, 60, 90, 120);
        itemsTablero = FXCollections.observableArrayList(tamañoTablero);
        tiempoPartidaBox.setItems(itemsTablero);
        //Efecto de mostrar las cartas al principio
        setComboBox(2, 3, 4, 5);
        itemsTablero = FXCollections.observableArrayList(tamañoTablero);
        showCardsTime.setItems(itemsTablero);
        //Parámetros de Partida (tipo de tablero)
        normal.setSelected(true);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Efectos de Partida
        //Sonidos
        setSonido("Acierto 1", "Acierto 2", "Acierto 3");
        ObservableList<String> itemsSonidos = FXCollections.observableArrayList(sonidos);
        soundOKBox.setItems(itemsSonidos);
        sonidoActualAcierto = "/music/correct.mp3";
        setSonido("Giro 1", "Giro 2", "Giro 3");
        itemsSonidos = FXCollections.observableArrayList(sonidos);
        soundFlipBox.setItems(itemsSonidos);
        sonidoActualGiro = "/music/flip.wav";
        setSonido("Fallo 1", "Fallo 2", "Fallo 3");
        itemsSonidos = FXCollections.observableArrayList(sonidos);
        soundFailBox.setItems(itemsSonidos);
        sonidoActualFallo = "/music/fail.mp3";
        //A falta de añadir los Efectos Visuales

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Música de la partida
        setListaCanciones();
        ObservableList<String> itemsSong = FXCollections.observableArrayList(gameSongList);
        desplegableMusica.setItems(itemsSong);
        cancionActual = "/music/Cancion1.mp3";
        setAudio(cancionActual);
        audio.stop();
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    @FXML
    private void saveAction(ActionEvent event) {
        if (audio.isPlaying()) {
            audio.stop();
        }
        nuevaLargura = largoBox.getValue();
        nuevaAnchura = anchoBox.getValue();
        //Parámetros de partida
        if (barajaNormalActual.getCartas().size() >= nuevaLargura * nuevaAnchura) {

            if (limiteChekbox.isSelected()) {
                parametros.setLimitePartida(true);
            } else {
                parametros.setLimitePartida(false);
                limiteActivado = "";
            }
            if (cancionActual != null) {
                parametros.setSinMusica(false);
                parametros.setCancionPartida(cancionActual);
            }
            nuevaLargura = largoBox.getValue();
            nuevaAnchura = anchoBox.getValue();
            parametros.setAnchuraTablero(nuevaAnchura);
            parametros.setLarguraTablero(nuevaLargura);

            parametros.setSonidoCorrecto(sonidoActualAcierto);
            parametros.setSonidoFallo(sonidoActualFallo);
            parametros.setSonidoGiro(sonidoActualGiro);

            if (parametros.isMostrarCartasInicio()) {
                tiempoMostrarCartas = showCardsTime.getValue();
                parametros.setTiempoCartasInicio(tiempoMostrarCartas);
            }
            if (parametros.isLimitePartida()) {
                nuevoTiempoTurno = volteoCartaBox.getValue();
                parametros.setTiempoTurno(nuevoTiempoTurno);
                nuevoTiempoPartida = tiempoPartidaBox.getValue();
                parametros.setTiempoPartida(nuevoTiempoPartida);
            }

            nuevoTiempoError = exposicionParErrorBox.getValue();
            parametros.setTiempoVerError(nuevoTiempoError);
            //Efectos

            //if(showCardsBox.isSelected()) tiempoMostrarCartas = showCardsTime.getValue(); 
            //Barajas
            parametros.setCartaPartida(imagenCarta);

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Baraja incompatible");
            alert.setContentText("La baraja que ha seleccionado es más pequeña que "
                    + "el tablero que ha escogido. Por favor, cambie alguna de las dos cosas");
            alert.showAndWait();
        }
    }

    //Botones de restablecer a Valores Predeterminados
    @FXML
    private void setDefaultBarajas(ActionEvent event) {
        defaultBaraja.setSelected(true);
        pajarosRotacion.setSelected(true);
        if (baraja3Rotacion.isSelected()) {
            baraja3Rotacion.setSelected(false);
        }
        if (baraja4Rotacion.isSelected()) {
            baraja4Rotacion.setSelected(false);
        }
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
        if (this.cancionActual != null) {
            if (audio.isPlaying()) {
                audio.stop();
            }
            seleccionarCancion();
            setAudio(cancionActual);
            audio.play(0.4);
        } else if (audio.isPlaying()) {
            audio.stop();
        }
    }

    @FXML
    private void stopDesplegableMusic(MouseEvent event) {
        if (audio.isPlaying()) {
            audio.stop();
        }
    }

    public void setListaCanciones() {
        gameSongList.add("Sin Música");
        gameSongList.add("Golf It Music");
        gameSongList.add("Zelda Remix");
        gameSongList.add("Force Theme Star Wars");
        gameSongList.add("Chill Music to play");

    }

    private void seleccionarCancion() {
        switch (desplegableMusica.getSelectionModel().getSelectedIndex()) {
            case 0:
                cancionActual = null;
                sinMusica = true;
                parametros.setSinMusica(sinMusica);
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
    public void setComboBox(int i, int j, int k) {
        tamañoTablero.clear();
        tamañoTablero.add(i);
        tamañoTablero.add(j);
        tamañoTablero.add(k);
    }

    public void setComboBox(int i, int j, int k, int m) {
        tamañoTablero.clear();
        tamañoTablero.add(i);
        tamañoTablero.add(j);
        tamañoTablero.add(k);
        tamañoTablero.add(m);
    }

    @FXML
    private void elegirTablero(ActionEvent event) {
    }

    @FXML
    private void disableLimites(ActionEvent event) {
        if (limiteChekbox.isSelected()) {
            volteoCartaBox.setDisable(false);
            tiempoPartidaBox.setDisable(false);
        } else {
            volteoCartaBox.setDisable(true);
            tiempoPartidaBox.setDisable(true);
        }
    }
    //Métodos de Efectos de Partida

    //////////////////////////////////////////////////////////////////////////////////////
    //Métodos de los Efectos
    @FXML
    private void disableCardTime(ActionEvent event) {
        if (showCardsBox.isSelected()) {
            showCardsTime.setDisable(false);
            parametros.setMostrarCartasInicio(true);
        } else {
            showCardsTime.setDisable(true);
            parametros.setMostrarCartasInicio(false);
        }
    }

    @FXML
    private void playOKSound(ActionEvent event) {
        seleccionarSonidoAcierto();
        if (this.sonidoActualAcierto != null) {
            if (audio.isPlaying()) {
                audio.stop();
            }
            seleccionarSonidoAcierto();
            setAudio(sonidoActualAcierto);
            audio.play(0.1);
        } else if (audio.isPlaying()) {
            audio.stop();
        }
    }

    @FXML
    private void playFailSound(ActionEvent event) {
        seleccionarSonidoFallo();
        if (this.sonidoActualFallo != null) {
            if (audio.isPlaying()) {
                audio.stop();
            }
            seleccionarSonidoFallo();
            setAudio(sonidoActualFallo);
            audio.play(0.1);
        } else if (audio.isPlaying()) {
            audio.stop();
        }

    }

    @FXML
    private void playFlipSound(ActionEvent event) {
        seleccionarSonidoGiro();
        if (this.sonidoActualGiro != null && this.sonidoActualGiro != "") {
            if (audio.isPlaying()) {
                audio.stop();
            }
            seleccionarSonidoGiro();
            setAudio(sonidoActualGiro);
            audio.play(0.4);
        } else if (audio.isPlaying()) {
            audio.stop();
        }
    }

    public void setSonido(String string1, String string2, String string3) {
        sonidos.clear();
        sonidos.add(string1);
        sonidos.add(string2);
        sonidos.add(string3);
    }

    private void seleccionarSonidoAcierto() {
        switch (soundOKBox.getSelectionModel().getSelectedIndex()) {
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

    private void seleccionarSonidoFallo() {
        switch (soundFailBox.getSelectionModel().getSelectedIndex()) {
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

    private void seleccionarSonidoGiro() {
        switch (soundFlipBox.getSelectionModel().getSelectedIndex()) { //insertar sonido de giro
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
        if (pajarosNormal.isSelected()) {
            pajarosNormal.setSelected(false);
        } else {
            pajarosNormal.setSelected(true);
        }
        imagenCarta = "card";
        barajaNormalActual = barajaDefault;
    }

    @FXML
    private void frutasNormal2(MouseEvent event) {
        if (frutasNormal.isSelected()) {
            frutasNormal.setSelected(false);
        } else {
            frutasNormal.setSelected(true);
        }
        imagenCarta = "fruit";
        barajaNormalActual = baraja2;
    }

    @FXML
    private void barajaNormal3(MouseEvent event) {
        if (baraja3Normal.isSelected()) {
            baraja3Normal.setSelected(false);
        } else {
            baraja3Normal.setSelected(true);
        }
        imagenCarta = "card";
        barajaNormalActual = barajaDefault;
    }

    @FXML
    private void barajaNormal4(MouseEvent event) {
        if (baraja4Normal.isSelected()) {
            baraja4Normal.setSelected(false);
        } else {
            baraja4Normal.setSelected(true);
        }
        imagenCarta = "fruit";
        barajaNormalActual = baraja2;
    }

    @FXML
    private void pajarosRotacion1(MouseEvent event) {
        if (pajarosRotacion.isSelected()) {
            pajarosRotacion.setSelected(false);
        } else {
            pajarosRotacion.setSelected(true);
        }
        imagenCarta = "card";
        barajaCategoriaActual = barajaDefault;

    }

    @FXML
    private void frutasRotacion2(MouseEvent event) {
        if (frutasRotacion.isSelected()) {
            frutasRotacion.setSelected(false);
        } else {
            frutasRotacion.setSelected(true);
        }
        imagenCarta = "fruit";
        barajaCategoriaActual = baraja2;
    }

    @FXML
    private void barajaRotacion3(MouseEvent event) {
        if (baraja3Rotacion.isSelected()) {
            baraja3Rotacion.setSelected(false);
        } else {
            baraja3Rotacion.setSelected(true);
        }
        imagenCarta = "card";
        barajaCategoriaActual = barajaDefault;
    }

    @FXML
    private void barajaRotacion4(MouseEvent event) {
        if (baraja4Rotacion.isSelected()) {
            baraja4Rotacion.setSelected(false);
        } else {
            baraja4Rotacion.setSelected(true);
        }
        imagenCarta = "fruit";
        barajaCategoriaActual = baraja2;
    }

    protected void defaultParameters() {
        largoBox.setValue(6);
        anchoBox.setValue(4);
        volteoCartaBox.setValue(5);
        exposicionParErrorBox.setValue(1);
        tiempoPartidaBox.setValue(60);
        showCardsTime.setValue(2);
        soundOKBox.setValue("Acierto 1");
        soundFailBox.setValue("Fallo 1");
        soundFlipBox.setValue("Giro 1");
        normal.setSelected(true);
        limiteChekbox.setSelected(true);

    }

    protected void setearImagenCartas() {
        try {
            
            Image image = new Image(this.getClass().getResource("/images/card1.png").toURI().toString());
            normal1.setImage(image);
            categ1.setImage(image);
            image = new Image(this.getClass().getResource("/images/card2.png").toURI().toString());
            normal3.setImage(image);
            categ3.setImage(image);
            image = new Image(this.getClass().getResource("/images/fruit1.png").toURI().toString());
            normal2.setImage(image);
            categ2.setImage(image);
            image = new Image(this.getClass().getResource("/images/fruit2.png").toURI().toString());
            normal4.setImage(image);
            categ4.setImage(image);
        } catch (URISyntaxException use) {
            use.printStackTrace();
        }

    }

}
