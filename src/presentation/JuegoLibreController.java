/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.IOException;
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

/**
 * FXML Controller class
 *
 * @author Dani
 */
/**
 *
 * @author Dani
 */
public abstract class JuegoLibreController extends JuegoController {
    
    //Objeto configuración (parámetros default si es la primera vez que se crea)
    public Configuracion parametros = Configuracion.getInstance();
  
    /**
     * Initializes the controller class.
     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // Loads parameters from config class
//        recibirParametros();
//        puntuacion = new Puntuacion(0);
//        setUpPauseMenuAccess();
//        setUpPairSelection();
//        setTimers(duracionPartida, duracionTurno);
//        
//        configurarTablero();
//        setAnimation();
//    }
    
    @Override
    public void setTimers(int roundDuration, int turnDuration){
        if(parametros.isLimitePartida()) super.setTimers(roundDuration, turnDuration);
    }
    
    /**
     * Configura las opciones del tablero
     */
    @Override
    public void configurarTablero(){
        Baraja baraja = duplicarCartas(parametros.getBarajaNormal());
        //configurarTablero(copiaBaraja(parametros.getBarajaNormal()));
        configurarTablero(copiaBaraja(baraja));
    }
    
    public void configurarTablero(Baraja barajaElegida){
        barajaElegida = duplicarCartas(barajaElegida);
        // Add event handlers
        for(Carta carta : barajaElegida){
            System.out.println(carta);
            carta.addEventHandler(MouseEvent.MOUSE_CLICKED, clickPairEventHandler);
        }   
        // initialize tablero
        tablero.setFilas(anchuraTablero);
        tablero.setColumnas(longitudTablero);

        tablero.setBaraja(barajaElegida.getCartas());
        if(parametros.isMostrarCartasInicio()){
            tablero.girarTodasCartas();
            mostrarCartasPrincipio();
        }
        tablero.barajarTablero();
        System.out.println(tablero.getChildren().size());
    }
    
    /**
     * Returns a copy of barajaOriginal with new instances of cards inside of 
     * it.
     * @param barajaOriginal
     * @return  nuevaCopiaBaraja baraja con nuevas instancias de todos los objetos 
          que contiene
     */
    public Baraja copiaBaraja(Baraja barajaOriginal){
//        Baraja nuevaCopiaBaraja = new Baraja(barajaOriginal.getNombre(), 
//                barajaOriginal.getImagenReverso());
//        nuevaCopiaBaraja.setCategorias(barajaOriginal.getCategorias());
        Baraja nuevaCopiaBaraja = new Baraja();
        nuevaCopiaBaraja.setPathImagenReverso(barajaOriginal.getPathImagenReverso());
        nuevaCopiaBaraja.setNombre(barajaOriginal.getNombre());
        nuevaCopiaBaraja.setCategorias(barajaOriginal.getCategorias());
         

        for(Carta cartaOriginal : barajaOriginal){
            Carta nuevaCarta = new Carta(cartaOriginal.getCartaID(),
                    cartaOriginal.getImagenCarta(), cartaOriginal.getImagenBaraja(),
                    cartaOriginal.getCategoria());
            nuevaCopiaBaraja.añadirCarta(nuevaCarta);
        }
        System.out.println(nuevaCopiaBaraja + " buenas tardes");
        return nuevaCopiaBaraja;
    }

        
    public Baraja duplicarCartas(Baraja baraja){
        Baraja barajaDuplicada = new Baraja();
        barajaDuplicada.setNombre(baraja.getNombre());
        barajaDuplicada.setCategorias(baraja.getCategorias());
        barajaDuplicada.setPathImagenReverso(baraja.getPathImagenReverso());
        for(Carta carta : baraja) {
            Carta cartaDuplicada = new Carta();
            cartaDuplicada.setcartaID(carta.getCartaID());
            
            barajaDuplicada.añadirCarta(carta);
            barajaDuplicada.añadirCarta(carta);
        }
        System.out.println(barajaDuplicada + "  duplacacion");
        return barajaDuplicada;
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
        if(audio.isPlaying()) audio.stop();
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
    protected void recibirParametros(){
        
        longitudTablero = parametros.getLarguraTablero();
        anchuraTablero = parametros.getAnchuraTablero();
        duracionPartida = parametros.getTiempoPartida();
        duracionTurno = parametros.getTiempoTurno();
        turnDelay = parametros.getTiempoVerError();
        
        audioFail = new AudioClip(this.getClass().getResource(parametros.getSonidoFallo()).toString());
        audioOK = new AudioClip(this.getClass().getResource(parametros.getSonidoCorrecto()).toString());
        audioFlip = new AudioClip(this.getClass().getResource(parametros.getSonidoGiro()).toString());
        cancion = parametros.getCancionPartida();
        if (!parametros.isSinMusica()) {
            setAudio(cancion);
            audio.play(0.3);
        }
        puntuacion = new Puntuacion(0);
      
    }
    
    @Override
    protected void defaultData(){
        longitudTablero = Configuracion.LARGURA_TABLERO_DEFAULT;
        anchuraTablero = Configuracion.ANCHURA_TABLERO_DEFAULT;
        duracionPartida = Configuracion.TIEMPO_PARTIDA_DEFAULT;
        duracionTurno = Configuracion.TIEMPO_TURNO_DEFAULT;
        turnDelay = Configuracion.TIEMPO_VER_ERROR_DEFAULT;
        audioFail = new AudioClip(this.getClass().getResource(Configuracion.SONIDO_FALLO_DEFAULT).toString());
        audioOK = new AudioClip(this.getClass().getResource(Configuracion.SONIDO_CORRECTO_DEFAULT).toString());
        audioFlip = new AudioClip(this.getClass().getResource(Configuracion.SONIDO_GIRO_DEFAULT).toString());
        cancion = Configuracion.CANCION_PARTIDA_DEFAULT;
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
