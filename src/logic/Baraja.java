/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author davit
 */
public class Baraja implements Iterable<Carta> {
    
    private String nombre;
    private List<Carta> cartas;
    private Image imagenReverso;
    private List<Categoria> categorias;
    private String pathImagenReverso;

    public Baraja() {
        this.nombre = "";
        this.cartas = new ArrayList<>();
        this.imagenReverso = null;
        this.categorias = new ArrayList<>();
    }
    public Baraja(String nombre, Image imagenReverso){
        this(nombre, new ArrayList<Carta>(), imagenReverso);
    }

    public Baraja(String nombre, List<Carta> cartas, List<Categoria> categorias, String pathImagenReverso) {
        this.nombre = nombre;
        this.cartas = cartas;
        this.categorias = categorias;
        this.pathImagenReverso = pathImagenReverso;
        this.imagenReverso = new Image(pathImagenReverso, 50, 50, false, false);
//        System.out.println("se ha creado imagen: " + pathImagenReverso);
    }

    
    
    public Baraja(String nombre, List<Carta> cartas, Image imagenReverso) {
        this.nombre = nombre;
        this.cartas = cartas;
        this.imagenReverso = imagenReverso;
        this.categorias = new ArrayList<>();
    }
    
    

    public Baraja(String nombre, List<Carta> cartas, Image imagenReverso, List<Categoria> categorias) {
//        super();
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

    public String getPathImagenReverso() {
        return pathImagenReverso;
    }

    public void setPathImagenReverso(String pathImagenReverso) {
        this.pathImagenReverso = pathImagenReverso;
        this.imagenReverso= new Image(pathImagenReverso, 50, 50, false, false);
//        System.out.println("se ha creado imagen: " + pathImagenReverso);
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

    public int size(){
        return this.cartas.size();
    }
    
    public List<Card> convertirCards(){
        List<Card> cards = new ArrayList();
        for(Carta carta : this.cartas){
            Card card = new Card(carta.getCartaID(),carta.getPathImagenCarta(),carta.getPathImagenBaraja(),carta.getCategoria());
            cards.add(card);
        }
        return cards;
    }
    
    public Deck convertirADeck(){
        return new Deck(nombre, convertirCards() , pathImagenReverso, categorias);
    }

    public List<Integer> getListaNumCategorias(int tamañoTablero){
        List<Integer> lista = new ArrayList<Integer>();
        int contadorCategoria = 0;
        for(int i =0; i < categorias.size(); i++){
            for(int j = 0; j < tamañoTablero/2; j++){
                if(cartas.get(j).getCategoria().equals(categorias.get(i)))
                  contadorCategoria++;  
            }
            lista.add(i, contadorCategoria);
            contadorCategoria = 0;
        }
        
        return lista;
    }
    @Override
    public String toString(){
        String thisBaraja = nombre + ": {";
        for(Carta carta : cartas){
            thisBaraja += carta.toString() + ", ";
        }
        thisBaraja += "}";
        return thisBaraja;
    }
}