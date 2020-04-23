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
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import logic.Carta;
import logic.Categoria;
import logic.Puntuacion;
import logic.Tablero;
import static presentation.PartidaEstandarController.ANCHURA_TABLERO;
import static presentation.PartidaEstandarController.LONGITUD_TABLERO;

/**
 * FXML Controller class para Partida por categoría.
 *
 * @author Jesús Yoel
 */
public class PartidaCategoriaController extends JuegoLibreController {

    private List<Categoria> categorias;
    private Categoria categoriaActual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        categorias = new ArrayList<Categoria>();
        rellenarCategorias();
        categoriaActual = categorias.get(0);
        mostrarCategoria();
    }

    /**
     * Comprueba si las cartas seleccionadas son iguales, y si son de la
     * Categoria que corresponde en ese momento
     */
    private void comprobarCartas() {
        if (parSeleccionado.size() == 2) {
            carta1 = parSeleccionado.get(0);
            carta2 = parSeleccionado.get(1);

            parSeleccionado.forEach((carta) -> {
                System.out.print(carta + " ");
            });
            System.out.println();

            if (carta1.getCartaID() == carta2.getCartaID()
                    && carta1.getCategoria() == categoriaActual) {
                carta1.setDisable(true);
                carta2.setDisable(true);
                //tablero.getChildren().remove(carta1);
                //tablero.getChildren().remove(carta2);
                puntuacion.sumarPuntos();
                try {
                    if (categoriaActual != categorias.get(1)) {
                        categorias.remove(0);
                        categoriaActual = categorias.get(0);
                        mostrarCategoria();
                    } else {
                        categorias.remove(0);
                        categoriaActual = categorias.get(0);
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("YOU DORK!!!!");
                    e.printStackTrace();
                }
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

    //pop-up para enseñar la categoria a buscar
    private void mostrarCategoria() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Categoria Actual");
        alert.setHeaderText(categoriaActual.toString());
        alert.setContentText("La pareja de cartas que tiene que buscar es de la categoria " + categoriaActual.toString());
        alert.showAndWait();
    }

    //métodos para rellenar la lista de categorias
    private void bucleRellenar(Categoria cat) {
        for (int i = 0; i < 4; i++) {
            categorias.add(cat);
        }
    }

    private void rellenarCategorias() {
        bucleRellenar(Categoria.FLORES);
        bucleRellenar(Categoria.ANIMALES);
        bucleRellenar(Categoria.FRUTAS);
    }

}
