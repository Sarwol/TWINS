/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import logic.Carta;
import logic.Categoria;
import logic.Puntuacion;
import logic.Tablero;

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
    
    private String cancion = "/music/Cancion1.mp3";
    @FXML
    protected Tablero tablero;
    @FXML
    protected Label tiempo;
    protected Timeline countdown;
    protected int tiempoActual;
    protected List<Carta> parSelec;
    protected ObservableList<Carta> parSeleccionado;
    protected Puntuacion puntuacion;
    protected Carta carta1;
    protected Carta carta2;
    //protected List<Categoria> categorias;
    protected boolean porCategoria;
    protected Categoria categoriaActual;
    protected int contador = 1;
    protected boolean categoria = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playAudio(cancion);
        if(categoria) 
            categoriaActual = Categoria.FRUTAS;
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
     * @param duration amount of seconds the round lasts
     */
    public void setTimer(int duration){
        tiempoActual = duration;
        countdown = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                if(tiempoActual == 0){countdown.stop();}
                tiempo.setText((tiempoActual--) + "");
            }
        }));
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();
    }

    public void comprobarCartas() {
        if (parSeleccionado.size() == 2) {
            carta1 = parSeleccionado.get(0);
            carta2 = parSeleccionado.get(1);

            parSeleccionado.forEach((carta) -> {
                System.out.print(carta + " ");
            });
            System.out.println();
            
            if(categoria) comprobarCategoria(); 
            if (carta1.getCartaID() == carta2.getCartaID() && !categoria) {
                    carta1.setDisable(true);
                    carta2.setDisable(true);
                    //tablero.getChildren().remove(carta1);
                    //tablero.getChildren().remove(carta2);
                     puntuacion.sumarPuntos();  
            } else {
                puntuacion.restarPuntos();
                Task<Void> waitTurnCards = new Task<Void>() { // task to wait a specific amount of time
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

            // since a new event is generated when we remove an element
            // from the ObservableList, we remove instead from the List
            // to avoid an infinite loop
            parSelec.remove(0);
            parSelec.remove(0);

        }
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
        String cardImages = "." + File.separator + "images" + File.separator + "card";
        //String fruitImages = "." + File.separator + "images" + File.separator + "fruit";
        Image deckCardImage = new Image(deckCard.toURI().toString(), 50, 50, false, false);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numCartas / 2; j++) {
                if(j % 2 == 0) cardImages = "." + File.separator + "images" + File.separator + "fruit";
                else cardImages = "." + File.separator + "images" + File.separator + "card";
                File currentCard = new File(cardImages + (j + 1) + ".png");
                Image currentCardImage = new Image(currentCard.toURI().toString(), 50, 50, false, false);
                Carta carta = new Carta(j, currentCardImage, deckCardImage);
                if(j % 2 == 0) carta.setCategoria(Categoria.FRUTAS);
                else carta.setCategoria(Categoria.PAJAROS);
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

    
       public void playAudio(String sonido){
        AudioClip note = new AudioClip(this.getClass().getResource(sonido).toString());
        note.play();
    }
     
     public void stopAudio(String sonido){
         AudioClip note = new AudioClip(this.getClass().getResource(sonido).toString());
         note.stop();
     }
     
     //pop-up para enseñar la categoria a buscar
    protected void mostrarCategoria() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Categoria Actual");
        alert.setHeaderText(categoriaActual.toString());
        alert.setContentText("La pareja de cartas que tiene que buscar es de la categoria " + categoriaActual.toString());
        alert.showAndWait();
    }
    
    
    protected void comprobarCategoria(){
        if (carta1.getCartaID() == carta2.getCartaID() && carta1.getCategoria() ==
                    categoriaActual && carta2.getCategoria() == categoriaActual) {
                    carta1.setDisable(true);
                    carta2.setDisable(true);
                     //tablero.getChildren().remove(carta1);
                     //tablero.getChildren().remove(carta2);
                     puntuacion.sumarPuntos();
                     contador++;
                     if(contador == 1 + 12/NUM_CATEGORIAS) {
                         categoriaActual = Categoria.PAJAROS;
                         mostrarCategoria();
                        }
            }
    }
}
