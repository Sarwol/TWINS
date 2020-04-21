/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author davit
 */
public class Puntuacion {
    private int puntos;
    
    private final int CARTAS_CORRECTAS = 10;
    private final int CARTAS_INCORRECTAS = 5;
    

    public Puntuacion(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntuacionFinal) {
        this.puntos = puntos;
    }
    
    public int sumarPuntos(){
        return puntos += CARTAS_CORRECTAS;
    }
    
     public int restarPuntos(){
        return puntos -= CARTAS_INCORRECTAS;
    }

    @Override
    public String toString() {
        return "Puntuacion{" + "puntos=" + puntos + '}';
    }
}
    