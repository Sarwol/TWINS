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
    
    private int id;
    private Image imagenInterior;

    /**
     * Creamos un Button sin texto y con una imagen.
     * @param id
     * @param imagenInterior 
     */
    public Carta(int id, Image imagenInterior) {
       super("", new ImageView(imagenInterior));
       this.id = id;
       this.imagenInterior = imagenInterior;
    }

    public Image getImagenInterior() {
        return imagenInterior;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImagenInterior(Image imagenInterior) {
        this.imagenInterior = imagenInterior;
        super.setGraphic(new ImageView(this.imagenInterior));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
    
    
}
