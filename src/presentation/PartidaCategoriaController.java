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
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import logic.Baraja;
import logic.Carta;
import logic.Categoria;
import logic.Puntuacion;

/**
 * FXML Controller class para Partida por categoría.
 *
 * @author Jesús Yoel
 * @author Dani
 */
public class PartidaCategoriaController extends JuegoLibreController {

    @FXML
    private Label categoriaLabel;
    
   
    public List<Integer> listaNumCategorias = new ArrayList<Integer>();
    public Baraja barajaCatActual = parametros.getBarajaCategoria();
    private Categoria categoriaActual;
    protected int contador = 0;
    private int indiceCat = 0;
    
   
    @Override
    public void configurarTablero() {
        barajaCatActual = duplicarCartas(barajaCatActual);
        Baraja baraja = barajaCatActual;
        configurarTablero(copiaBaraja(baraja));
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
                if (contador == listaNumCategorias.get(indiceCat)*2) {
                    // CAMBIO CATEGORIA
                    indiceCat++;
                    contador = 0;
                    //try{
                    if(indiceCat < barajaCatActual.getCategorias().size()){
                        categoriaActual = barajaCatActual.getCategorias().get(indiceCat);
                        categoriaLabel.setText(categoriaActual.toString());
                    }//} catch(Exception e){categoriaLabel.setText("FIN!!");} 
                    else {categoriaLabel.setText("FIN!!");}
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
       // CAMBIO CATEGORIA
        listaNumCategorias = barajaCatActual.getListaNumCategorias();
        categoriaActual = barajaCatActual.getCategorias().get(indiceCat);
        categoriaLabel.setText(categoriaActual.toString());
    }
}
