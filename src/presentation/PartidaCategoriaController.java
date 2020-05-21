/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import logic.Carta;
import logic.Categoria;
import static presentation.PartidaEstandarController.longitudTablero;
import static presentation.PartidaEstandarController.anchuraTablero;

/**
 * FXML Controller class para Partida por categoría.
 *
 * @author Jesús Yoel
 * @author Dani
 */
public class PartidaCategoriaController extends JuegoLibreController {

    private Categoria categoriaActual;
    public int numeroCategorias = parametros.getBarajaCategoria().getCategorias().size();
    
    protected int contador = 1;
    
   
    @FXML
    private Label categoriaLabel;

    /**
     * Initializes the controller class.
     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // Loads parameters from config class
//        recibirParametros();
//        if (!parametros.isSinMusica()) {
//            setAudio(cancion);
//            audio.play(0.3);
//        }
//        puntuacion = new Puntuacion(0);
//        setUpPauseMenuAccess();
//        setUpPairSelection();
//        
//       
//        if(parametros.isLimitePartida())
//            setTimers(duracionPartida, duracionTurno);
//        
//        configurarTablero();
//        setAnimation();
//        // CAMBIO CATEGORIA
//        categoriaActual = parametros.getBarajaCategoria().getCategorias().get(0);
//        categoriaLabel.setText(categoriaActual.toString());
//    }

    @Override
    public void configurarTablero(){
        configurarTablero(copiaBaraja(parametros.getBarajaCategoria()));
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
                audioOK.play(0.1);
                int numCategorias = parametros.getBarajaCategoria().getCategorias().size();
                if (contador == 1 + ((anchuraTablero*longitudTablero)/numCategorias) / numeroCategorias) {
                    // CAMBIO CATEGORIA
                    categoriaActual = parametros.getBarajaCategoria().getCategorias().get(1);
                    categoriaLabel.setText(categoriaActual.toString());
                }
            } else {
                puntuacion.restarPuntos();
                punt.setText(puntuacion.getPuntos() + "");
                // Wait a specified amount of time before turning the cards back around
                setDelayedCardTurn();
                audioFail.play(0.1);
            }
            resetTurnCountdown();

            // since a new event is generated when we remove an element
            // from the ObservableList, we remove instead from the List
            // to avoid an infinite loop by triggering the listener
            parSelec.remove(0);
            parSelec.remove(0);
        }
    }
    // CAMBIO CATEGORIA
    @Override
    public boolean sonIguales(Carta card1, Carta card2) {
        return super.sonIguales(card1, card2) && carta1.getCategoria().equals(categoriaActual)
                && carta2.getCategoria().equals(categoriaActual);
    }
    
    @Override
    public void recibirParametros(){
        super.recibirParametros();
        categoriaActual = parametros.getBarajaCategoria().getCategorias().get(0);
        categoriaLabel.setText(categoriaActual.toString());
    }
}
