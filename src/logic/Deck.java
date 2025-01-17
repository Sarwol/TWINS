/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author David
 */
//@XmlRootElement(name="deck")
@XmlType(propOrder={"nombre","cartas","imagenReverso", "categorias"})
public class Deck implements Iterable<Card>{
 

    
    private String nombre;
    private List<Card> cartas;
    private String imagenReverso;
    private List<Categoria> categorias;

    public Deck() {
        this.nombre = "";
        this.cartas = new ArrayList<>();
        this.imagenReverso = null;
        this.categorias = new ArrayList<>();
    }
    public Deck(String nombre, String imagenReverso){
        this(nombre, new ArrayList<Card>(), imagenReverso);
    }

    public Deck(String nombre, List<Card> cartas, String imagenReverso) {
        this.nombre = nombre;
        this.cartas = cartas;
        this.imagenReverso = imagenReverso;
        this.categorias = new ArrayList<>();
    }
    
    

    public Deck(String nombre, List<Card> cartas, String imagenReverso, List<Categoria> categorias) {
//        super();
        this.nombre = nombre;
        this.cartas = cartas;
        this.imagenReverso = imagenReverso;
        this.categorias = categorias;
    }
    
    
    @Override
    public Iterator<Card> iterator() {
        return this.cartas.iterator();
    }
    
    @XmlElementWrapper(name="categorias")
    @XmlElement(name="categoria")
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
    @XmlAttribute(name="imagenReverso")
    public String getImagenReverso() {
        return imagenReverso;
    }

    /**
     * Set the value of imagenReverso
     *
     * @param imagenReverso new value of imagenReverso
     */
    public void setImagenReverso(String imagenReverso) {
        this.imagenReverso = imagenReverso;
    }

    /**
     * Get the value of cartas
     *
     * @return the value of cartas
     */
    @XmlElementWrapper(name="cartas")
    @XmlElement(name="carta")
    public List<Card> getCartas() {
        return cartas;
    }

    /**
     * Set the value of cartas
     *
     * @param cartas new value of cartas
     */
    public void setCartas(List<Card> cartas) {
        this.cartas = cartas;
    }

    /**
     * Get the value of nombre
     *
     * @return the value of nombre
     */
    @XmlAttribute(name="nombre")
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

    public void añadirCarta(Card carta){
        cartas.add(carta);
    }
    
    public void eliminarCarta(Card carta){
        cartas.remove(carta);
    }
    
    public int getNumeroCartas() {
        return cartas.size();
    }
    
    public boolean esVacia(){
        return getNumeroCartas() == 0;
    }
    
//    public List<Image> listarImagenesCartas(){
//        List<Image> imagenesCarta = new ArrayList<Image>();
//        for (Iterator<Card> it = cartas.iterator(); it.hasNext();) {
//            Card carta = it.next();
//            Image image = carta.getImagenCarta();
//            imagenesCarta.add(image);
//        }
//         return imagenesCarta;
//    }

    public int size(){
        return this.cartas.size();
    }
    
    public Baraja convertirABaraja(){
        return new Baraja(nombre, convertirCartas() , categorias, imagenReverso);
    }
    
    public List<Carta> convertirCartas(){
        List<Carta> cartas = new ArrayList();
        int i = 0;
        for(Card card : this.cartas){
            // poner ids 
            card.setcartaID(i++);
//            System.out.println(card.getCartaID());
            Carta carta = new Carta(card.getCartaID(),card.getImagenCarta(),card.getImagenBaraja(),card.getCategoria());
//            System.out.println("PATH PERSISTENCIA: " + card.getCartaID() + "        " + card.getImagenBaraja());
            cartas.add(carta);
        }
        return cartas;
    }
    
    @Override
    public String toString(){
        String thisBaraja = nombre + ": {";
        for(Card carta : cartas){
            thisBaraja += carta.toString() + ", ";
        }
        thisBaraja += "}";
        return thisBaraja;
    }
} 

