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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import logic.Baraja;
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
 * @author Dani
 */
public class PartidaCategoriaController extends JuegoLibreController {

    private List<Categoria> categorias;
    private Categoria categoriaActual;
    //protected boolean porCategoria;
    protected int contador = 1;
    //protected boolean categoria = false;
    @FXML
    private Tablero tablero;
    @FXML
    private Label tiempo;
   
    @FXML
    private Label categoriaLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        categorias = new ArrayList<Categoria>();
        /*rellenarCategorias();
        categoriaActual = categorias.get(0);*/
        //categoria = false;
        categoriaActual = Categoria.FRUTAS;
        categoriaLabel.setText(categoriaActual.toString());
        //mostrarCategoria();
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
                contador++;
                AudioClip ok = new AudioClip(this.getClass().getResource("/music/correct.mp3").toString());
                ok.play(0.1);
                if (contador == 1 + 12 / NUM_CATEGORIAS) {
                    categoriaActual = Categoria.PAJAROS;
                    categoriaLabel.setText(categoriaActual.toString());
                    //mostrarCategoria();
                }
            } else {
                puntuacion.restarPuntos();
                punt.setText(puntuacion.getPuntos() + "");
                // Wait a specified amount of time before turning the cards back around
                setDelayedCardTurn();
                AudioClip fail = new AudioClip(this.getClass().getResource("/music/fail.mp3").toString());
                fail.play(0.05);
            }
            resetTurnCountdown();

            // since a new event is generated when we remove an element
            // from the ObservableList, we remove instead from the List
            // to avoid an infinite loop by triggering the listener
            parSelec.remove(0);
            parSelec.remove(0);
        }
    }

    @Override
    public boolean sonIguales(Carta card1, Carta card2) {
        return super.sonIguales(card1, card2) && carta1.getCategoria()
                == categoriaActual && carta2.getCategoria() == categoriaActual;
    }

    @Override
    public Baraja generarBaraja(int numCartas, String cartaModelo, String nombreBaraja) {
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
                if (j % 2 == 0) {
                    cardImages = "." + File.separator + "images" + File.separator + "fruit";
                } else {
                    cardImages = "." + File.separator + "images" + File.separator + "card";
                }
                File currentCard = new File(cardImages + (j + 1) + ".png");
                Image currentCardImage = new Image(currentCard.toURI().toString(), 50, 50, false, false);
                Carta carta = new Carta(j, currentCardImage, deckCardImage);
                if (j % 2 == 0) {
                    carta.setCategoria(Categoria.FRUTAS);
                } else {
                    carta.setCategoria(Categoria.PAJAROS);
                }
                // Add event to detect when a Carta is clicked
                carta.addEventHandler(MouseEvent.MOUSE_CLICKED, clickPairEventHandler);
                baraja.add(carta);
            }

        }
        Baraja laBaraja = new Baraja(nombreBaraja,baraja,deckCardImage);
        return laBaraja;
    }
/*
    
    protected void mostrarCategoria() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Categoria Actual");
        alert.setHeaderText(categoriaActual.toString());
        alert.setContentText("La pareja de cartas que tiene que buscar es de la categoria " + categoriaActual.toString());
        alert.showAndWait();
    }*/
}
