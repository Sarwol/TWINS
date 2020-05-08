/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Carta;
import logic.Puntuacion;
import logic.Tablero;
import static presentation.MusicaController.cancionActual; 
import static presentation.PausaController.pauseMusic;
import twins.PartidaEstandarApplication;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class JuegoLibreController implements Initializable {

    public static final int LONGITUD_TABLERO = 6;
    public static final int ANCHURA_TABLERO = 4;
    public static final int TURN_DELAY = 500;
    public static final int NUM_CATEGORIAS = 2;
    public static final int DURACION_PARTIDA = 60;
    public static final int DURACION_TURNO = 5;
    
    protected static String modo = PartidaEstandarApplication.mode;
   
    
    //private BooleanProperty pauseProperty = new SimpleBooleanProperty();
    protected static String cancion;
    @FXML
    protected Tablero tablero;
    @FXML
    protected Label tiempo;
    @FXML
    protected Label tiempoTurno;
    @FXML
    protected Label punt;
    protected Timeline countdown;
    protected int tiempoActual;
    protected int turnoActual;
    protected List<Carta> parSelec;
    protected ObservableList<Carta> parSeleccionado;
    protected static List<Boolean> pauseList;
    protected static ObservableList<Boolean> observPauseList; 
    protected Puntuacion puntuacion;
    protected Carta carta1;
    protected Carta carta2;
    protected AudioClip audio = null; 
    //protected List<Categoria> categorias;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/presentation/MenuPrincipal.fxml"));
        try {
            Parent root = (Parent) miCargador.load();
        } catch (IOException ex) {
            Logger.getLogger(JuegoLibreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        MenuPrincipalController controller = miCargador.<MenuPrincipalController>getController();
        controller.stopMediaPlayer();
        controller.cargarCancion("/src/music/Cancion1.mp3");
        controller.playMediaPlayer();
        */
        cancion = cancionActual; 
        if(cancion == null) cancion = "/music/Cancion1.mp3"; 
        if(cancion != "") { 
            setAudio(cancion);
            audio.play(0.3); 
        } 
        puntuacion = new Puntuacion(0);
        pauseList = new ArrayList<Boolean>();
        pauseList.add(Boolean.FALSE);
        observPauseList = FXCollections.observableList(pauseList);

        
        
        // CAUTION: parSelec and parSeleccionado must be defined in each subclass
        parSelec = new ArrayList<Carta>();
        parSeleccionado = FXCollections.observableList(parSelec);
        parSeleccionado.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                comprobarCartas();
                if(isVictoria()) {
                    try{
                        saltarAVictoria(puntuacion, tiempoActual, modo);
                    } catch(IOException e) {}
                }
            }
        });
        
       //listener para activar de nuevo el tablero después de Pausa
        observPauseList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                if(observPauseList.get(0)){
                    tablero.setVisible(true);
                    if(pauseMusic.isPlaying()) pauseMusic.stop();
                }
            }
        });
        
        
        setTimer(DURACION_PARTIDA,tiempo);
        setTimer(DURACION_TURNO, tiempoTurno);
        
        // initialize tablero
        tablero.setFilas(ANCHURA_TABLERO);
        tablero.setColumnas(LONGITUD_TABLERO);
        tablero.setBaraja(generarBaraja(LONGITUD_TABLERO * ANCHURA_TABLERO));
        tablero.barajarTablero();
        
    }
    
    /**
     * Creates the Timeline used to implement the countdown time in the game
     *
     * @param duration amount of seconds the round lasts
     */
    public void setTimer(int duration, Label label) {
        if(label == tiempo) tiempoActual = duration;
        else turnoActual = duration;
        countdown = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(label == tiempo) {
                    if (tiempoActual == 0) {
                        countdown.stop();
                        try {
                            saltarADerrota(modo);
                        } catch (IOException e) {}
                    } 
                    label.setText((tiempoActual--) + "");
               } else {
                    if(turnoActual == 0) {
                        countdown.stop();
                        puntuacion.restarPuntos();
                         punt.setText(puntuacion.getPuntos() + "");
                        setTimer(DURACION_TURNO,tiempoTurno);
                    }
                   else label.setText((turnoActual--) + "");
                    
                }
            }
            
        }));
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();
        
    }
    
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
                AudioClip ok = new AudioClip(this.getClass().getResource("/music/correct.mp3").toString());
                ok.play(0.1);
                punt.setText(puntuacion.getPuntos() + "");
                countdown.stop();
                setTimer(DURACION_TURNO, tiempoTurno);
            } else {
                puntuacion.restarPuntos();
                punt.setText(puntuacion.getPuntos() + "");
                // Wait a specified amount of time before turning the cards back around
                setDelayedCardTurn();
                AudioClip fail = new AudioClip(this.getClass().getResource("/music/fail.mp3").toString());
                fail.play(0.05);
            }

            // since a new event is generated when we remove an element
            // from the ObservableList, we remove instead from the List
            // to avoid an infinite loop by triggering the listener
            parSelec.remove(0);
            parSelec.remove(0);
        }
    }
    
    /**
     * Creates a new thread that will turn the cards back around.
     */
    public void setDelayedCardTurn() {
        Task<Void> waitTurnCards = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(TURN_DELAY);
                } catch (InterruptedException ie) {
                    System.out.println("Error sleeping before turning cards");
                    ie.printStackTrace();
                }

                return null;
            }
        };
        waitTurnCards.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                carta1.turn();
                carta2.turn();
            }
        });
        new Thread(waitTurnCards).start();
    }

    /**
     * Checks whether the two cards make up a valid pair.
     *
     * @param card1 one of the cards to be compared
     * @param card2 the other card to be compared
     * @return whether the cards are equal or not.
     */
    public boolean sonIguales(Carta card1, Carta card2) {
        return carta1.getCartaID() == carta2.getCartaID() && !carta1.equals(carta2);
    }

    /**
     * Auxiliary method to generate a deck for the game
     *
     * @param numCartas the amount of cards to generate
     * @return baraja the deck with cards.
     */
    public List<Carta> generarBaraja(int numCartas) {
        if (numCartas % 2 != 0) {
            return null;
        }

        List<Carta> baraja = new ArrayList<Carta>();
        File deckCard = new File("." + File.separator + "images" + File.separator + "card.png");
        //String cardImages = "." + File.separator + "images" + File.separator + "card";
        String cardImages = "." + File.separator + "images" + File.separator + "fruit";
        Image deckCardImage = new Image(deckCard.toURI().toString(), 50, 50, false, false);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numCartas / 2; j++) {
                File currentCard = new File(cardImages + (j + 1) + ".png");
                Image currentCardImage = new Image(currentCard.toURI().toString(), 50, 50, false, false);
                Carta carta = new Carta(j, currentCardImage, deckCardImage);
                
                // Add event to detect when a Carta is clicked
                carta.addEventHandler(MouseEvent.MOUSE_CLICKED, clickPairEventHandler);
                baraja.add(carta);
            }
        }
        return baraja;
    }

    /*
    Turns the card around and adds it to the selected pair
     */
    EventHandler<MouseEvent> clickPairEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Carta cartaElegida = (Carta) e.getSource();
            cartaElegida.turn();

            // Just for the debug print
            int cartaID = cartaElegida.getCartaID();
            int posX = Tablero.getRowIndex(cartaElegida);
            int posY = Tablero.getColumnIndex(cartaElegida);

            // Add card to pair to compare whether they are equal
            parSeleccionado.add(cartaElegida);
            System.out.println("Carta elegida:\t" + "[ID: " + cartaID + "]\t" + "(" + posX + ", " + posY + ")\n" + puntuacion.toString()
                    + "\n" + cartaElegida.getCategoria());
        }
    };

    public void setAudio(String sonido) {
        audio = new AudioClip(this.getClass().getResource(sonido).toString());
        //note.play();
    }
    
    @FXML
    public void mute_onClick(ActionEvent event) {
        audio.stop();
    }
    
    @FXML
    public void pause_onClick(ActionEvent event) throws IOException{
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Pausa.fxml"));
        Parent root = (Parent) myLoader.load();
        PausaController pausaController = myLoader.<PausaController>getController();
        audio.stop();
        tablero.setVisible(false);
        
        Stage winStage = new Stage();
        // When this stage is closed, resume the countdown
        winStage.setOnHidden(e ->{
            countdown.play();
        });
        pausaController.initPausaWindow(winStage);
        Scene scene = new Scene(root);
        winStage.setScene(scene);
        winStage.setTitle("Pausa");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
        
        countdown.pause();
    }
    
    public boolean isVictoria() {
        boolean res = true;
        ObservableList<Node> cartas = tablero.getChildren();
        for(int i = 0; i < cartas.size(); i++){
            if((cartas.get(i) instanceof Carta && cartas.get(i).isDisabled())){
                res = true;
            }
            else {
                res = false;
                return res;
            }
        }
        return res;
    }
    
    public void saltarAVictoria(Puntuacion punt, int temp, String m) throws IOException {
        audio.stop();
        countdown.stop();
        tablero.setDisable(true);
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Victoria.fxml"));         
        Parent root = (Parent) myLoader.load();
        VictoriaController victoriaController = myLoader.<VictoriaController>getController();        
        Stage winStage = new Stage();
        victoriaController.initVictoriaWindow(winStage, punt, temp, m);
        Scene scene = new Scene(root);
        winStage.setScene(scene);
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
        //stopAudio(cancion);
        Stage thisStage = (Stage) tablero.getScene().getWindow();
        thisStage.close();
    }
    
    public void saltarADerrota(String m) throws IOException{
        audio.stop();
        tablero.setDisable(true);
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Derrota.fxml"));
        Parent root = (Parent) myLoader.load();
        DerrotaController derrotaController = myLoader.<DerrotaController>getController();        
        Stage winStage = new Stage();
        derrotaController.initDerrotaWindow(winStage, m);
        Scene scene = new Scene(root);
        winStage.setScene(scene);
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
        //stopAudio(cancion);
        Stage thisStage = (Stage) tablero.getScene().getWindow();
        thisStage.close();
    }
    
}