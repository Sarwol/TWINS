/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
public class NuevaParejaController implements Initializable {

    @FXML
    private Button imagenButton;
    @FXML
    private ComboBox<Categoria> categoriaComboBox;
    @FXML
    private Button guardarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private ImageView imagenCarta;

    private String pathImagenCarta;
    private List<Categoria> barajaCategorias;
    private ObservableList<Categoria> categoriasObservableList;
    private Baraja barajaSeleccionada;
    private Carta cartaNueva;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cartaNueva = null;
     }

    @FXML
    private void subirImagen(ActionEvent event) {
        Image imagenSubida = new Image(uploadImagePath(), 50, 50, false, false);
        imagenCarta.setImage(imagenSubida);
    }

    @FXML
    private void guardar(ActionEvent event) {
        String reverso = barajaSeleccionada.getPathImagenReverso();
        cartaNueva = new Carta();
        cartaNueva.setCategoria(categoriaComboBox.getSelectionModel().getSelectedItem());
        cartaNueva.setPathImagenCarta(pathImagenCarta);
        cartaNueva.setPathImagenBaraja(reverso);
        cancelar(event);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
    }

    public Carta devolverCarta() {
        return cartaNueva;
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
           pathImagenCarta = imagePath;
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
    
    public void abrirVentana(FXMLLoader cargador) throws IOException {
        Pane root = cargador.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    public void inicializarBaraja(Baraja baraja){
        barajaSeleccionada = baraja;
        barajaCategorias = baraja.getCategorias();
        categoriasObservableList = FXCollections.observableList(barajaCategorias);
        categoriaComboBox.setItems(categoriasObservableList);
    }
}
