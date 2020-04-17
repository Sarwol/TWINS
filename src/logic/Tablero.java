/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 *
 * @author davit
 */
public class Tablero extends GridPane {
    private int filas;
    private int columnas;
    private List<Carta> baraja;
    private int randomNumber;
    private Carta cartaAIntroducir;
    
    public Tablero(int filas, int columnas) {
        super();
        this.filas = filas;
        this.columnas = columnas;
        baraja = new ArrayList();
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public void setBaraja(List<Carta> baraja) {
        this.baraja = baraja;
    }
    
    public void barajarTablero(){
        for(int i = 0; i <= columnas; i++){
            for(int j=0; i<= filas; j++){
                try{
                    randomNumber = (int) Math.floor(Math.random()*(baraja.size()-1));
                    cartaAIntroducir = baraja.remove(randomNumber);
                    super.add(cartaAIntroducir, i, j);
                } catch(Exception e){}
           }
        }
    }
}
