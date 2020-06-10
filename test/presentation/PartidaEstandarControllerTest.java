/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import logic.Baraja;
import logic.Carta;
import logic.Categoria;
import logic.JavaFXThreadingRule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.junit.Assert;

/**
 *
 * @author Dani
 */
public class PartidaEstandarControllerTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    List<Carta> lista4;
    Baraja baraja4;
    List<Categoria> cats;
    String imagePath;
    Baraja barajaNoDup;
    List<Carta> listaNoDup;

    @Before
    public void setUp() {
        lista4 = Arrays.asList(
                new Carta(0, null, null),
                new Carta(1, null, null),
                new Carta(0, null, null),
                new Carta(1, null, null));
        cats = Arrays.asList(new Categoria("FRUTAS"));
        try {
            imagePath = getClass().getResource("/images/card.png").toURI().toString();

        } catch (URISyntaxException uri) {
            System.out.println("Can't get URI");
        }
        baraja4 = new Baraja("Baraja4", lista4, cats, imagePath);

        listaNoDup = Arrays.asList(
                new Carta(0, null, null),
                new Carta(1, null, null),
                new Carta(2, null, null),
                new Carta(3, null, null));
        barajaNoDup = new Baraja("BarajaNoDup", listaNoDup, cats, imagePath);

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of copiaBaraja method, of class JuegoLibreController.
     */
    @Test
    public void testCopiaBaraja() {
        System.out.println("copiaBaraja");
        Baraja barajaResultado = null;
        JuegoLibreController instance = new PartidaEstandarController();
        barajaResultado = instance.copiaBaraja(baraja4);
        boolean hasRepInstance = false; // we assume no repeated instances
        for (Carta carta : baraja4) {
            for (Carta copiaCarta : barajaResultado) {
                hasRepInstance = copiaCarta.equals(carta);  // are these two pointers the same instance?
                if (hasRepInstance) { // if they point to the same object
                    break;
                }
            }
            if (hasRepInstance) {
                break;   // if repeated, break out of this loop too
            }
        }

        Assert.assertFalse(hasRepInstance); // we want no repeated instances
        // Everything else is the same
        Assert.assertEquals(baraja4.getNombre(), barajaResultado.getNombre());
        Assert.assertEquals(baraja4.getCategorias(), barajaResultado.getCategorias());
        Assert.assertEquals(baraja4.getPathImagenReverso(), barajaResultado.getPathImagenReverso());

    }

    /**
     * Test of duplicarCartas method, of class JuegoLibreController.
     */
    @Test
    public void testDuplicarCartas() {
        System.out.println("duplicarCartas");
        Baraja barajaResultado = null;
        JuegoLibreController instance = new PartidaEstandarController();

        barajaResultado = instance.duplicarCartas(barajaNoDup);
        
        // copy must be double the size
        Assert.assertEquals(barajaNoDup.size() * 2, barajaResultado.size());
        System.out.println("barajaNoDup: " + barajaNoDup + "\nBarajaResultado: " + barajaResultado);
        
        boolean cardIsTwice = true;
        // Check the duped list has two of each card
        for (Carta carta : barajaNoDup) {
            Carta cartaDup1 = null;
            Carta cartaDup2 = null;
            for (Carta cartaDuplicada : barajaResultado) {
                if (cartaDup1 == null && carta.getCartaID() == cartaDuplicada.getCartaID()) {  // gets the first card
                    cartaDup1 = cartaDuplicada;
                }
                if (carta.getCartaID() == cartaDuplicada.getCartaID() && !cartaDuplicada.equals(cartaDup1)) { // gets the second card
                    cartaDup2 = cartaDuplicada;
                }
            }
            if (!(cartaDup1 != null && cartaDup2 != null)) {  // if we can't find two cards, bad copy
                cardIsTwice = false;
                break;
            }
            if (cartaDup1.equals(cartaDup2)) {    // if we have the same instance twice, bad copy
                cardIsTwice = false;
                break;
            }
        }

        Assert.assertTrue(cardIsTwice);     // all cards must be present twice
    }
}
