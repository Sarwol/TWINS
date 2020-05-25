
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
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
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Baraja;
import logic.Carta;
import logic.Puntuacion;
import logic.Tablero;

/**
 * FXML Controller class
 *
 * @author Dani
 */
/**
 *
 * @author Dani
 */
public abstract class JuegoController implements Initializable {

    public static int longitudTablero = 6;
    public static int anchuraTablero = 4;
    // Measured in seconds
    public static double turnDelay = 0.500;
//    public static int NUM_CATEGORIAS = 2;
    public static int duracionPartida = 60;
    // Duracion del turno en segundos
    public static int duracionTurno = 5;

    protected static String modo;
    // Referencia a esta Stage
    private Stage winStage;

    protected static String cancion;
    @FXML
    protected Tablero tablero;
    // tiempoPartida restante de la partida actual
    @FXML
    protected Label tiempoPartida;
    // tiempoPartida restante del turno actual
    @FXML
    protected Label tiempoTurno;
    // Label de puntuación
    @FXML
    protected Label punt;
    // Actualiza el tiempoPartida restante de partida
    protected Timeline countdownPartida;
    // Actualiza el tiempoPartida restante del turno
    protected Timeline countdownTurno;
    // se usa para la cuenta atrás de la partida
    protected int tiempoActualPartida;
    // se usa para la cuenta atrás del turno
    protected int tiempoActualTurno;
    // Se usa para guardar las dos cartas seleccionadas
    protected List<Carta> parSelec;
    // Se usa para guardar las dos cartas seleccionadas
    protected ObservableList<Carta> parSeleccionado;
    // Guarda todas las cartas que están boca arriba y se deben girar
    protected List<Carta> cartasPorGirar;
    // Guarda un booleano para ocultar el tablero
    protected static List<Boolean> pauseList;
    // Guarda un booleano para ocultar el tablero
    protected static ObservableList<Boolean> observPauseList;
    // Lleva el registro de la puntuación
    protected Puntuacion puntuacion;
    // La primera carta que se selecciona
    protected Carta carta1;
    // La segunda carta que se selecciona
    protected Carta carta2;
    // Para reproducir la canción
    protected AudioClip audio = null;
    // Animaciones
    public RotateTransition turnAnimation;
    //Audio de fallo de carta
    public static AudioClip audioFail;
    //Audio de Acierto
    public static AudioClip audioOK;
    //Audio de Giro
    public static AudioClip audioFlip;
    //Baraja que contendrá el tablero de la partida
    public static Baraja barajaActual;
    //Baraja que contendrá el tablero de la partida por Categoria
    public static Baraja barajaCategoria;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // loads the default parameters
        recibirParametros();                        // abstract
        // initial score
        //puntuacion = new Puntuacion(0);             // same for all game modes
        // configures access to the pause menu
        setUpPauseMenuAccess();                     // same for all game modes
        // Configures pair selection mechanics
        setUpPairSelection();                       // same for all game modes
        // configures countdowns
        setTimers(duracionPartida, duracionTurno);  // same for all game modes
        // initialize tablero
        configurarTablero();                        // abstract
        // loads the card turning animation
        setAnimation();                             // same for all game modes
    }

    public void setUpPauseMenuAccess() {
        pauseList = new ArrayList<>();
        pauseList.add(Boolean.FALSE);
        observPauseList = FXCollections.observableList(pauseList);

        //listener para activar de nuevo el tablero después de Pausa
        observPauseList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                if (observPauseList.get(0)) {
                    tablero.setVisible(true);
                }
            }
        });
    }

    public void setUpPairSelection() {
        /*
        Construye la lista para guardar las cartas seleccionadas
         */
        parSelec = new ArrayList<>();
        parSeleccionado = FXCollections.observableList(parSelec);
        parSeleccionado.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                comprobarCartas();
                if (isVictoria()) {
                    try {
                        saltarAVictoria(puntuacion, tiempoActualPartida, modo);
                    } catch (IOException e) {
                        System.out.println("Error intentando saltar a victoria");
                        e.printStackTrace();
                    }
                }
            }
        });
        // Las cartas que deben volver a girarse
        cartasPorGirar = new ArrayList<>();
    }

    /**
     * Configura las opciones del tablero
     */
    public abstract void configurarTablero();

    /**
     * Configures animations for cards
     */
    public void setAnimation() {

        turnAnimation = new RotateTransition();
        turnAnimation.setDuration(Duration.millis(200));
        turnAnimation.setByAngle(360);
        turnAnimation.setCycleCount(1);
        turnAnimation.setAutoReverse(false);
        turnAnimation.setAxis(new Point3D(0, 1, 0));
    }

    /**
     * Creates the Timeline used to implement the countdown time in the game
     * Creates de Timeline used to implement the turn countdown time in the game
     *
     * @param roundDuration amount of seconds the round lasts
     * @param turnDuration amount of seconds the turn lasts
     */
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

                    // if there is a card selected, turn it back around
                    if (parSeleccionado.size() > 0) {
                        parSeleccionado.get(0).turn();
                        parSeleccionado.remove(0);
                    }
                }
                tiempoTurno.setText((tiempoActualTurno--) + "");
            }
        }));
        countdownTurno.setCycleCount(Timeline.INDEFINITE);
        countdownTurno.play();
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

            // since a new event is generated when we remove an element
            // from the ObservableList, we remove instead from the List
            // to avoid an infinite loop by triggering the listener
            parSelec.remove(0);
            parSelec.remove(0);
        }
    }

    /**
     * Resets the turn countdown
     */
    public void resetTurnCountdown() {
        // Reset turn countdown
        tiempoActualTurno = duracionTurno;
        //tiempoTurno.setText(tiempoActualTurno + "");
    }

    /**
     * Creates a new thread that will turn the cards back around.
     */
    public void setDelayedCardTurn() {
        cartasPorGirar.add(carta1);
        cartasPorGirar.add(carta2);
        System.out.println("CALLING SETDELAYEDCARDTURN in JUEGOCONTROLLER" + carta1 + "\n" + carta2);
        Task<Void> waitTurnCards = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(Math.round(turnDelay * 1000));
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
                for (Carta carta : cartasPorGirar) {
                    carta.turn();
                    turnAnimation.setNode(carta);
                    turnAnimation.play();
                    System.out.println("CARTA:" + carta);
                }
                cartasPorGirar.clear();
//                carta1.turn();
//                turnAnimation.setNode(carta1);
//                turnAnimation.play();
//                carta2.turn();
//                turnAnimation.setNode(carta2);
//                turnAnimation.play();
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
     * @param cartaModelo path to the image of the deck of cards
     * @param nombreBaraja name of the deck
     * @return baraja the deck with cards.
     */
    public Baraja generarBaraja(int numCartas, String cartaModelo, String nombreBaraja) {
        Baraja barajaCartas = null;
        if (numCartas % 2 != 0) {
            System.out.println("*****************************************");
            System.out.println("Uneven number of cards");
            System.out.println("*****************************************");
            return null;
        }
        try {

            List<Carta> baraja = new ArrayList<>();
            Image deckCardImage = new Image(this.getClass().getResource("/images/card.png").toURI().toString(), 50, 50, false, false);

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < numCartas / 2; j++) {
                    //System.out.println(this.getClass().getResource("/images/" + cartaModelo + (j + 1) + ".png"));
                    Image currentCardImage = new Image(this.getClass().getResource("/images/" + cartaModelo + (j + 1) + ".png").toURI().toString(), 50, 50, false, false);

                    Carta carta = new Carta(j, currentCardImage, deckCardImage);

                    // Add event to detect when a Carta is clicked
                    carta.addEventHandler(MouseEvent.MOUSE_CLICKED, clickPairEventHandler);
                    baraja.add(carta);
                }
            }
            barajaCartas = new Baraja(nombreBaraja, baraja, deckCardImage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (barajaCartas == null) {
            System.out.println("BARAJA IS NULL!!!!");
        }
        return barajaCartas;
    }
    /*
    Turns the card around and adds it to the selected pair
     */
    EventHandler<MouseEvent> clickPairEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Carta cartaElegida = (Carta) e.getSource();
            audioFlip.play(1);
            cartaElegida.turn();

            // Plays animation
            turnAnimation.setNode(cartaElegida);
            turnAnimation.play();

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
        // If audio is playing, stop it. Otherwise, start audio
        if (audio.isPlaying()) {
            audio.stop();
        } else {
            audio.play(0.3);
        }
    }

    @FXML
    public void pause_onClick(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Pausa.fxml"));
        Parent root = (Parent) myLoader.load();
        PausaController pausaController = myLoader.<PausaController>getController();
        audio.stop();
        tablero.setVisible(false);
        if(countdownPartida != null && countdownTurno != null){
            countdownPartida.pause();
            countdownTurno.pause();
        }

        Stage pausaWinStage = new Stage();
        Stage thisStage = (Stage) punt.getScene().getWindow();
        pausaController.initPausaWindow(pausaWinStage, thisStage, cancion, audio, observPauseList, countdownPartida, countdownTurno);
        Scene scene = new Scene(root);
        pausaWinStage.setScene(scene);
        pausaWinStage.setTitle("Pausa");
        pausaWinStage.initModality(Modality.APPLICATION_MODAL);
        pausaWinStage.show();
    }

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
        return allCardsFound;
    }

    public abstract void saltarAVictoria(Puntuacion punt, int temp, String m) throws IOException;

    public void saltarADerrota(String m) throws IOException {
        if (audio.isPlaying()) {
            audio.stop();
        }
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

    /**
     * Pass the parameters needed when starting this stage
     *
     * @param stage a reference to this stage
     */
    void initWindow(Stage stage) {
        winStage = stage;
    }

    /**
     * Should load default data for a given game
     */
    protected abstract void defaultData();

    /**
     * Should load parameters from the Configuration class. Can also load
     * default data
     */
    protected abstract void recibirParametros();
}
