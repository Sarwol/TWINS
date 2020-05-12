/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;

/**
 *
 * @author Dani
 */
public class Nivel implements Serializable {
    private int ID;
    private int boardWidth;
    private int boardHeight;
    private int time;
    private int minPoints;
    
    /**
     * Creates a new level with the specified parameters
     * @param ID            ID of the level
     * @param boardWith     width of the board
     * @param boardHeight   height of the board
     * @param time          total duration of the game in seconds
     * @param minPoints     amount of points required to win.
     */
    public Nivel(int ID, int boardWith, int boardHeight, int time, int minPoints) {
        this.ID = ID;
        this.boardWidth = boardWith;
        this.boardHeight = boardHeight;
        this.time = time;
        this.minPoints = minPoints;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public void setMinPoints(int minPoints) {
        this.minPoints = minPoints;
    }
    
    public int size(){
        return boardWidth * boardHeight;
    }
   
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Nivel)) return false;
        Nivel other = (Nivel) o;
        return this.ID == other.ID;
    }
    @Override
    public String toString(){
        return "Level\t" + ID + "(" + boardWidth + "x" + boardHeight + ")\t" +
                "t: " + time + ", minPoints: " + minPoints;
    }
}
