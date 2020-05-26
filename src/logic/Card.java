/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import logic.Categoria;

/**
 * 
 * @author davit
 */

//@XmlRootElement(name="card")
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
    
    @XmlElement(name="imagenCarta")
    public String getImagenCarta() {
        return imagenCarta;
    }
    
    @XmlElement(name="imagenBaraja")
    public String getImagenBaraja() {
        return imagenBaraja;
    }

    public void setcartaID(int id) {
        this.cartaID = id;
    }

    @XmlElement(name="categoria")
    public Categoria getCategoria(){
        return categoria;
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
    
}


