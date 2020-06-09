/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static presentation.MenuPrincipalController.musicaInicial;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MenuSeleccionNivelesController implements Initializable {

  @FXML
  private Button b1;
  @FXML
  private Button b2;
  @FXML
  private Button b3;
  @FXML
  private Button b4;
  @FXML
  private Button b5;
  @FXML
  private Button b6;
  @FXML
  private Button b7;
  @FXML
  private Button b8;
  @FXML
  private Button b9;
  @FXML
  private Button b10;
  
  @FXML
  private Label l1;
  @FXML
  private Label l2;
  @FXML
  private Label l3;
  @FXML
  private Label l4;
  @FXML
  private Label l5;
  @FXML
  private Label l6;
  @FXML
  private Label l7;
  @FXML
  private Label l8;
  @FXML
  private Label l9;
  @FXML
  private Label l10;
  
  private Label[] labels = new Label[10];
  private Button[] buttons = new Button[10];
  
  private Stage winStage;
  private Stage parentStage;
  public static String nivel;
  public static AudioClip musica;
  private int contador;
  /**
    * Initializes the controller class.
    */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    this.labels[0] = l1;
    this.labels[1] = l2;
    this.labels[2] = l3;
    this.labels[3] = l4;
    this.labels[4] = l5;
    this.labels[5] = l6;
    this.labels[6] = l7;
    this.labels[7] = l8;
    this.labels[8] = l9;
    this.labels[9] = l10;
    this.buttons[0] = b1;
    this.buttons[1] = b2;
    this.buttons[2] = b3;
    this.buttons[3] = b4;
    this.buttons[4] = b5;
    this.buttons[5] = b6;
    this.buttons[6] = b7;
    this.buttons[7] = b8;
    this.buttons[8] = b9;
    this.buttons[9] = b10;
    this.b2.setDisable(true);
    this.b3.setDisable(true);
    this.b4.setDisable(true);
    this.b5.setDisable(true);
    this.b6.setDisable(true);
    this.b7.setDisable(true);
    this.b8.setDisable(true);
    this.b9.setDisable(true);
    this.b10.setDisable(true);
    for (int i = 0; i < 10; i++) {
      if (MenuPrincipalController.progress[i] == true) {
        this.labels[i].setText("COMPLETED");
        if (i + 1 < 10)
          this.buttons[i + 1].setDisable(false); 
      } 
    } 
  }
  
  private void openLevel(Button b, Label l, int p) throws IOException {
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("JuegoNiveles.fxml"));
    Parent root = myLoader.<Parent>load();
    JuegoNivelesController juegoNivelesController = myLoader.<JuegoNivelesController>getController();
    Stage winStage = new Stage();
    Stage thisStage = (Stage)this.b1.getScene().getWindow();
    juegoNivelesController.initWindow(winStage, b, l, p);
    Scene scene = new Scene(root);
    winStage.setScene(scene);
    winStage.setTitle("TWINS");
    winStage.getIcons().add(new Image("/buttons/twinslogo.png"));
    winStage.initModality(Modality.APPLICATION_MODAL);
    winStage.show();
    MenuPrincipalController.musicaInicial.stop();
  }
  
  @FXML
  private void selectLevel1_onClick(ActionEvent event) {
    nivel = "nivel1.ser";
    try {
      openLevel(this.b2, this.l1, 0);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  @FXML
  private void selectLevel2_onClick(ActionEvent event) {
    nivel = "nivel2.ser";
    try {
      openLevel(this.b3, this.l2, 1);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  @FXML
  private void selectLevel3_onClick(ActionEvent event) {
    nivel = "nivel3.ser";
    try {
      openLevel(this.b4, this.l3, 2);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  @FXML
  private void selectLevel4_onClick(ActionEvent event) {
    nivel = "nivel4.ser";
    try {
      openLevel(this.b5, this.l4, 3);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  @FXML
  private void selectLevel5_onClick(ActionEvent event) {
    nivel = "nivel5.ser";
    try {
      openLevel(this.b6, this.l5, 4);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  @FXML
  private void selectLevel6_onClick(ActionEvent event) {
    nivel = "nivel6.ser";
    try {
      openLevel(this.b7, this.l6, 5);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  @FXML
  private void selectLevel7_onClick(ActionEvent event) {
    nivel = "nivel7.ser";
    try {
      openLevel(this.b8, this.l7, 6);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  @FXML
  private void selectLevel8_onClick(ActionEvent event) {
    nivel = "nivel8.ser";
    try {
      openLevel(this.b9, this.l8, 7);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  @FXML
  private void selectLevel9_onClick(ActionEvent event) {
    nivel = "nivel9.ser";
    try {
      openLevel(this.b10, this.l9, 8);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  @FXML
  private void selectLevel10_onClick(ActionEvent event) {
    nivel = "nivel10.ser";
    try {
      openLevel(null, this.l10, 9);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  @FXML
  private void volver_onClick(ActionEvent event) {
    Stage thisStage = (Stage)this.b1.getScene().getWindow();
    thisStage.close();
    System.out.println("Esto es: " + MenuPrincipalController.progress[0]);
  }
  
  void initWindow(Stage stage, Stage pStage, AudioClip mI) {
    this.winStage = stage;
    this.parentStage = pStage;
    musica = mI;
  }
  
  @FXML
  private void changeMusic(MouseEvent event) throws URISyntaxException {
    if (musica.isPlaying()) {
      musica.stop();
    } else {
      musica.play(0.15);
    } 
  }
}
