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
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Baraja;
import logic.Carta;
import logic.Configuracion;
import logic.Puntuacion;
//import static presentation.ParametrosPartidaController.*;
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
    //public static int turnDelay = 500;
    public static int NUM_CATEGORIAS = 2;
    //public static int DURACION_PARTIDA = 60;
    //public static int DURACION_TURNO = 5;
//    public static int S_CARTAS_INICIO = 2;

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
//    public RotateTransition turnAnimation;
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
//    public boolean mostrarCartasOn = false;
    //Baraja default que, si la de Parámetros es null, instanciará el tablero
    Baraja nuevaBaraja;
    //Objeto configuración (parámetros default si es la primera vez que se crea)
    public  Configuracion parametros = Configuracion.getInstance();
    //String que marcará que baraja se inicia en partida estándar y partida por carta
//    String cartaBaraja;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       System.out.println("Initializing parameters");
        recibirParametros();
        
        
//        if (cancion == null) {
//            cancion = "/music/Cancion1.mp3";
//        }
        if (!parametros.isSinMusica()) {
            setAudio(cancion);
            audio.play(0.3);
        }
        puntuacion = new Puntuacion(0);
        setUpPauseMenuAccess();
        setUpPairSelection();
        
       
        if(parametros.isLimitePartida())
            setTimers(DURACION_PARTIDA, DURACION_TURNO);
        
        configurarTablero();
        setAnimation();
        
    }
    
    /**
     * Configura las opciones del tablero
     */
    @Override
    public void configurarTablero(){
        System.out.println("Running CONFIGURARTABLERO from JUEGOLIBRECONTROLLER");
        
        barajaActual = copiaBaraja(parametros.getBarajaNormal());
        System.out.println("BARAJA ACTUAL: " + barajaActual);
//        barajaCategoria = parametros.getBarajaCategoria();
        // Add event handlers
        for(Carta carta : barajaActual){
            carta.addEventHandler(MouseEvent.MOUSE_CLICKED, clickPairEventHandler);
        }
//        for(Carta carta : barajaCategoria){
//            carta.addEventHandler(MouseEvent.MOUSE_CLICKED, clickPairEventHandler);
//        }
//        
        // initialize tablero
        tablero.setFilas(ANCHURA_TABLERO);
        tablero.setColumnas(LONGITUD_TABLERO);
        tablero.setBaraja(barajaActual.getCartas());
        if(parametros.isMostrarCartasInicio()){
            tablero.girarTodasCartas();
            mostrarCartasPrincipio();
        }
        tablero.barajarTablero();
    }
    
    
    /**
     * Returns a copy of barajaOriginal with new instances of cards inside of 
     * it.
     * @param barajaOriginal
     * @return  nuevaBaraja baraja con nuevas instancias de todos los objetos 
     *          que contiene
     */
    public Baraja copiaBaraja(Baraja barajaOriginal){
        Baraja nuevaBaraja = new Baraja(barajaOriginal.getNombre(), 
                barajaOriginal.getImagenReverso());
        nuevaBaraja.setCategorias(barajaOriginal.getCategorias());
        
        for(Carta cartaOriginal : barajaOriginal){
            Carta nuevaCarta = new Carta(cartaOriginal.getCartaID(),
                    cartaOriginal.getImagenCarta(), cartaOriginal.getImagenBaraja(),
                    cartaOriginal.getCategoria());
            nuevaBaraja.añadirCarta(nuevaCarta);
        }
        return nuevaBaraja;
    }

    /**
     * Creates a new thread that will turn the cards back around.
     */
    public void mostrarCartasPrincipio() {
        Platform.runLater(() -> {
            try {
                Thread.sleep(Math.round(turnDelay * 1000));  
                tablero.girarTodasCartas();  
            } catch (InterruptedException ex) {ex.printStackTrace();}
        });
    }
    
    @Override
    public void saltarAVictoria(Puntuacion punt, int temp, String m) throws IOException {
        audio.stop();
        if(parametros.isLimitePartida()){
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

    @Override
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
        
        LONGITUD_TABLERO = parametros.getLarguraTablero();
        ANCHURA_TABLERO = parametros.getAnchuraTablero();
        DURACION_PARTIDA = parametros.getTiempoPartida();
        DURACION_TURNO = parametros.getTiempoTurno();
        turnDelay = parametros.getTiempoVerError();
//        cartaBaraja = parametros.getCartaPartida();
        if(parametros.isMostrarCartasInicio()){
//            mostrarCartasOn = parametros.isMostrarCartasInicio();
//            S_CARTAS_INICIO = parametros.getTiempoCartasInicio();
        }
        audioFail = new AudioClip(this.getClass().getResource(parametros.getSonidoFallo()).toString());
        audioOK = new AudioClip(this.getClass().getResource(parametros.getSonidoCorrecto()).toString());
        audioFlip = new AudioClip(this.getClass().getResource(parametros.getSonidoGiro()).toString());
        cancion = parametros.getCancionPartida();
      
    }
    
    @Override
    protected void defaultData(){
        LONGITUD_TABLERO = Configuracion.LARGURA_TABLERO_DEFAULT;
        ANCHURA_TABLERO = Configuracion.ANCHURA_TABLERO_DEFAULT;
        DURACION_PARTIDA = Configuracion.TIEMPO_PARTIDA_DEFAULT;
        DURACION_TURNO = Configuracion.TIEMPO_TURNO_DEFAULT;
        turnDelay = Configuracion.TIEMPO_VER_ERROR_DEFAULT;
        audioFail = new AudioClip(this.getClass().getResource(Configuracion.SONIDO_FALLO_DEFAULT).toString());
        audioOK = new AudioClip(this.getClass().getResource(Configuracion.SONIDO_CORRECTO_DEFAULT).toString());
        audioFlip = new AudioClip(this.getClass().getResource(Configuracion.SONIDO_GIRO_DEFAULT).toString());
//        cartaBaraja = Configuracion.CARTA_PARTIDA_DEFAULT;   
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
