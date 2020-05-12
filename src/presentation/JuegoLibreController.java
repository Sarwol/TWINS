/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Baraja;
import logic.Configuracion;
import logic.Puntuacion;
import static presentation.ParametrosPartidaController.*;
//import static presentation.PausaController.pauseMusic;

/**
 * FXML Controller class
 *
 * @author Dani
 */
/**
 *
 * @author Dani
 */
public class JuegoLibreController extends JuegoController {

    //public static int LONGITUD_TABLERO = 6;
    //public static int ANCHURA_TABLERO = 4;
    //public static int TURN_DELAY = 500;
    public static int NUM_CATEGORIAS = 2;
    //public static int DURACION_PARTIDA = 60;
    //public static int DURACION_TURNO = 5;
    public static int S_CARTAS_INICIO = 2;

    //protected static String modo = VentanaJuegoLibreController.mode;
    //modo = VentanaJuegoLibreController.mode;

    //private Stage winStage;
    
    //protected static String cancion;
//    @FXML
//    protected Tablero tablero;
//    // tiempoPartida restante de la partida actual
//    @FXML
//    protected Label tiempoPartida;
//    // tiempoPartida restante del turno actual
//    @FXML
//    protected Label tiempoTurno;
//    // Label de puntuación
//    @FXML
//    protected Label punt;
//    // Actualiza el tiempoPartida restante de partida
//    protected Timeline countdownPartida;
//    // Actualiza el tiempoPartida restante del turno
//    protected Timeline countdownTurno;
//    // se usa para la cuenta atrás de la partida
//    protected int tiempoActualPartida;
//    // se usa para la cuenta atrás del turno
//    protected int tiempoActualTurno;
//    // Se usa para guardar las dos cartas seleccionadas
//    protected List<Carta> parSelec;
//    // Se usa para guardar las dos cartas seleccionadas
//    protected ObservableList<Carta> parSeleccionado;
//    // Guarda un booleano para ocultar el tablero
//    protected static List<Boolean> pauseList;
//    // Guarda un booleano para ocultar el tablero
//    protected static ObservableList<Boolean> observPauseList;
//    // Lleva el registro de la puntuación
//    protected Puntuacion puntuacion;
//    // La primera carta que se selecciona
//    protected Carta carta1;
//    // La segunda carta que se selecciona
//    protected Carta carta2;
//    // Para reproducir la canción
//    protected AudioClip audio = null;
//    // Animación de rotación
//    public RotateTransition rotateAnimation;
//    //Audio de fallo de carta
//    public static AudioClip audioFail;
//    //Audio de Acierto
//    public static AudioClip audioOK;
//    //Audio de Giro
//    public static AudioClip audioFlip;
//    //Baraja que contendrá el tablero de la partida
//    public static Baraja barajaActual;
//    //Baraja que contendrá el tablero de la partida por Categoria
//    public static Baraja barajaCategoria;
    //String para comprobar si se ha activado el límite de tiempo de la partida
    public static String limiteActivado;
    public boolean mostrarCartasOn = false;
    //Baraja default que, si la de Parámetros es null, instanciará el tablero
    Baraja nuevaBaraja;
    //Objeto configuración con parámetros obtenidos de la interfaz de Parámetros
    public static Configuracion parametros = new Configuracion("/music/Cancion1.mp3","/music/correct.mp3","/music/fail.mp3","/music/flip.wav",4,6,5,60,1,2,true,"fruit",false,false);
    //Objeto configuración con parámetros default
    Configuracion defaultConfig = new Configuracion("/music/Cancion1.mp3", "/music/correct.mp3","/music/fail.mp3","/music/flip.wav",4,6,5,60,2,2,true,"fruit",false,false);
    //String que marcará que baraja se inicia en partida estándar y partida por carta
    String cartaBaraja;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(parametros == null)
            defaultData(); 
        else recibirParametros();
        
        cancion = cancionActual;
        if (cancion == null) {
            cancion = "/music/Cancion1.mp3";
        }
        if (!sinMusica) {
            setAudio(cancion);
            audio.play(0.3);
        }
        puntuacion = new Puntuacion(0);
        setUpPauseMenuAccess();
        setUpPairSelection();
        
       
        if(parametros != null && parametros.isLimitePartida())
            setTimers(DURACION_PARTIDA, DURACION_TURNO);
        
        configurarTablero();
        setAnimation();
        
    }
    
    /**
     * Configura las opciones del tablero
     */
    @Override
    public void configurarTablero(){
        if(parametros == null) {
             nuevaBaraja = generarBaraja(LONGITUD_TABLERO * ANCHURA_TABLERO, defaultConfig.getCartaPartida(), "Baraja Default");
             barajaActual = nuevaBaraja;
           } else {
             barajaActual = generarBaraja(LONGITUD_TABLERO * ANCHURA_TABLERO, parametros.getCartaPartida() , "Baraja Default");
             barajaCategoria = barajaCategoriaActual;
        }
        // initialize tablero
        tablero.setFilas(ANCHURA_TABLERO);
        tablero.setColumnas(LONGITUD_TABLERO);
        tablero.setBaraja(barajaActual.getCartas());
        if(mostrarCartasOn){
            tablero.girarTodasCartas();
            mostrarCartasPrincipio();
        }
        tablero.barajarTablero();
    }
