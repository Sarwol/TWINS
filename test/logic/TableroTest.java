/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Rule;

/**
 *
 * @author Dani
 */
public class TableroTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    List<Carta> lista4;
    Baraja baraja4;

    List<Carta> lista3;
    Baraja baraja3;

    @Before
    public void setUp() {
        lista4 = Arrays.asList(
                new Carta(0, null, null),
                new Carta(1, null, null),
                new Carta(0, null, null),
                new Carta(1, null, null));
        baraja4 = new Baraja("baraja4", lista4, null);

        lista3 = Arrays.asList(
                new Carta(0, null, null),
                new Carta(1, null, null),
                new Carta(0, null, null));
        baraja3 = new Baraja("baraja3", lista3, null);
    }

    /**
     * Test of barajarTablero method, of class Tablero.
     */
    @Test
    public void testBarajarTableroCorrectSize() {
        System.out.println("barajarTableroCorrectSize");
        int expResult4 = 4;     // resultado para baraja de 4 cartas        
        // Tablero de 2x2 con baraja de 4 cartas
        Tablero instance = new Tablero(2, 2, baraja4.getCartas());

        int result = instance.barajarTablero();
        Assert.assertEquals(expResult4, result);
    }

    @Test
    public void testBarajarTableroIncorrectSize() {
        System.out.println("barajarTableroIncorrectSize");
        int expResult3 = -1;     // resultado para baraja de 3 cartas  
        // Tablero de 2x3 con baraja de 4 cartas
        Tablero instance = new Tablero(2, 3, baraja4.getCartas());
        int result = instance.barajarTablero();
        Assert.assertEquals(expResult3, result);
    }

    /**
     * Test of clonarBaraja method, of class Tablero.
     */
    @Test
    public void testClonarBaraja() {
        System.out.println("clonarBaraja");
        Tablero instance = new Tablero(2, 2, baraja4.getCartas());
        int tableroSize = 2*2;

        List<Carta> resultList = instance.clonarBaraja(baraja4.getCartas(), baraja4.getCartas().size());
        // Check if sizes are equal
        boolean result = tableroSize == resultList.size();
        System.out.println("SAME SIZES?: " + result + " baraja4: " + baraja4.size() + " resultList: " + resultList.size());
        System.out.println("COMPARING: baraja4: " + baraja4.getCartas() + " resultList: " + resultList);

        while(resultList.size() > 0 && result){
            Carta cartaOG = resultList.get(0);
            for(int j = 0; j < baraja4.size(); j++){
                Carta cartaResult = baraja4.getCartas().get(j);
                result = cartaResult.getCartaID() == cartaOG.getCartaID();
                if(result){ // if a card with the same ID is found, remove it and stop
                    
                    break;
                }
            }
            if(!result){    // a card was not found, so the copy is wrong
                break;
            }
            resultList.remove(0);
        }
        Assert.assertTrue(result);  // we want an exact copy
    }
    
    @Test
    public void testClonarBarajaMasGrande() {
        System.out.println("clonarBaraja");
        Tablero instance = new Tablero(2, 1, baraja4.getCartas());
        int tableroSize = 2*1;
        List<Carta> resultList = instance.clonarBaraja(baraja4.getCartas(), tableroSize);
        // Check if sizes are equal
        boolean result = tableroSize == resultList.size();
        System.out.println("SAME SIZES?: " + result + " baraja4: " + baraja4.size() + " resultList: " + resultList.size());
        System.out.println("COMPARING: baraja4: " + baraja4.getCartas() + " resultList: " + resultList);

        while(resultList.size() > 0 && result){
            Carta cartaOG = resultList.get(0);
            for(int j = 0; j < baraja4.size(); j++){
                Carta cartaResult = baraja4.getCartas().get(j);
                result = cartaResult.getCartaID() == cartaOG.getCartaID();
                if(result){ // if a card with the same ID is found, remove it and stop
                    
                    break;
                }
            }
            if(!result){    // a card was not found, so the copy is wrong
                break;
            }
            resultList.remove(0);
        }
        Assert.assertTrue(result);  // we want an exact copy
    }
}
