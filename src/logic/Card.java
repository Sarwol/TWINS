/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javafx.scene.image.Image;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author davit
 */

//@XmlRootElement(name="card")
@SuppressWarnings("unchecked")
@XmlType(propOrder={"cartaID","imagenCarta","imagenBaraja","categoria"})
public class Card {
    
    private int cartaID;
    private Categoria categoria;
    private String imagenCarta;
    private String imagenBaraja;
    private boolean isRevealed;

    /**
     * Creamos un Button sin texto y con una imagen.
     * @param cartaID
     * @param imagenCarta 
     */
    
    public Card(){
        
    }
    
    public Card(Carta carta){
        this.cartaID = carta.getCartaID();
        this.imagenCarta = carta.getImagenCarta().impl_getUrl();
        this.imagenBaraja = carta.getImagenBaraja().impl_getUrl();
        this.categoria = carta.getCategoria();
    }
    
    /**
     * 
     * @param id id of the card. Used to identify the pairs.
     * @param imagenCarta Specific image for the pair of cards.
     * @param imagenBaraja Specific image for a deck.
     */
    public Card(int id, String imagenCarta, String imagenBaraja) {
       // By default, set the deck image.
       this.cartaID = id;
       this.imagenCarta = imagenCarta;
       this.imagenBaraja = imagenBaraja;
       this.isRevealed = false;

    }
  
    public Card(int id, String imagenCarta, String imagenBaraja, Categoria categoria) {
       // By default, set the deck image.
       this.cartaID = id;
       this.imagenCarta = imagenCarta;
       this.imagenBaraja = imagenBaraja;
       this.isRevealed = false;
       this.categoria = categoria;
    }
 
 
    @XmlAttribute(name="cartaID")
    public int getCartaID() {
        return cartaID;
    }
    
    @XmlAttribute(name="imagenCarta")
    public String getImagenCarta() {
        return imagenCarta;
    }
    
    @XmlAttribute(name="imagenBaraja")
    public String getImagenBaraja() {
        return imagenBaraja;
    }

    public void setcartaID(int cartaID) {
        this.cartaID = cartaID;
    }

    @XmlElement(name="categoria")
    public Categoria getCategoria(){
        return categoria;
    }
    
    public void setImagenCarta(String imagenCarta) {
        this.imagenCarta = imagenCarta;
    } 
    
    public void setImagenBaraja(String imagenBaraja) {
        this.imagenBaraja = imagenCarta;
    } 
    
    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }
    
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 59 * hash + this.cartaID;
//        return hash;
//    }
    @Override
     public String toString(){
        return "[ID: " + getCartaID() + "]";
     }
    
     public Carta cardToCarta(){
        Image imagenCarta = new Image(this.imagenCarta);
        Image imagenBaraja = new Image (this.imagenBaraja);
        Carta resultado = new Carta(this.getCartaID(), imagenCarta, imagenBaraja, this.getCategoria());
        return resultado;
     }
}


