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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class JuegoLibreController implements Initializable {

    public static final int LONGITUD_TABLERO = 6;
    public static final int ANCHURA_TABLERO = 4;
    public static final int TURN_DELAY = 500;
   
    public static final int DURACION_PARTIDA = 60;

    protected static String cancion;
    @FXML
    protected Tablero tablero;
    @FXML
    protected Label tiempo;
    @FXML
    protected Label punt;
    protected Timeline countdown;
    protected int tiempoActual;
    protected List<Carta> parSelec;
    protected ObservableList<Carta> parSeleccionado;
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
        cancion = cancionActual;
        if(cancion == null) cancion = "/music/Cancion1.mp3";
        if(cancion != "") {
            setAudio(cancion);
            audio.play();
        }
        
        puntuacion = new Puntuacion(0);
        // CAUTION: parSelec and parSeleccionado must be defined in each subclass
        parSelec = new ArrayList<Carta>();
        parSeleccionado = FXCollections.observableList(parSelec);
        parSeleccionado.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                comprobarCartas();
            }
        });
        setTimer(DURACION_PARTIDA);
        
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
    public void setTimer(int duration) {
        tiempoActual = duration;
        countdown = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tiempoActual == 0) {
                    countdown.stop();
                }
                tiempo.setText((tiempoActual--) + "");
            }
        }));
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();
    }

    /*
    Alert alert= new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText("This dialog has custom actions");
    alert.setContentText("Choose an option");
    ButtonType reset = new ButtonType("Volver a jugar");
    ButtonType close = new ButtonType("Salir");
    alert.getButtonTypes().setAll(reset, close);
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent()) {
        if (result.get() == reset)
            System.out.println("One");
        else
            System.exit(0);
    }
    */
    
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
                punt.setText(puntuacion.getPuntos() + "");
            } else {
                puntuacion.restarPuntos();
                punt.setText(puntuacion.getPuntos() + "");
                // Wait a specified amount of time before turning the cards back around
                setDelayedCardTurn();
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
    public void pause_onClick(ActionEvent event) throws IOException{
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Pausa.fxml"));
        Parent root = (Parent) myLoader.load();
        PausaController pausaController = myLoader.<PausaController>getController();
        audio.stop();
        
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
}
