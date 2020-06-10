/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@XmlRootElement(name="coleccion")
public class Coleccion {
    private List<Deck> barajas;
    
    public Coleccion(){
        barajas = new ArrayList<Deck>();
    }

    @XmlElement(name="deck")
    public List<Deck> getBarajas() {
        return barajas;
    }

    public void setBarajas(List<Deck> barajas) {
        this.barajas = barajas;
    }
    
    public void addBaraja(Deck baraja){
        barajas.add(baraja);
    }
    
    public List<Baraja> leerBarajas(){
        List<Baraja> resultado = new ArrayList();
        for(Deck deck : barajas){
            resultado.add(deck.convertirABaraja());
        }
        return resultado;       
    }
}
