/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author Jesús Yoel
 */
public class Configuracion {
    
    private String cancionPartida;
    private String sonidoCorrecto;
    private String sonidoFallo;
    private String sonidoGiro;
    private int anchuraTablero;
    private int larguraTablero;
    private int tiempoTurno;
    private int tiempoPartida;
    private int tiempoVerError;
    private int tiempoCartasInicio;
    private boolean limitePartida;
    private String cartaPartida;
    private boolean sinMusica;
    private boolean mostrarCartasInicio;
   
    
    public Configuracion(String song, String correctSound, String failSound, String flipSound,int anchura, int largura, int turno, int partida, int cartaError,
            int cartasPincipio,boolean limite, String carta, boolean noMusic, boolean cartasInicio){
           limitePartida = limite;
           cancionPartida = song;
           sonidoCorrecto = correctSound;
           sonidoFallo = failSound;
           sonidoGiro = flipSound;
           anchuraTablero = anchura;
           larguraTablero = largura;
           tiempoTurno = turno;
           tiempoPartida = partida;
           tiempoVerError = cartaError;
           tiempoCartasInicio = cartasPincipio;
           cartaPartida = carta;
           sinMusica = noMusic;
           mostrarCartasInicio = cartasInicio;
     }

    public int getTiempoCartasInicio() {
        return tiempoCartasInicio;
    }

    public void setTiempoCartasInicio(int tiempoCartasInicio) {
        this.tiempoCartasInicio = tiempoCartasInicio;
    }

    public boolean isMostrarCartasInicio() {
        return mostrarCartasInicio;
    }

    public void setMostrarCartasInicio(boolean mostrarCartasInicio) {
        this.mostrarCartasInicio = mostrarCartasInicio;
    }
    
    public boolean isSinMusica() {
        return sinMusica;
    }

    public void setSinMusica(boolean sinMusica) {
        this.sinMusica = sinMusica;
    }
    
    public String getCartaPartida() {
        return cartaPartida;
    }

    public void setCartaPartida(String cartaPartida) {
        this.cartaPartida = cartaPartida;
    }

    public boolean isLimitePartida() {
        return limitePartida;
    }

    public void setLimitePartida(boolean limitePartida) {
        this.limitePartida = limitePartida;
    }

    public String getCancionPartida() {
        return cancionPartida;
    }

    public void setCancionPartida(String cancionPartida) {
        this.cancionPartida = cancionPartida;
    }

    public String getSonidoCorrecto() {
        return sonidoCorrecto;
    }

    public void setSonidoCorrecto(String sonidoCorrecto) {
        this.sonidoCorrecto = sonidoCorrecto;
    }

    public String getSonidoFallo() {
        return sonidoFallo;
    }

    public void setSonidoFallo(String sonidoFallo) {
        this.sonidoFallo = sonidoFallo;
    }

    public String getSonidoGiro() {
        return sonidoGiro;
    }

    public void setSonidoGiro(String sonidoGiro) {
        this.sonidoGiro = sonidoGiro;
    }

    public int getAnchuraTablero() {
        return anchuraTablero;
    }

    public void setAnchuraTablero(int anchuraTablero) {
        this.anchuraTablero = anchuraTablero;
    }

    public int getLarguraTablero() {
        return larguraTablero;
    }

    public void setLarguraTablero(int larguraTablero) {
        this.larguraTablero = larguraTablero;
    }

    public int getTiempoTurno() {
        return tiempoTurno;
    }

    public void setTiempoTurno(int tiempoTurno) {
        this.tiempoTurno = tiempoTurno;
    }

    public int getTiempoPartida() {
        return tiempoPartida;
    }

    public void setTiempoPartida(int tiempoPartida) {
        this.tiempoPartida = tiempoPartida;
    }

    public int getTiempoVerError() {
        return tiempoVerError;
    }

    public void setTiempoVerError(int tiempoVerError) {
        this.tiempoVerError = tiempoVerError;
    }
    
}
