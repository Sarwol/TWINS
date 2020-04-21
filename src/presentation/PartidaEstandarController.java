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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import logic.Carta;
import logic.Puntuacion;
import logic.Tablero;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class PartidaEstandarController implements Initializable {
    public static final int LONGITUD_TABLERO = 6;
    public static final int ANCHURA_TABLERO = 4;
    @FXML
    private Tablero tablero;
    private List<Carta> parSelec;
    private ObservableList<Carta> parSeleccionado;
    private Puntuacion puntuacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        parSelec = new ArrayList<Carta>();
        puntuacion = new Puntuacion(0);
        parSeleccionado = FXCollections.observableList(parSelec);
        parSeleccionado.addListener(new ListChangeListener(){
            @Override
            public void onChanged(Change change){
                if(parSeleccionado.size() == 2){
                    Carta carta1 = parSeleccionado.get(0);
                    Carta carta2 = parSeleccionado.get(1);
                
                    parSeleccionado.forEach((carta) -> {
                        System.out.print(carta + " ");
                    });
                    System.out.println();
                    
                    if(carta1.getCartaID() == carta2.getCartaID()){
                        tablero.getChildren().remove(carta1);
                        tablero.getChildren().remove(carta2);
                        puntuacion.sumarPuntos();
                    } else {
                        puntuacion.restarPuntos();
                    }
                    
                    // since a new event is generated when we remove an element
                    // from the ObservableList, we remove instead from the List
                    // to avoid an infinite loop
                    parSelec.remove(0); parSelec.remove(0);
                
                }
                //System.out.println("Cards selected: " + parSeleccionado.size());
            }
        });
        
        tablero.setFilas(ANCHURA_TABLERO);
        tablero.setColumnas(LONGITUD_TABLERO);
        tablero.setBaraja(generarBaraja(LONGITUD_TABLERO * ANCHURA_TABLERO));
        tablero.barajarTablero();
        
    }    
    
    private List<Carta> generarBaraja(int numCartas){
        if(numCartas %2 != 0){return null;}
        
        List<Carta> baraja = new ArrayList<Carta>();
        File card = new File("." + File.separator + "images" + File.separator + "card.png");
        Image image = new Image(card.toURI().toString(), 50, 50, false, false);
        
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < numCartas / 2; j++){
                Carta carta = new Carta(j, image);
                // Add event to detect when a Carta is clicked
                carta.addEventHandler(MouseEvent.MOUSE_CLICKED, clickPairEventHandler);
                baraja.add(carta);
            }
           
        }
        
        return baraja;
    }
    
    EventHandler<MouseEvent> clickPairEventHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent e){
            Carta cartaElegida = (Carta) e.getSource();
            int cartaID = cartaElegida.getCartaID();
            int posX = Tablero.getRowIndex(cartaElegida);
            int posY = Tablero.getColumnIndex(cartaElegida);
            
            // Add card to pair to compare whether they are equal
            parSeleccionado.add(cartaElegida);
            System.out.println("Carta elegida:\t" + "[ID: " + cartaID + "]\t" + "(" + posX + ", " + posY + ")\n" + puntuacion.toString());
        }
    };
    
}
