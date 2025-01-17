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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import logic.Baraja;
import logic.Categoria;
import logic.Coleccion;
import logic.Configuracion;
import logic.JAXBResolver;

/**
 * FXML Controller class
 *
 * @author Jesús Yoel
 */
public class ParametrosPartidaController implements Initializable {

    private ImageView normal1;
    private ImageView normal2;
    private ImageView normal3;
    private ImageView normal4;
    private ImageView categ1;
    private ImageView categ2;
    private ImageView categ3;
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
    private ComboBox<Double> exposicionParErrorBox;
    @FXML
    private ComboBox<Integer> tiempoPartidaBox;
    @FXML
    private CheckBox limiteChekbox;
    @FXML
    private CheckBox showCardsBox;
    @FXML
    private ComboBox<Integer> showCardsTime;
    @FXML
    private ComboBox<Baraja> barajasBox;
    @FXML
    private Label nCategorias;
    @FXML
    private Label categoriasLabel;

    // Singleton instance to configure parameters
    Configuracion parametros = Configuracion.getInstance();

    protected AudioClip audio = null;

    private Stage parentStage;

    //Música de la Partida   
    protected List<String> gameSongList = new ArrayList<>();
    public static String cancionActual;
    public static boolean sinMusica = false;

    //Parámetro de la Partida
    //Parámetros
    
    protected List<Integer> tamañoTablero = new ArrayList<>();
    
    //Parámetros que actualizarán los datos de la siguiente partida
    
    public static int nuevaLargura = Configuracion.LARGURA_TABLERO_DEFAULT;
    public static int nuevaAnchura = Configuracion.ANCHURA_TABLERO_DEFAULT;
    public static int nuevoTiempoTurno = Configuracion.TIEMPO_TURNO_DEFAULT;
    public static int nuevoTiempoPartida = Configuracion.TIEMPO_PARTIDA_DEFAULT;
    public static double nuevoTiempoError = Configuracion.TIEMPO_VER_ERROR_DEFAULT;

    //Tipo de Tablero 
    //
    //Efectos
    protected List<String> sonidos = new ArrayList<>();
    public static int tiempoMostrarCartas = 2;
    //Variables que setearán los sonidos en la partida
    public static String sonidoActualAcierto = "/music/correct.mp3";
    public static String sonidoActualFallo = "/music/fail.mp3";
    public static String sonidoActualGiro = "/music/flip.wav";

    //Barajas
    public List<Baraja> listaBarajas;
    public static Baraja barajaNormalActual;
    public static Baraja barajaCategoriaActual;
    private Baraja barajaActual = parametros.getBarajaNormal();
    public static String imagenCarta = "fruit";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populates interfaces with latest parameters
        loadCurrentParameters();

        //Barajas
        barajaNormalActual = parametros.getBarajaNormal();
        barajaCategoriaActual = parametros.getBarajaCategoria();

        populateFields();

        //Música de la partida
        ObservableList<String> itemsSong = FXCollections.observableArrayList(gameSongList);
        desplegableMusica.setItems(itemsSong);
        cancionActual = parametros.getCancionPartida();

        setAudio(cancionActual);
        audio.stop();

