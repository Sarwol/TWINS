/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
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
//        barajaCategorias = barajaSeleccionada.getCategorias();
        // categoriasObservableList = FXCollections.observableList(barajaCategorias);
        //categoriaComboBox.setItems(categoriasObservableList);
    }

    @FXML
    private void subirImagen(ActionEvent event) {
        imagenCarta.setImage(uploadImage());
    }

    @FXML
    private void guardar(ActionEvent event) {
        Image anverso = imagenCarta.getImage();
        Image reverso = barajaSeleccionada.getImagenReverso();
        cartaNueva = new Carta();
        cartaNueva.setCategoria(categoriaComboBox.getSelectionModel().getSelectedItem());
        cartaNueva.setImagenCarta(anverso);
        cartaNueva.setImagenBaraja(reverso);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();
    }

    public Carta devolverCarta() {
        return cartaNueva;
    }

    public Image uploadImage() {
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
        } catch (Exception e) {
        }
        return image;
    }

    private void setExtFilters(FileChooser chooser) {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }
}
