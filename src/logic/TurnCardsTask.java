/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javafx.animation.RotateTransition;
import javafx.concurrent.Task;

/**
 *
 * @author Dani
 */
public class TurnCardsTask<E> extends Task<E> {

    private Carta cartaAGirar;
    private double turnDelay;
    private RotateTransition animation;

    public TurnCardsTask(Carta carta, double delay) {
        cartaAGirar = carta;
        turnDelay = delay;
        
    }

    @Override
    protected E call() {
        try {
            Thread.sleep(Math.round(turnDelay * 1000));
        } catch (InterruptedException ie) {
            System.out.println("Error sleeping before turning cards");
            ie.printStackTrace();
        }
        
        return null;
    }
}