        barajasBox.setCellFactory(new Callback<ListView<Baraja>, ListCell<Baraja>>() {
            @Override
            public ListCell<Baraja> call(ListView<Baraja> p) {
                return new ListCell<Baraja>() {

                    @Override
                    protected void updateItem(Baraja item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getNombre());
                        }
                    }
                };
            }
        });
    }

    /**
     * Fills in all selectable options
     */
    public void populateFields() {
        //Parámetros de la Partida (tamaño tablero)
        largoBox.setItems(FXCollections.observableList(new ArrayList(Arrays.asList(2, 4, 6, 8))));
        anchoBox.setItems(FXCollections.observableList(new ArrayList(Arrays.asList(2, 4, 6))));
        //Parámetros de Partida (tiempo volteo carta)
        volteoCartaBox.setItems(FXCollections.observableList(new ArrayList(Arrays.asList(5, 10, 15))));
        //Parámetros de Partida (tiempo pareja errónea)
        exposicionParErrorBox.setItems(FXCollections.observableList(new ArrayList(Arrays.asList(0.125, 0.250, 0.500, 1.0, 2.0))));
        //Parámetros de Partida (tiempo de partida)
        tiempoPartidaBox.setItems(FXCollections.observableList(new ArrayList(Arrays.asList(45, 60, 90, 120))));
        //Efecto de mostrar las cartas al principio
        showCardsTime.setItems(FXCollections.observableList(new ArrayList(Arrays.asList(2, 3, 4, 5))));
        //Parámetros de Partida (tipo de tablero)
        normal.setSelected(true);
        //Barajas
        setBarajas();
        //Efectos de Partida
        if(!parametros.isLimitePartida()){
            volteoCartaBox.setDisable(true);
            tiempoPartidaBox.setDisable(true);
        }
        //Sonidos
        soundOKBox.setItems(FXCollections.observableList(Arrays.asList("Acierto 1", "Acierto 2", "Acierto 3")));
        sonidoActualAcierto = parametros.getSonidoCorrecto();
        soundFlipBox.setItems(FXCollections.observableList(Arrays.asList("Giro 1", "Giro 2", "Giro 3")));
        sonidoActualGiro = parametros.getSonidoGiro();
        soundFailBox.setItems(FXCollections.observableList(Arrays.asList("Fallo 1", "Fallo 2", "Fallo 3")));
        sonidoActualFallo = parametros.getSonidoFallo();
        gameSongList.addAll(Arrays.asList("Sin Música", "Golf It Music", "Zelda Remix", "Force Theme Star Wars", "Chill Music to play"));
    }

    @FXML
    private void saveAction(ActionEvent event) {
        if (audio.isPlaying()) {
            audio.stop();
        }
//    private boolean sinMusica;
        parametros.setLarguraTablero(largoBox.getValue());
        parametros.setAnchuraTablero(anchoBox.getValue());

        //Parámetros de partida
            parametros.setLimitePartida(limiteChekbox.isSelected());

            parametros.setCancionPartida(cancionActual);
            parametros.setSonidoCorrecto(sonidoActualAcierto);
            parametros.setSonidoFallo(sonidoActualFallo);
            parametros.setSonidoGiro(sonidoActualGiro);

            parametros.setMostrarCartasInicio(showCardsBox.isSelected());
            if (showCardsBox.isSelected()) {
                parametros.setTiempoCartasInicio(showCardsTime.getValue());
            }
            parametros.setLimitePartida(limiteChekbox.isSelected());
            if (limiteChekbox.isSelected()) {
                parametros.setTiempoTurno(volteoCartaBox.getValue());
                parametros.setTiempoPartida(tiempoPartidaBox.getValue());
            }
            parametros.setTiempoVerError(exposicionParErrorBox.getValue());

            //Barajas
            parametros.setBarajaNormal(barajaNormalActual);
            parametros.setBarajaCategoria(barajaCategoriaActual);
            parametros.setCartaPartida(imagenCarta);
            parametros.setBarajaNormal(barajaActual);
            parametros.setBarajaCategoria(barajaActual);

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();


    }

    //Botones de restablecer a Valores Predeterminados
    @FXML
    private void setDefaultBarajas(ActionEvent event) {
        barajasBox.setValue(parametros.getBarajaNormal());
        barajaActual = parametros.getBarajaNormal();
        nCategorias.setText(String.valueOf(barajaActual.getCategorias().size()));
        categoriasLabel.setText(barajaActual.getCategorias().toString());
        
    }

    @FXML
    private void setDefaultParametros(ActionEvent event) {
        largoBox.setValue(Configuracion.LARGURA_TABLERO_DEFAULT);
        anchoBox.setValue(Configuracion.ANCHURA_TABLERO_DEFAULT);
        volteoCartaBox.setValue(Configuracion.TIEMPO_TURNO_DEFAULT);
        exposicionParErrorBox.setValue(Configuracion.TIEMPO_VER_ERROR_DEFAULT);
        tiempoPartidaBox.setValue(Configuracion.TIEMPO_PARTIDA_DEFAULT);
        normal.setSelected(true);
    }

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
        if (this.sonidoActualGiro != null && !"".equals(this.sonidoActualGiro)) {
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
        switch (soundFlipBox.getSelectionModel().getSelectedIndex()) {
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

    public void setAudio(String sonido) {
        audio = new AudioClip(this.getClass().getResource(sonido).toString());
    }

    @FXML
    private void elegirBaraja(ActionEvent event) /*throws URISyntaxException*/ {
        barajaActual = listaBarajas.get(barajasBox.getSelectionModel().getSelectedIndex());
        nCategorias.setText(String.valueOf(barajaActual.getCategorias().size()));
        categoriasLabel.setText(barajaActual.getCategorias().toString());
    }

    private void setBarajas() {
        listaBarajas = leerXML();
        listaBarajas.add(parametros.generarBaraja(parametros.getAnchuraTablero() * parametros.getLarguraTablero(), "fruit", new Categoria("FRUTAS"), "Baraja Default"));
        listaBarajas.add(parametros.generarBaraja(parametros.getAnchuraTablero() * parametros.getLarguraTablero(), "card", new Categoria("PAJAROS"), "Baraja Default 2"));
        listaBarajas.add(parametros.generarBarajaCategoria(parametros.getAnchuraTablero() * parametros.getLarguraTablero(), "", "Baraja Categoria Default"));
        List<String> nombreBarajas = new ArrayList<>();
        for (int i = 0; i < listaBarajas.size(); i++) {
            nombreBarajas.add(listaBarajas.get(i).getNombre());
        }
        barajasBox.setItems(FXCollections.observableList(listaBarajas));
    }

    public List<Baraja> leerXML() {
        Coleccion coleccion = new Coleccion();
        try {
            JAXBResolver dr = new JAXBResolver();
            JAXBContext context = dr.getCtx();

            Unmarshaller unmarshaller = context.createUnmarshaller();
            coleccion = (Coleccion) unmarshaller.unmarshal(new File(System.getProperty("user.home") + File.separator + "coleccion.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(ParametrosPartidaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coleccion.leerBarajas();
    }

    protected void loadCurrentParameters() {
        largoBox.setValue(parametros.getLarguraTablero());
        anchoBox.setValue(parametros.getAnchuraTablero());
        volteoCartaBox.setValue(parametros.getTiempoTurno());
        exposicionParErrorBox.setValue(parametros.getTiempoVerError());
        tiempoPartidaBox.setValue(parametros.getTiempoPartida());
        showCardsTime.setValue(parametros.getTiempoCartasInicio());
        soundOKBox.setValue(comprobarSonido("Acierto"));
        soundFailBox.setValue(comprobarSonido("Fallo"));
        soundFlipBox.setValue(comprobarSonido("Giro"));
        desplegableMusica.setValue(comprobarMusica());
        normal.setSelected(true);
        limiteChekbox.setSelected(parametros.isLimitePartida());
        barajasBox.setValue(parametros.getBarajaNormal());
        nCategorias.setText(String.valueOf(parametros.getBarajaNormal().getCategorias().size()));
        categoriasLabel.setText(parametros.getBarajaNormal().getCategorias().toString());
        barajasBox.setValue(parametros.getBarajaNormal());
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
        }

    }

    //Método que compureba los efectos que hay instanciados actualemnte en la clase Configuración
    protected String comprobarSonido(String sonido) {
        if ("Acierto".equals(sonido)) {
            if (null == parametros.getSonidoCorrecto()) {
                return sonido = sonido + " " + 3;
            } else switch (parametros.getSonidoCorrecto()) {
                case "/music/correct.mp3":
                    return sonido = sonido + " " + 1;
                case "/music/correct2.mp3":
                    return sonido = sonido + " " + 2;
                default:
                    return sonido = sonido + " " + 3;
            }
        } else if ("Fallo".equals(sonido)) {
            if (null == parametros.getSonidoFallo()) {
                return sonido = sonido + " " + 3;
            } else switch (parametros.getSonidoFallo()) {
                case "/music/fail.mp3":
                    return sonido = sonido + " " + 1;
                case "/music/fail2.mp3":
                    return sonido = sonido + " " + 2;
                default:
                    return sonido = sonido + " " + 3;
            }
        } else {
            if (null == parametros.getSonidoGiro()) {
                return sonido = sonido + " " + 3;
            } else switch (parametros.getSonidoGiro()) {
                case "/music/flip.wav":
                    return sonido = sonido + " " + 1;
                case "/music/flip2.wav":
                    return sonido = sonido + " " + 2;
                default:
                    return sonido = sonido + " " + 3;
            }
        }
    }

    //Método para comprobar qué canción es la que está configurada actualmente en Configuración
    protected String comprobarMusica() {
        if ("/music/Cancion1.mp3".equals(parametros.getCancionPartida())) {
            return "Golf It Music";
        } else if ("/music/Cancion2.mp3".equals(parametros.getCancionPartida())) {
            return "Zelda Remix";
        } else if ("/music/Cancion3.mp3".equals(parametros.getCancionPartida())) {
            return "Force Theme Star Wars";
        } else if ("/music/Cancion4.mp3".equals(parametros.getCancionPartida())) {
            return "Chill Music to play";
        } else if (parametros.isSinMusica()) {
            return "Sin Música";
        } else {
            return "Seleccione música para la partida";
        }
    }

     void initWindow(Stage stage) {
        this.parentStage = stage;
    }
}
