/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author davit
 */
public class Baraja {
    
    private String nombre;
    private List<Carta> cartas;
    private Image imagenReverso;

    public Baraja(String nombre, List<Carta> cartas, Image imagenReverso) {
        this.nombre = nombre;
        this.cartas = cartas;
        this.imagenReverso = imagenReverso;
    }

    /**
     * Get the value of imagenReverso
     *
     * @return the value of imagenReverso
     */
    public Image getImagenReverso() {
        return imagenReverso;
    }

    /**
     * Set the value of imagenReverso
     *
     * @param imagenReverso new value of imagenReverso
     */
    public void setImagenReverso(Image imagenReverso) {
        this.imagenReverso = imagenReverso;
    }

    /**
     * Get the value of cartas
     *
     * @return the value of cartas
     */
    public List<Carta> getCartas() {
        return cartas;
    }

    /**
     * Set the value of cartas
     *
     * @param cartas new value of cartas
     */
    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

    /**
     * Get the value of nombre
     *
     * @return the value of nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Set the value of nombre
     *
     * @param nombre new value of nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void añadirCarta(Carta carta){
        cartas.add(carta);
    }
    
    public void eliminarCarta(Carta carta){
        cartas.remove(carta);
    }
    
    public int getNumeroCartas() {
        return cartas.size();
    }
    
    public boolean esVacia(){
        return getNumeroCartas() == 0;
    }
}