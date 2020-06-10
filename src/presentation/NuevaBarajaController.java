/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private String pathImagenReverso;
    private Image imagenNueva;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            pathImagenReverso = this.getClass().getResource("/images/card.png").toURI().toString();
        } catch (URISyntaxException ex) {
            Logger.getLogger(NuevaBarajaController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        nuevaBaraja.setCategorias(listaCategorias.getItems());
        nuevaBaraja.setPathImagenReverso(pathImagenReverso);
//        System.out.println(nuevaBaraja.getCategorias());
        cancelar(event);
    }

    @FXML
    private void cancelar(ActionEvent event) {
	((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
    }

    @FXML
    private void añadirCategoria(ActionEvent event) {
        Categoria nueva = new Categoria(categoriaField.getText());
        categoriaObservableList.add(nueva);
        listaCategorias.setItems(categoriaObservableList);
    }

    @FXML
    private void subirImagenReverso(ActionEvent event) {
        Image imagenSubida = new Image(uploadImagePath(), 50, 50, false, false);
        imagenReverso.setImage(imagenSubida);
    }
    
    public Baraja devolverBaraja(){
        return nuevaBaraja;
    }
    
    public String uploadImagePath() {
        String imagePath = null;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        try {
           imagePath = file.toURI().toString();
           pathImagenReverso = imagePath;
        } catch (Exception e) {
        }
        return imagePath;
    }

    private void setExtFilters(FileChooser chooser) {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }
}
