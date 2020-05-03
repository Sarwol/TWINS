/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import logic.Carta;
import logic.Puntuacion;
import static presentation.JuegoLibreController.TURN_DELAY;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class PartidaPorCartaController extends JuegoLibreController {

    @FXML
    private Carta cartaAEncontrar;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
//        parSelec = new ArrayList<Carta>();
//        parSeleccionado = FXCollections.observableList(parSelec);
//        parSeleccionado.addListener(new ListChangeListener() {
//            @Override
//            public void onChanged(ListChangeListener.Change change) {
//                comprobarCartas();
//                if(isVictoria()) {
//                    try{
//                        saltarAVictoria(puntuacion, tiempoActual);
//                    } catch(IOException e) {}
//                }
//            }
//        });     // end parSeleccionado
        seleccionarCartaAEncontrar();
    }

    /**
     * Looks for cards that haven't been found and chooses a random one among
     * them.
     */
    private void seleccionarCartaAEncontrar() {
        ObservableList<Node> cards = tablero.getChildren();
        List<Carta> cardsNotFound = new ArrayList<Carta>();
        
        for (Node nodoCarta : cards) {
            if ((nodoCarta instanceof Carta) && !nodoCarta.isDisabled()) {
                cardsNotFound.add((Carta) nodoCarta);
            }
        }
         System.out.println("THERE ARE " + cardsNotFound.size() + " CARDS LEFT");
        if(cardsNotFound.size() > 0){
            int randomPos = (int) Math.floor(Math.random() * (cardsNotFound.size()));
            Carta chosenCard = cardsNotFound.get(randomPos);
            cartaAEncontrar.setcartaID(chosenCard.getCartaID());
            cartaAEncontrar.setGraphic(new ImageView(chosenCard.getImagenCarta()));
            cartaAEncontrar.setText(Integer.toString(chosenCard.getCartaID()));
        }
        
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
                punt.setText(puntuacion.getPuntos() + "");
                this.seleccionarCartaAEncontrar();
                countdown.stop();
                setTimer(DURACION_TURNO, tiempoTurno);
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
    
    @Override
    public boolean sonIguales(Carta card1, Carta card2) {
        return super.sonIguales(card1, card2) 
                && card1.getCartaID() == cartaAEncontrar.getCartaID();
    }
}
