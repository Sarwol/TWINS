/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Carta;

/**
 *
 * @author davit
 */
public class Baraja implements Iterable<Carta> {
    
    private String nombre;
    private List<Carta> cartas;
    private Image imagenReverso;
    private List<Categoria> categorias;

    public Baraja() {
    }

    public Baraja(String nombre, List<Carta> cartas, Image imagenReverso) {
        super();
        this.nombre = nombre;
        this.cartas = cartas;
        this.imagenReverso = imagenReverso;
        this.categorias = categorias;
    }
    
    
    @Override
    public Iterator<Carta> iterator() {
        return this.cartas.iterator();
    }
    
    
    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
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
    
    public List<Image> listarImagenesCartas(){
        List<Image> imagenesCarta = new ArrayList<Image>();
        for (Iterator<Carta> it = cartas.iterator(); it.hasNext();) {
            Carta carta = it.next();
            Image image = carta.getImagenCarta();
            imagenesCarta.add(image);
        }
         return imagenesCarta;
    }

    
}