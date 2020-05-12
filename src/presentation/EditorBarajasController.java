/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import logic.Baraja;
import logic.Carta;

/**
 * FXML Controller class
 *
 * @author davit
 */
public class EditorBarajasController implements Initializable {

    private TilePane cartasPane;
    @FXML
    private Button AñadirParejaButton;
    @FXML
    private Button deleteParejaButton;
    @FXML
    private Button nuevaBarajaButton;
    @FXML
    private Button eliminarBarajaButton;
    @FXML
    private ListView<Baraja> listaBarajas;
    @FXML
    private ListView<Carta> cartasListView;
    @FXML
    private ImageView imagenReverso;
    @FXML
    private Button nuevoReverso;

    private ObservableList<Baraja> barajasObservableList;
    private ObservableList<Carta> cartasObservableList;
    private List<Baraja> barajas;
    private List<Carta> cartas;
    private Carta cartaNueva;
    private Baraja barajaNueva;
    
    Baraja baraja1 = new Baraja();
    Carta carta = new Carta();
    
    private Stage winStage;
    private Stage parentStage;
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cartas = new ArrayList<Carta>();
        barajas = new ArrayList<Baraja>();
        
        //PRUEBA
        baraja1.setNombre("hola");
        barajas.add(baraja1);
        //
       
        barajasObservableList = FXCollections.observableList(barajas);
        listaBarajas.setItems(barajasObservableList);
        cartasListView.setCellFactory(c-> new CartaListCell());
        listaBarajas.setCellFactory(c-> new BarajaListCell());
        listaBarajas.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
           cartas = barajaSeleccionada().getCartas();
           cartasObservableList = FXCollections.observableList(cartas);
           cartasListView.setItems(cartasObservableList);
           imagenReverso.setImage(barajaSeleccionada().getImagenReverso());
           });
    }    

    @FXML
    private void abrirNuevaPareja(ActionEvent event) {
        
        //Después de abrir la ventana y de que se cierre esta, añadir la baraja al observablelist de barajas.
        //Utilizando el método devolverCarta() de NuevaCartaController
        //cartaNueva = (el controlador).devolverCarta();
        // if(cartaNueva != null) 
        //      castasOvservableList.add(cartaNueva);
        //  ADEMAS HAY QUE AÑADIR DOS VECES ESTA CARTA EN LA LISTA DE CARTAS DE BARAJA
    }

    @FXML
    private void eliminarPareja(ActionEvent event) {
        int posicionCarta = cartasListView.getSelectionModel().getSelectedIndex();
        eliminarSeleccionado(posicionCarta, cartasObservableList);     
    }

    @FXML
    private void abrirNuevaBaraja(ActionEvent event) {
        
        //Después de abrir la ventana y de que se cierre esta, añadir la baraja al observablelist de barajas.
        //Utilizando el método devolverBaraja() de NuevaBarajaController
        //barajaNueva = (el controlador).devolverBaraja();
        // if(barajaNueva != null) 
        //      barajaOvservableList.add(barajaNueva);
    }

    @FXML
    private void eliminarBaraja(ActionEvent event) {
        int posicionBaraja = listaBarajas.getSelectionModel().getSelectedIndex();
        eliminarSeleccionado(posicionBaraja, barajasObservableList);
    }
    
    @FXML
    private void nuevaImagenReverso(ActionEvent event) {
        Image reverso = uploadImage();
        imagenReverso.setImage(reverso);
        barajaSeleccionada().setImagenReverso(reverso);
    }
    
    public void eliminarSeleccionado(int posicion, ObservableList list){
           if (posicion>=0) {
                list.remove(posicion);
           }
    }
    
    public void cargarCartas(Baraja baraja){
        cartas = baraja.getCartas();
        
    }
    
    public Baraja barajaSeleccionada(){
        return listaBarajas.getSelectionModel().getSelectedItem();
    }
    
    public Image uploadImage(){
        Image image = null;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
              
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
                       
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                image = SwingFXUtils.toFXImage(bufferedImage, null); 
            } catch (Exception e){
            }  
            return image;   
    }
    
    public void añadirPareja(Image image) {
        ImageView imageView = new ImageView(image);
        cartasPane.getChildren().add(imageView);
    }
    public void cargarBarajas(){
        barajasObservableList = FXCollections.observableList(barajas);
        listaBarajas.setItems(barajasObservableList);
    }

    void initWindow(Stage stage, Stage pStage) {
        winStage = stage;
        parentStage = pStage;
    }
    
    class BarajaListCell extends ListCell<Baraja>
        {
        @Override
            protected void updateItem(Baraja item, boolean empty)
            { 
                super.updateItem(item, empty); // Obligatoria esta llamada
                if (item==null || empty) setText(null);
                else setText(item.getNombre());
            }
            }
    
    class CartaListCell extends ListCell<Carta>
        {
        @Override
            protected void updateItem(Carta item, boolean empty)
            { 
                super.updateItem(item, empty); // Obligatoria esta llamada
                if (item==null || empty){
                    setText(null);
                    setGraphic(null);
                }
                else {
                    ImageView imagenCarta = new ImageView(item.getImagenCarta());
                    setGraphic(imagenCarta);
                }
            }
            }
}
