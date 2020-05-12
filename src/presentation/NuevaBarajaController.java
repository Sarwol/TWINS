/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import logic.Baraja;
import logic.Carta;
import logic.Categoria;

/**
 * FXML Controller class
 *
 * @author davit
 */
public class NuevaBarajaController implements Initializable {

    @FXML
    private Button guardarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private TextField nombreField;
    @FXML
    private ListView<Categoria> listaCategorias;
    @FXML
    private TextField categoriaField;
    @FXML
    private Button añadirButton;
    @FXML
    private ImageView imagenReverso;
    
    private Baraja nuevaBaraja;
    private List<Categoria> categorias;
    private ObservableList<Categoria> categoriaObservableList;
    private Image image;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nuevaBaraja = null;
        categorias = new ArrayList<Categoria>();
        categoriaObservableList = FXCollections.observableArrayList(categorias);
    }    

    @FXML
    private void guardarBaraja(ActionEvent event) {
        Image reverso = imagenReverso.getImage();
        String nombre = nombreField.getText();
        List<Carta> cartas = new ArrayList<Carta>();
        nuevaBaraja = new Baraja(nombre,cartas,reverso);
         //nuevaBaraja.setCategorias(categorias);
	        cancelar(event);
    }

    @FXML
	    private void cancelar(ActionEvent event) {
	        ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
	    }

    @FXML
    private void añadirCategoria(ActionEvent event) {
        //Categoria nueva = new Categoria(categoriaField.getText());
        //categoriaObservableList.add(nueva);
        //listaCategorias.setItems(categoriaObservableList);
    }

    @FXML
    private void subirImagenReverso(ActionEvent event) {
        imagenReverso.setImage(uploadImage());
    }
    
    public Baraja devolverBaraja(){
        return nuevaBaraja;
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
    
    private void setExtFilters(FileChooser chooser){
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }
}