//    @Override
//    public void setUpPauseMenuAccess(){
//        pauseList = new ArrayList<Boolean>();
//        pauseList.add(Boolean.FALSE);
//        observPauseList = FXCollections.observableList(pauseList);
//        
//        //listener para activar de nuevo el tablero después de Pausa
//        observPauseList.addListener(new ListChangeListener() {
//            @Override
//            public void onChanged(ListChangeListener.Change change) {
//                if (observPauseList.get(0)) {
//                    tablero.setVisible(true);
//                    // La música de la pausa
//                    if (pauseMusic.isPlaying()) {
//                        pauseMusic.stop();
//                    }
//                }
//            }
//        });
//    }


    /**
     * Creates a new thread that will turn the cards back around.
     */
    public void mostrarCartasPrincipio() {
        Platform.runLater(() -> {
            try {
                Thread.sleep(S_CARTAS_INICIO*1000);  
                tablero.girarTodasCartas();  
            } catch (InterruptedException ex) {}
        });
    }
    
    @Override
    public void saltarAVictoria(Puntuacion punt, int temp, String m) throws IOException {
        audio.stop();
        if(limiteActivado != null && limiteActivado != ""){
            countdownPartida.stop();
            countdownTurno.stop();
        }
        tablero.setDisable(true);
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Victoria.fxml"));
        Parent root = (Parent) myLoader.load();
        VictoriaController victoriaController = myLoader.<VictoriaController>getController();
        Stage victoriaWinStage = new Stage();
        victoriaController.initVictoriaWindow(victoriaWinStage, punt, temp, m);
        Scene scene = new Scene(root);
        victoriaWinStage.setScene(scene);
        victoriaWinStage.initModality(Modality.APPLICATION_MODAL);
        victoriaWinStage.show();
        //stopAudio(cancion);
        Stage thisStage = (Stage) tablero.getScene().getWindow();
        thisStage.close();
    }

    public void saltarADerrota(String m) throws IOException {
        audio.stop();
        tablero.setDisable(true);
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Derrota.fxml"));
        Parent root = (Parent) myLoader.load();
        DerrotaController derrotaController = myLoader.<DerrotaController>getController();
        Stage derrotaWinStage = new Stage();
        derrotaController.initDerrotaWindow(derrotaWinStage, m);
        Scene scene = new Scene(root);
        derrotaWinStage.setScene(scene);
        derrotaWinStage.initModality(Modality.APPLICATION_MODAL);
        derrotaWinStage.show();
        //stopAudio(cancion);
        Stage thisStage = (Stage) tablero.getScene().getWindow();
        thisStage.close();
    }
    
    protected void recibirParametros(){
        LONGITUD_TABLERO = nuevaLargura;
        ANCHURA_TABLERO = nuevaAnchura;
        DURACION_PARTIDA = nuevoTiempoPartida;
        DURACION_TURNO = nuevoTiempoTurno;
        TURN_DELAY = nuevoTiempoError*1000;
        cartaBaraja = parametros.getCartaPartida();
        if(parametros.isMostrarCartasInicio()){
            mostrarCartasOn = true;
            S_CARTAS_INICIO = parametros.getTiempoCartasInicio();
        }
        try{ 
        audioFail = new AudioClip(this.getClass().getResource(sonidoActualFallo).toString());
        audioOK = new AudioClip(this.getClass().getResource(sonidoActualAcierto).toString());
        audioFlip = new AudioClip(this.getClass().getResource(sonidoActualGiro).toString());
         } catch(Exception e){
             audioFail = new AudioClip(this.getClass().getResource(defaultConfig.getSonidoFallo()).toString());
             audioOK = new AudioClip(this.getClass().getResource(defaultConfig.getSonidoCorrecto()).toString());
             audioFlip = new AudioClip(this.getClass().getResource(defaultConfig.getSonidoGiro()).toString());
         } 
      
    }
    
    @Override
    protected void defaultData(){
        LONGITUD_TABLERO = defaultConfig.getLarguraTablero();
        ANCHURA_TABLERO = defaultConfig.getAnchuraTablero();
        DURACION_PARTIDA = defaultConfig.getTiempoPartida();
        DURACION_TURNO = defaultConfig.getTiempoTurno();
        TURN_DELAY = defaultConfig.getTiempoVerError();
        audioFail = new AudioClip(this.getClass().getResource(defaultConfig.getSonidoFallo()).toString());
        audioOK = new AudioClip(this.getClass().getResource(defaultConfig.getSonidoCorrecto()).toString());
        audioFlip = new AudioClip(this.getClass().getResource(defaultConfig.getSonidoGiro()).toString());
        cartaBaraja = defaultConfig.getCartaPartida();
        
        
    }
    
    /**
     * Pass the parameters needed when starting this controller
     * @param stage a reference to this stage
     * @param modoPartida this game mode
     */
    
    void initWindow(Stage stage, String modoPartida ) {
        super.initWindow(stage);
        modo = modoPartida;
    }
}
