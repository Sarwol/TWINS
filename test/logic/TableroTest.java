/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Dani
 */
public class TableroTest {
    
    public TableroTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of barajarTablero method, of class Tablero.
     */
    @Test
    public void testBarajarTablero() {
        System.out.println("barajarTablero");
        Tablero instance = new Tablero();
        int expResult = 0;
        int result = instance.barajarTablero();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clonarBaraja method, of class Tablero.
     */
    @Test
    public void testClonarBaraja() {
        System.out.println("clonarBaraja");
        List<Carta> barajaOriginal = null;
        int size = 0;
        Tablero instance = new Tablero();
        List<Carta> expResult = null;
        List<Carta> result = instance.clonarBaraja(barajaOriginal, size);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFilas method, of class Tablero.
     */
    @Test
    public void testGetFilas() {
        System.out.println("getFilas");
        Tablero instance = new Tablero();
        int expResult = 0;
        int result = instance.getFilas();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColumnas method, of class Tablero.
     */
    @Test
    public void testGetColumnas() {
        System.out.println("getColumnas");
        Tablero instance = new Tablero();
        int expResult = 0;
        int result = instance.getColumnas();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFilas method, of class Tablero.
     */
    @Test
    public void testSetFilas() {
        System.out.println("setFilas");
        int filas = 0;
        Tablero instance = new Tablero();
        instance.setFilas(filas);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setColumnas method, of class Tablero.
     */
    @Test
    public void testSetColumnas() {
        System.out.println("setColumnas");
        int columnas = 0;
        Tablero instance = new Tablero();
        instance.setColumnas(columnas);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBaraja method, of class Tablero.
     */
    @Test
    public void testSetBaraja() {
        System.out.println("setBaraja");
        List<Carta> baraja = null;
        Tablero instance = new Tablero();
        instance.setBaraja(baraja);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of girarTodasCartas method, of class Tablero.
     */
    @Test
    public void testGirarTodasCartas() {
        System.out.println("girarTodasCartas");
        Tablero instance = new Tablero();
        instance.girarTodasCartas();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
