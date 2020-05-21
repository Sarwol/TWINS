/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Carta;
import logic.Nivel;
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
public class JuegoNivelesController extends JuegoController {

    // Lista con los diferentes niveles
    private List<Nivel> niveles;
    // ID of the level to be played
    private int currentLevel;
    // Min amount of points required to win
    private int minPoints;
    private Stage winStage;
    // Para activar el siguiente nivel si se gana el anterior
    private Button nextLevel;

    @FXML
    private Label minPointsLabel;

    /**
     * Initializes the controller class.
     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//
//        // TODO
//        //super.initialize(url, rb);
//        // loads the default parameters
//        recibirParametros();
//
//        // initial score
//        puntuacion = new Puntuacion(0);
//        // configures access to the pause menu
//        setUpPauseMenuAccess();
//        // Configures pair selection mechanics
//        setUpPairSelection();
//        // Specify levels to be loaded
//        // configures countdowns
//        setTimers(duracionPartida, duracionTurno);
//        // initialize tablero
//        configurarTablero();
//        // loads the card turning animation
//        setAnimation();
//        // Loading the levels
//        //String [] levels = {"nivel1.ser"};
//        //niveles = loadLevels(levels);
//        //setupLevel(0);
//        // Loads label for minimum required points
//
//    }

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
    }

    /**
     * Updates the level parameters
     *
     * @param levelIndex sets up the level at this index position
     */
    public void setUpLevel(int levelIndex) {
        Nivel nivel = niveles.get(levelIndex);
        System.out.println("LEVEL PARAMETERS: " + nivel);
        anchuraTablero = nivel.getBoardHeight();
        longitudTablero = nivel.getBoardWidth();
        duracionPartida = nivel.getTime();
        minPoints = nivel.getMinPoints();
    }
//    @Override
//    public void configurarTablero(){
//        tablero.setFilas(anchuraTablero);
//        tablero.setColumnas(longitudTablero);
//        tablero.setBaraja(barajaActual.getCartas());
//        tablero.barajarTablero();
//    }

    /**
     * Gets a list with all the specified levels
     *
     * @param levelNames the specified levels
     * @return a list with the specified levels
     */
    public List<Nivel> loadLevels(String[] levelNames) {
        List<Nivel> levels = new ArrayList<>();

        for (String level : levelNames) {
            levels.add(loadLevel(level));
        }
        return levels;
    }

    /**
     * Reads Nivel objects from the filesystem
     *
     * @param fileName
     * @return a level
     */
    private Nivel loadLevel(String fileName) {
        Nivel nivel = null;
        try {
            FileInputStream fileIn = new FileInputStream("." + File.separator + "src" + File.separator + "levels" + File.separator + fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            nivel = (Nivel) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Loaded level:\n" + nivel);
        } catch (IOException i) {
            System.out.println("Unable to read level data");
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }

        return nivel;
    }

    @Override
    public boolean isVictoria() {
        boolean allCardsFound = true;
        ObservableList<Node> cartas = tablero.getChildren();
        for (int i = 0; i < cartas.size(); i++) {
            if ((cartas.get(i) instanceof Carta && cartas.get(i).isDisabled())) {
                allCardsFound = true;
            } else {
                allCardsFound = false;
                return allCardsFound;
            }
        }
        boolean minScoreReached = puntuacion.getPuntos() >= minPoints;
        // If all cards have been found but minPoints is not reached,
        // jump to lose screen
        try {
            if (allCardsFound && !minScoreReached) {
                saltarADerrota(modo);
            }
        } catch (IOException ioe) {
            System.out.println("Couldn't execute saltarADerrota(modo)");
            ioe.printStackTrace();
        }

        return allCardsFound && minScoreReached;
    }

    @Override
    public void saltarAVictoria(Puntuacion punt, int temp, String m) throws IOException {
        audio.stop();

        countdownPartida.stop();
        countdownTurno.stop();

        if (nextLevel != null) {
            nextLevel.setDisable(false);
        }
        tablero.setDisable(true);
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("VictoriaNiveles.fxml"));
        Parent root = (Parent) myLoader.load();
        VictoriaNivelesController victoriaController = myLoader.<VictoriaNivelesController>getController();
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

    void initWindow(Stage winStage, Button b) {
        this.winStage = winStage;
        this.nextLevel = b;
        modo = "JuegoNiveles.fxml";

    }

    @Override
    protected void defaultData() {
        longitudTablero = 6;
        anchuraTablero = 4;
        duracionPartida = 60;
        duracionTurno = 5;
        turnDelay = 1;
        cancion = "/music/Cancion1.mp3";
        setAudio(cancion);
        audio.play(0.3);
        audioFail = new AudioClip(this.getClass().getResource("/music/fail.mp3").toString());
        audioOK = new AudioClip(this.getClass().getResource("/music/correct.mp3").toString());
        audioFlip = new AudioClip(this.getClass().getResource("/music/flip.wav").toString());
    }

    /**
     * Loads default parameters
     */
    @Override
    protected void recibirParametros() {
        defaultData();
        String[] selectedLevels = {MenuSeleccionNivelesController.nivel};
        niveles = loadLevels(selectedLevels);
        setUpLevel(0);
        minPointsLabel.setText(minPoints + "");

    }

}
