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
import logic.Configuracion;
import logic.Puntuacion;
import logic.Tablero;
import static presentation.ParametrosPartidaController.*;
import static presentation.PausaController.pauseMusic;
import presentation.VentanaJuegoLibreController;

/**
 * FXML Controller class
 *
 * @author Dani
 */
/**
 *
 * @author Dani
 */
public class JuegoLibreController implements Initializable {

    public static int LONGITUD_TABLERO = 6;
    public static int ANCHURA_TABLERO = 4;
    public static int TURN_DELAY = 500;
    public static int NUM_CATEGORIAS = 2;
    public static int DURACION_PARTIDA = 60;
    public static int DURACION_TURNO = 5;

    protected static String modo = VentanaJuegoLibreController.mode;
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
    // Animación de rotación
    public RotateTransition rotateAnimation;
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
     //String para comprobar si se ha activado el límite de tiempo de la partida
     public static String limiteActivado;
     //Baraja default que, si la de Parámetros es null, instanciará el tablero
     Baraja nuevaBaraja;
     //Variable que comprobará en JuegoLibre si se han inicializado los parámetros 
     public String enParametros;
     //Objeto configuración con parámetros default;
     Configuracion defaultConfig = new Configuracion("/music/Cancion1.mp3", "/music/correct.mp3","/music/fail.mp3","/music/flip.wav",4,6,5,60,2,true,"fruit",false);
     //Objeto configuracion que guardará los parámetros escogidos por el usuario
     public static Configuracion parametros;
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
        pauseList = new ArrayList<Boolean>();
        pauseList.add(Boolean.FALSE);
        observPauseList = FXCollections.observableList(pauseList);

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

