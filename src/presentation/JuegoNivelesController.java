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
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import logic.Carta;
import logic.Nivel;
import logic.Puntuacion;
import static presentation.JuegoController.DURACION_PARTIDA;
import static presentation.JuegoController.DURACION_TURNO;
import static presentation.JuegoController.modo;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modo = "JuegoNiveles.fxml";
        // TODO
        //super.initialize(url, rb);
        // loads the default parameters
        defaultData();
        String [] selectedLevels = {"nivel1.ser"};
        niveles = loadLevels(selectedLevels);
        setUpLevel(0);
        // initial score
        puntuacion = new Puntuacion(0);
        // configures access to the pause menu
        setUpPauseMenuAccess();
        // Configures pair selection mechanics
        setUpPairSelection();
        // Specify levels to be loaded
        // configures countdowns
        setTimers(DURACION_PARTIDA, DURACION_TURNO);
        // initialize tablero
        configurarTablero();
        // loads the card turning animation
        setAnimation();
        // Loading the levels
        //String [] levels = {"nivel1.ser"};
        //niveles = loadLevels(levels);
        //setupLevel(0);

    }

    /**
     * Updates the level parameters
     *
     * @param levelIndex sets up the level at this index position
     */
    public void setUpLevel(int levelIndex) {
        Nivel nivel = niveles.get(levelIndex);
        System.out.println("LEVEL PARAMETERS: " + nivel);
        ANCHURA_TABLERO = nivel.getBoardHeight();
        LONGITUD_TABLERO = nivel.getBoardWidth();
        DURACION_PARTIDA = nivel.getTime();
        minPoints = nivel.getMinPoints();
    }
//    @Override
//    public void configurarTablero(){
//        tablero.setFilas(ANCHURA_TABLERO);
//        tablero.setColumnas(LONGITUD_TABLERO);
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
        try{
            if(allCardsFound && !minScoreReached) saltarADerrota(modo);
        } catch(IOException ioe){
            System.out.println("Couldn't execute saltarADerrota(modo)");
            ioe.printStackTrace();
        }
        
        return allCardsFound && minScoreReached;
    }
    
}
