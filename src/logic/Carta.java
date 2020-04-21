/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author davit
 */
public class Carta extends Button{
    
    private int cartaID;
    private Image imagenInterior;

    /**
     * Creamos un Button sin texto y con una imagen.
     * @param cartaID
     * @param imagenInterior 
     */
    
    public Carta(){
        super();
    }
    public Carta(int id, Image imagenInterior) {
       super("", new ImageView(imagenInterior));
       this.cartaID = id;
       this.imagenInterior = imagenInterior;
       super.setText(new Integer(id).toString());
    }

    

//    @Override
//    public boolean equals(Object o){
//        if(!(o instanceof Carta)){return false;}
//        
//        Carta otra = (Carta) o;
//        
//        return otra.getCartaID() == this.getCartaID();
//    }

    public int getCartaID() {
        return cartaID;
    }

    public Image getImagenInterior() {
        return imagenInterior;
    }

    public void setcartaID(int id) {
        this.cartaID = id;
    }

    public void setImagenInterior(Image imagenInterior) {
        this.imagenInterior = imagenInterior;
        super.setGraphic(new ImageView(this.imagenInterior));
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