        //listener para activar de nuevo el tablero después de Pausa
        observPauseList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                if (observPauseList.get(0)) {
                    tablero.setVisible(true);
                    // La música de la pausa
                    if (pauseMusic.isPlaying()) {
                        pauseMusic.stop();
                    }
                }
            }
        });

        if(limiteActivado != "") 
            setTimers(DURACION_PARTIDA, DURACION_TURNO);
        
        
        if(barajaCategoriaActual == null) {
             nuevaBaraja = generarBaraja(LONGITUD_TABLERO * ANCHURA_TABLERO, "fruit", "Baraja Default");
             barajaActual = nuevaBaraja;
           } else {
             barajaActual = generarBaraja(LONGITUD_TABLERO * ANCHURA_TABLERO, imagenCarta , "Baraja Default");
             barajaCategoria = barajaCategoriaActual;
        }
        
        // initialize tablero
        tablero.setFilas(ANCHURA_TABLERO);
        tablero.setColumnas(LONGITUD_TABLERO);
        tablero.setBaraja(barajaActual.getCartas());
        tablero.barajarTablero();
        
        setAnimation();
    }
    
    /**
     * Configures animations for cards
     */
    public void setAnimation(){
        rotateAnimation = new RotateTransition();
        rotateAnimation.setDuration(Duration.millis(200));
        rotateAnimation.setByAngle(360);
        rotateAnimation.setCycleCount(1);
        rotateAnimation.setAutoReverse(false);
        rotateAnimation.setAxis(new Point3D(0, 1, 0));
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
                tiempoPartida.setText((tiempoActualPartida--) + "");
                //System.out.println("UPDATED GAME TIME");
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
                    tiempoActualTurno = DURACION_TURNO;
                }
                tiempoTurno.setText((tiempoActualTurno--) + "");
                //System.out.println("UPDATED TURN TIME");
            }
        }));
        countdownTurno.setCycleCount(Timeline.INDEFINITE);
        countdownTurno.play();
    }

    /*
    public void setTimer(int duration, Label label) {
        if (label == tiempoPartida) {
            tiempoActualPartida = duration;
        } else {
            tiempoActualTurno = duration;
        }
        countdown = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (label == tiempoPartida) {
                    if (tiempoActualPartida == 0) {
                        countdown.stop();
                        try {
                            saltarADerrota(modo);
                        } catch (IOException e) {
                        }
                    }
                    label.setText((tiempoActualPartida--) + "");
                } else {
                    if (tiempoActualTurno == 0) {
                        countdown.stop();
                        puntuacion.restarPuntos();
                        punt.setText(puntuacion.getPuntos() + "");
                        setTimer(DURACION_TURNO, tiempoTurno);
                    } else {
                        label.setText((tiempoActualTurno--) + "");
                    }

                }
            }

        }));
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();

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
                audioOK.play(0.1);
                punt.setText(puntuacion.getPuntos() + "");
                //setTimer(DURACION_TURNO, tiempoTurno);
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
        tiempoActualTurno = DURACION_TURNO;
        //tiempoTurno.setText(tiempoActualTurno + "");
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
                rotateAnimation.setNode(carta1);
                rotateAnimation.play();
                carta2.turn();
                rotateAnimation.setNode(carta2);
                rotateAnimation.play();
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
    public Baraja generarBaraja(int numCartas, String cartaModelo, String nombreBaraja) {
        if (numCartas % 2 != 0) {
            return null;
        }
        
        
        List<Carta> baraja = new ArrayList<Carta>();
        File deckCard = new File("." + File.separator + "images" + File.separator + "card.png");
        String cardImages = "." + File.separator + "images" + File.separator + cartaModelo;
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
        Baraja barajaCartas = new Baraja(nombreBaraja,baraja, deckCardImage);
        return barajaCartas;
        
        /*
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
        return baraja;*/
        
    }

    /*
    Turns the card around and adds it to the selected pair
     */
    EventHandler<MouseEvent> clickPairEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Carta cartaElegida = (Carta) e.getSource();
            audioFlip.play(0.1);
            cartaElegida.turn();
            
            // Plays animation
            rotateAnimation.setNode(cartaElegida);
            rotateAnimation.play();

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
        countdownPartida.pause();
        countdownTurno.pause();

        Stage winStage = new Stage();
        // When this stage is closed, resume the countdown
        winStage.setOnHidden(e -> {
            countdownTurno.play();
            countdownPartida.play();
        });
        pausaController.initPausaWindow(winStage);
        Scene scene = new Scene(root);
        winStage.setScene(scene);
        winStage.setTitle("Pausa");
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.show();
    }

    public boolean isVictoria() {
        boolean res = true;
        ObservableList<Node> cartas = tablero.getChildren();
        for (int i = 0; i < cartas.size(); i++) {
            if ((cartas.get(i) instanceof Carta && cartas.get(i).isDisabled())) {
                res = true;
            } else {
                res = false;
                return res;
            }
        }
        return res;
    }

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

    public void saltarADerrota(String m) throws IOException {
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
    
    void initWindow(Stage stage) {
        winStage = stage;
    }
    protected void recibirParametros(){
        //LONGITUD_TABLERO = nuevaLargura;
        //ANCHURA_TABLERO = nuevaAnchura;
        DURACION_PARTIDA = nuevoTiempoPartida;
        DURACION_TURNO = nuevoTiempoTurno;
        TURN_DELAY = nuevoTiempoError*1000;
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
    protected void defaultData(){
        LONGITUD_TABLERO = defaultConfig.getLarguraTablero();
        ANCHURA_TABLERO = defaultConfig.getAnchuraTablero();
        DURACION_PARTIDA = defaultConfig.getTiempoPartida();
        DURACION_TURNO = defaultConfig.getTiempoTurno();
        TURN_DELAY = defaultConfig.getTiempoVerError();
        audioFail = new AudioClip(this.getClass().getResource(defaultConfig.getSonidoFallo()).toString());
        audioOK = new AudioClip(this.getClass().getResource(defaultConfig.getSonidoCorrecto()).toString());
        audioFlip = new AudioClip(this.getClass().getResource(defaultConfig.getSonidoGiro()).toString());
       
    }
}
