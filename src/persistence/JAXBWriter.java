/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import logic.Baraja;
import logic.Card;
import logic.Carta;
import logic.Categoria;
import logic.Coleccion;
import logic.Coleccion;
import logic.Deck;
import logic.Deck;
import logic.JAXBResolver;

/**
 *
 * @author David
 */
public class JAXBWriter {
    
    public static void main(String[] args) throws JAXBException, IOException {
        JAXBResolver dr = new JAXBResolver();
        
        JAXBContext context = dr.getCtx();
        
        Coleccion coleccion = new Coleccion();
        Deck baraja = new Deck();
        baraja.setNombre("Baraja 1");
        Deck baraja1 = new Deck();
        baraja1.setNombre("Baraja 2");
        
        Carta carta = new Carta();
        carta.setcartaID(23);
        //Card cardPrueba = carta.cartaToCard();
        
        List<Card> cartas = new ArrayList<>();
        Card card = new Card();
        card.setcartaID(23);
        Categoria categoria = new Categoria("Pájaros");
        card.setCategoria(categoria);
        cartas.add(card);
        
        //cartas.add(cardPrueba);
        
        baraja.setCartas(cartas);
        
        coleccion.addBaraja(baraja);
        coleccion.addBaraja(baraja1);
       
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(coleccion, new FileWriter(System.getProperty("user.home") + File.separator + "coleccion.xml"));
    }
}
