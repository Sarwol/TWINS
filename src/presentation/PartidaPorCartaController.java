/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

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
        parSelec = new ArrayList<Carta>();
        parSeleccionado = FXCollections.observableList(parSelec);
        parSeleccionado.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                comprobarCartas();
            }
        });     // end parSeleccionado
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
            if (!nodoCarta.isDisabled()) {
                cardsNotFound.add((Carta) nodoCarta);
            }
        }
        int randomPos = (int) Math.floor(Math.random() * (cardsNotFound.size()));
        Carta chosenCard = cardsNotFound.get(randomPos);
        cartaAEncontrar.setcartaID(chosenCard.getCartaID());
        cartaAEncontrar.setGraphic(new ImageView(chosenCard.getImagenCarta()));
        cartaAEncontrar.setText(Integer.toString(chosenCard.getCartaID()));
    }
    
    @Override
    public void comprobarCartas() {
        if (parSeleccionado.size() == 2) {
            System.out.println("METODO comprobarCartas EN PartidaPorCartaController");
            carta1 = parSeleccionado.get(0);
            carta2 = parSeleccionado.get(1);

            parSeleccionado.forEach((carta) -> {
                System.out.print(carta + " ");
            });
            System.out.println();

            if (carta1.getCartaID() == carta2.getCartaID()
                    && carta1.getCartaID() == cartaAEncontrar.getCartaID()) {
                carta1.setDisable(true);
                carta2.setDisable(true);
                //tablero.getChildren().remove(carta1);
                //tablero.getChildren().remove(carta2);
                puntuacion.sumarPuntos();
                this.seleccionarCartaAEncontrar();
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
}
