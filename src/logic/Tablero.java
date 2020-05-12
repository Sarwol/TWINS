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
    
    public Tablero(){
        super();
    }
    
    public Tablero(int filas, int columnas) {
        super();
        this.filas = filas;
        this.columnas = columnas;
        baraja = new ArrayList();
    }
    
    public Tablero(int filas, int columnas, ArrayList<Carta> baraja){
        this(filas, columnas);
        this.baraja = baraja;
    }
     
    /**
     * Distribuye las cartas de la baraja actual de manera aleatoria por el
     * tablero. 
     * La baraja debe tener tantas cartas como posiciones hay en el 
     * tablero.
     * La baraja debe estar compuesta solamente por cartas que tengan su 
     * respectiva pareja (IMPLEMENTAR)
     * @return retorna el tamaño de la baraja si esta es correcta.
     * Retorna -1 si no tiene el número adecuado de cartas.
     * Retorna -2 si alguna carta no tiene pareja (IMPLEMENTAR)
     */
    public int barajarTablero(){
        
        if(baraja.size() != filas * columnas){  // comprobar tamaño correcto
            return -1;
        }
        
        List<Carta> copiaBaraja = clonarBaraja(baraja);
        Carta cartaAIntroducir = null;
        
        
        for(int i = 0; i < columnas; i++){
            for(int j = 0; j < filas; j++){
                try{
                    int randomNumber = (int) Math.floor(Math.random()*(copiaBaraja.size()));
                    cartaAIntroducir = copiaBaraja.remove(randomNumber);
                    super.add(cartaAIntroducir, i, j);
                } catch(Exception e){
                    e.printStackTrace();
                }
           }
        }
        return baraja.size();   // baraja correcta
    }
    
    /**
     * Método auxiliar para copiar los elementos de una lista a otra.
     * @param barajaOriginal
     * @return Copia de la baraja original
     */
    public List<Carta> clonarBaraja(List<Carta> barajaOriginal){
        List<Carta> copiaBaraja = new ArrayList<Carta>();
        for(Carta carta : barajaOriginal){
            copiaBaraja.add(carta);
        }
        
        return copiaBaraja;
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
    
    public void girarTodasCartas(){
        for(int i =0; i < baraja.size();i++){
            baraja.get(i).turn();
        }
    }
}
