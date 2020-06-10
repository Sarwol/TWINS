/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author davit
 */
public class Carta extends Button{
    
    private int cartaID;
    private Categoria categoria;
    private Image imagenCarta;
    private Image imagenBaraja;
    private boolean isRevealed;
    private String pathImagenCarta;
    private String pathImagenBaraja;

    /**
     * Creamos un Button sin texto y con una imagen.
     * @param cartaID
     * @param imagenCarta 
     */
    
    public Carta(){
        super();
    }
    
    public Carta(int cartaID, String pathImagenCarta, String pathImagenBaraja, Categoria categoria){
        super();
        this.cartaID = cartaID;
        imagenCarta = new Image(pathImagenCarta, 50, 50, false, false);
        imagenBaraja = new Image(pathImagenBaraja, 50, 50, false, false);
        this.categoria = categoria;
        this.pathImagenCarta = pathImagenCarta;
        this.pathImagenBaraja = pathImagenBaraja;
    }
    
    /**
     * 
     * @param id id of the card. Used to identify the pairs.
     * @param imagenCarta Specific image for the pair of cards.
     * @param imagenBaraja Specific image for a deck.
     */
    public Carta(int id, Image imagenCarta, Image imagenBaraja) {
       // By default, set the deck image.
       super("", new ImageView(imagenBaraja));
       this.cartaID = id;
       this.imagenCarta = imagenCarta;
       this.imagenBaraja = imagenBaraja;
       this.isRevealed = false;
       // Used to cheat, maybe remove later
       super.setText(Integer.toString(id));
    }
  
    public Carta(int id, Image imagenCarta, Image imagenBaraja, Categoria cat) {
       // By default, set the deck image.
       super("", new ImageView(imagenBaraja));
       this.cartaID = id;
       this.imagenCarta = imagenCarta;
       this.imagenBaraja = imagenBaraja;
       this.isRevealed = false;
       this.categoria = cat;
       // Used to cheat, maybe remove later
       super.setText(Integer.toString(id));
    }
 
    /**
     * Turns the card around alternating between the card image and the deck
     * image.
     */
    public void turn(){
        if(isRevealed){ 
            super.setGraphic(new ImageView(imagenBaraja));
            isRevealed = false;
        } else{
            super.setGraphic(new ImageView(imagenCarta));
            isRevealed = true;
        }
    }
    
    public int getCartaID() {
        return cartaID;
    }

    public Image getImagenCarta() {
        return imagenCarta;
    }
    
    public Image getImagenBaraja() {
        return imagenBaraja;
    }

    public void setcartaID(int id) {
        this.cartaID = id;
    }

    public void setImagenCarta(Image imagenCarta) {
        this.imagenCarta = imagenCarta;
        super.setGraphic(new ImageView(this.imagenCarta));
    }
    
    public void setImagenBaraja(Image imagenBaraja) {
        this.imagenBaraja = imagenBaraja;
        super.setGraphic(new ImageView(this.imagenBaraja));
    }

    public String getPathImagenCarta() {
        return pathImagenCarta;
    }

    public void setPathImagenCarta(String pathImagenCarta) {
        this.pathImagenCarta = pathImagenCarta;
        this.imagenCarta = new Image(pathImagenCarta, 50, 50, false, false);
        super.setGraphic(new ImageView(this.imagenCarta));
    }

    public String getPathImagenBaraja() {
        return pathImagenBaraja;
    }

    public void setPathImagenBaraja(String pathImagenBaraja) {
        this.pathImagenBaraja = pathImagenBaraja;
        this.imagenBaraja = new Image(pathImagenBaraja, 50, 50, false, false);
        super.setGraphic(new ImageView(this.imagenBaraja));
    }
    
    public Categoria getCategoria(){
        return categoria;
    }
    
    public void setCategoria(Categoria category){
        categoria = category;
    }
    
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 59 * hash + this.cartaID;
//        return hash;
//    }
    @Override
     public String toString(){
        return "[ID: " + getCartaID() + "] (" + getCategoria() + ")";
     }
    
//     public Card cartaToCard(){
//      Card resultado = new Card(this.getCartaID(), this.getImagenCarta().impl_getUrl(), this.imagenBaraja.impl_getUrl(), this.getCategoria());
//      return resultado;
//     }
}
