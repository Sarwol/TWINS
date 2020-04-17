/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twins;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
   import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.layout.GridPane;
import logic.Tablero;


/**
 *
 * @author davit
 */
public class PartidaEstandar extends Application{



    private Pane root = new Pane();
    private GridPane table = new GridPane();
    private Tablero tablero = new Tablero(6, 4);
    
    private Parent createContent() {
        root.setPrefSize(700, 600);
        
        root.getChildren().add(tablero);

        return root;
    }


    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());

        stage.setScene(scene);
        stage.show();
    }
    
    public void barajarTablero(){
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
    
