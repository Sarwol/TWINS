/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Image;
import java.util.Objects;
import javafx.scene.control.Button;

/**
 *
 * @author davit
 */
public class Carta extends Button{
    
    private int id;
    private Image imagenInterior;

    public Carta(int id, Image imagenInterior) {
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
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carta other = (Carta) obj;
        if (!Objects.equals(this.imagenInterior, other.imagenInterior)) {
            return false;
        }
        return true;
    }
    
    
}
