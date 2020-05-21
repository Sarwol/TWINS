/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Carta;
import logic.Puntuacion;
import static presentation.JuegoController.anchuraTablero;
import static presentation.JuegoController.audioFail;
import static presentation.JuegoController.audioFlip;
import static presentation.JuegoController.audioOK;
import static presentation.JuegoController.barajaActual;
import static presentation.JuegoController.cancion;
import static presentation.JuegoController.duracionPartida;
import static presentation.JuegoController.duracionTurno;
import static presentation.JuegoController.longitudTablero;
import static presentation.JuegoController.modo;
import static presentation.JuegoController.turnDelay;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class JuegoMultijugadorController extends JuegoController {

    // Used to signal what player must play
    @FXML
    private Label puntosJugador1;
    @FXML
    private Label puntosJugador2;
    // Used to display score
    @FXML
    private Label puntJugador1;
    @FXML
    private Label puntJugador2;
    // Keeps track of current score 
    private Puntuacion puntuacionJugador1;
    private Puntuacion puntuacionJugador2;

    /**
     * Initializes the controller class.
     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//        // loads the default parameters
//        recibirParametros();                        // abstract
//        // initial score
//        puntuacion = new Puntuacion(0);             // same for all game modes
//        // configures access to the pause menu
//        setUpPauseMenuAccess();                     // same for all game modes
//        // Configures pair selection mechanics
//        setUpPairSelection();                       // same for all game modes
//        // configures countdowns
//        setTimers(duracionPartida, duracionTurno);  // same for all game modes
//        // initialize tablero
//        configurarTablero();                        // abstract
//        // loads the card turning animation
//        setAnimation();
//    }
//    
    @Override
    public void configurarTablero() {
        barajaActual = generarBaraja(longitudTablero * anchuraTablero, "fruit", "Baraja Default");
        tablero.setFilas(anchuraTablero);
        tablero.setColumnas(longitudTablero);

        for (Carta carta : barajaActual) {
            carta.addEventHandler(MouseEvent.MOUSE_CLICKED, clickPairEventHandler);
        }
        tablero.setBaraja(barajaActual.getCartas());
        tablero.barajarTablero();
        System.out.println("FINISHED SETTING DECK");
    }

    @Override
    protected void defaultData() {
        longitudTablero = 6;
        anchuraTablero = 4;
        duracionPartida = 120;
        duracionTurno = 10;
        turnDelay = 0.5;
        cancion = "/music/Cancion1.mp3";
        setAudio(cancion);
        audio.play(0.3);
        audioFail = new AudioClip(this.getClass().getResource("/music/fail.mp3").toString());
        audioOK = new AudioClip(this.getClass().getResource("/music/correct.mp3").toString());
        audioFlip = new AudioClip(this.getClass().getResource("/music/flip.wav").toString());
    }

    @Override
    protected void recibirParametros() {
        defaultData();
        puntuacionJugador1 = new Puntuacion(0);
        puntuacionJugador2 = new Puntuacion(0);
        puntuacion = puntuacionJugador1;
        puntosJugador1.setStyle("-fx-font-weight: bold;");
        punt = puntJugador1;
        System.out.println("RECEIVING PARAMETERS TO JUEGOMULTIJUGADOR");
    }

    @Override
    public void comprobarCartas() {
        if (parSeleccionado.size() == 2) {
            carta1 = parSeleccionado.get(0);
            carta2 = parSeleccionado.get(1);

            // Debugging purposes
            parSeleccionado.forEach((carta) -> {
                System.out.print(carta + " ");
            });
            System.out.println();
            
            if (sonIguales(carta1, carta2)) {
                carta1.setDisable(true);
                carta2.setDisable(true);
                puntuacion.sumarPuntos();
                audioOK.play(0.1);
                punt.setText(puntuacion.getPuntos() + "");
                //setTimer(duracionTurno, tiempoTurno);
            } else {
                puntuacion.restarPuntos();
                punt.setText(puntuacion.getPuntos() + "");
                // Wait a specified amount of time before turning the cards back around
                setDelayedCardTurn();
                audioFail.play(0.1);
            }
            // Reset turn countdown
            resetTurnCountdown();
            switchPlayers();
            // since a new event is generated when we remove an element
            // from the ObservableList, we remove instead from the List
            // to avoid an infinite loop by triggering the listener
            parSelec.remove(0);
            parSelec.remove(0);
            
        }
    }

    @Override
    public void setTimers(int roundDuration, int turnDuration) {
        tiempoActualPartida = roundDuration;
        tiempoActualTurno = turnDuration;
        countdownPartida = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tiempoActualPartida == 0) {
                    countdownPartida.stop();
                    try {
                        saltarADerrota(modo);
                    } catch (IOException e) {
                        System.out.println("No se pudo saltar a la pantalla de derrota");
                        e.printStackTrace();
                    }
                }
                String timeStr = String.format("%02d:%02d", tiempoActualPartida / 60, tiempoActualPartida % 60);
                tiempoPartida.setText(timeStr);
                tiempoActualPartida--;
            }
        }));
        countdownPartida.setCycleCount(Timeline.INDEFINITE);
        countdownPartida.play();
        countdownTurno = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tiempoActualTurno == 0) {
                    puntuacion.restarPuntos();
                    punt.setText(puntuacion.getPuntos() + "");
                    tiempoActualTurno = duracionTurno;
                    switchPlayers();
                    System.out.println("RESETTING TURN TIME");
                }
                tiempoTurno.setText((tiempoActualTurno--) + "");
                System.out.println("DECREASING TURN TIME");

            }
        }));
        countdownTurno.setCycleCount(Timeline.INDEFINITE);
        countdownTurno.play();
    }

    /**
     * Updates score pointer and labels to indicate which player must choose
     * cards
     */
    public void switchPlayers() {
        if (puntuacion.equals(puntuacionJugador1)) {    // player 1 has just played
            puntuacion = puntuacionJugador2;
            punt = puntJugador2;
            puntosJugador1.setStyle("-fx-font-weight: normal;");
            puntosJugador2.setStyle("-fx-font-weight: bold;");

        } else {
            puntuacion = puntuacionJugador1;
            puntosJugador1.setStyle("-fx-font-weight: bold;");
            puntosJugador2.setStyle("-fx-font-weight: normal;");
            punt = puntJugador1;

        }
    }

    @Override
    public void saltarAVictoria(Puntuacion punt, int temp, String m) throws IOException {
        saltarAVictoria(puntuacionJugador1, puntuacionJugador2, temp, m);
//        if (audio.isPlaying()) {
//            audio.stop();
//        }
//
//        countdownPartida.stop();
//        countdownTurno.stop();
//
//        tablero.setDisable(true);
//        
//        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Victoria.fxml"));
//        Parent root = (Parent) myLoader.load();
//        VictoriaController victoriaController = myLoader.<VictoriaController>getController();
//        Stage victoriaWinStage = new Stage();
//        victoriaController.initVictoriaWindow(victoriaWinStage, punt, temp, m);
//        Scene scene = new Scene(root);
//        victoriaWinStage.setScene(scene);
//        victoriaWinStage.initModality(Modality.APPLICATION_MODAL);
//        victoriaWinStage.show();
//        //stopAudio(cancion);
//        Stage thisStage = (Stage) tablero.getScene().getWindow();
//        thisStage.close();
    }
    
    public void saltarAVictoria(Puntuacion puntJ1, Puntuacion puntJ2, int temp, String m){
        if (audio.isPlaying()) {
            audio.stop();
        }

        countdownPartida.stop();
        countdownTurno.stop();

        tablero.setDisable(true);
                
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("VictoriaMultijugador.fxml"));
        Parent root = null;
        try {
            root = (Parent) myLoader.load();
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
        VictoriaMultijugadorController victoriaMultijugadorController = myLoader.<VictoriaMultijugadorController>getController();
        Stage victoriaWinStage = new Stage();
        victoriaMultijugadorController.initVictoriaWindow(victoriaWinStage, puntJ1, puntJ2, temp, m);
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
        this.saltarAVictoria(puntuacionJugador1, puntuacionJugador2, tiempoActualPartida, modo);
    }

    @Override
    void initWindow(Stage winStage) {
        super.initWindow(winStage);
        modo = "JuegoMultijugador.fxml";
        //System.out.println("INIT WINDOW");

    }
}
