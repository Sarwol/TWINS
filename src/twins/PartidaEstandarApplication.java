/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import logic.Carta;
import logic.Tablero;


/**
 *
 * @author davit
 */
public class PartidaEstandarApplication extends Application{

    public static final int LONGITUD_TABLERO = 6;
    public static final int ANCHURA_TABLERO = 4;

//    private Pane root = new Pane();
//    private GridPane table = new GridPane();
//    private Tablero tablero = new Tablero(LONGITUD_TABLERO, ANCHURA_TABLERO);
    private Pane root;
    private Tablero tablero;
    
    private Parent createContent() {
//        root.setPrefSize(700, 600);
        tablero.setPrefSize(700, 600);
        tablero.setBaraja(generarBaraja(LONGITUD_TABLERO * ANCHURA_TABLERO));
        tablero.barajarTablero();
        //root.getChildren().add(tablero);
        

        //return root;
        return tablero;
    }
    
    /**
     * Método temporal para generar barajas.
     * @param numCartas
     * @return lista con las cartas. Retorna null si el número de cartas a 
     * generar no es par.
     */
    private List<Carta> generarBaraja(int numCartas){
        if(numCartas %2 != 0){return null;}
        
        List<Carta> baraja = new ArrayList<Carta>();
        File card = new File("." + File.separator + "images" + File.separator + "card.png");
        Image image = new Image(card.toURI().toString(), 50, 50, false, false);
        
        for(int i = 0; i < numCartas; i++){
           baraja.add(new Carta(i, image));
        }
        
        return baraja;
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        root = new Pane();
        
        tablero = new Tablero(ANCHURA_TABLERO, LONGITUD_TABLERO);
        tablero.setBaraja(generarBaraja(LONGITUD_TABLERO * ANCHURA_TABLERO));
        tablero.barajarTablero();
        
        root.getChildren().add(tablero);
        
        Scene scene = new Scene(root, 700, 600);        
        stage.setScene(scene);
        
        stage.show();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
    
