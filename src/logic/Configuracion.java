/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Jesús Yoel
 */
public class Configuracion {

    private static Configuracion configuracionSingleInstance = null;

    // Can be changed
    private String cancionPartida;
    private String sonidoCorrecto;
    private String sonidoFallo;
    private String sonidoGiro;
    private int anchuraTablero;
    private int larguraTablero;
    private int tiempoTurno;
    private int tiempoPartida;
    private double tiempoVerError;
    private int tiempoCartasInicio;
    private boolean limitePartida;
    private String cartaPartida;
    private boolean sinMusica;
    private boolean mostrarCartasInicio;
    private Baraja barajaNormal;
    private Baraja barajaCategoria;

    // Default values
    public static final String CANCION_PARTIDA_DEFAULT = "/music/Cancion1.mp3";
    public static final String SONIDO_CORRECTO_DEFAULT = "/music/correct.mp3";
    public static final String SONIDO_FALLO_DEFAULT = "/music/fail.mp3";
    public static final String SONIDO_GIRO_DEFAULT = "/music/flip.wav";
    public static final int ANCHURA_TABLERO_DEFAULT = 4;
    public static final int LARGURA_TABLERO_DEFAULT = 6;
    public static final int TIEMPO_TURNO_DEFAULT = 5;
    public static final int TIEMPO_PARTIDA_DEFAULT = 60;
    public static final double TIEMPO_VER_ERROR_DEFAULT = 0.500;
    public static final int TIEMPO_CARTAS_INICIO_DEFAULT = 2;
    public static final boolean LIMITE_PARTIDA_DEFAULT = true;
    public static final String CARTA_PARTIDA_DEFAULT = "fruit";
    public static final boolean SIN_MUSICA_DEFAULT = false;
    public static final boolean MOSTRAR_CARTAS_INICIO_DEFAULT = false;

    private Configuracion(String song, String correctSound, String failSound, 
            String flipSound, int anchura, int largura, int turno, int partida, 
            double cartaError, int cartasPrincipio, boolean limite, String carta, 
            boolean noMusic, boolean cartasInicio) {
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
        tiempoCartasInicio = cartasPrincipio;
        cartaPartida = carta;
        sinMusica = noMusic;
        mostrarCartasInicio = cartasInicio;
        // May change depending on where we're going to end up putting the card generation
        this.barajaNormal = generarBaraja(larguraTablero * anchuraTablero, CARTA_PARTIDA_DEFAULT,new Categoria("FRUTAS"), "Baraja Default");
        this.barajaCategoria = generarBarajaCategoria(larguraTablero * anchuraTablero, getCartaPartida(), "Baraja Categoria Default");
    }

    private Configuracion(String song, String correctSound, String failSound,
            String flipSound, int anchura, int largura, int turno, int partida,
            double cartaError, int cartasPrincipio, boolean limite, String carta,
            boolean noMusic, boolean cartasInicio, Baraja barajaNormal, Baraja barajaCategoria) {
        this(song, correctSound, failSound, flipSound, anchura, largura, turno,
                partida, cartaError, cartasPrincipio, limite, carta, noMusic,
                cartasInicio);

        this.barajaNormal = barajaNormal;
        this.barajaCategoria = barajaCategoria;
    }

    /**
     *
     * @return a single instance of this class
     */
    public static Configuracion getInstance() {
        if (configuracionSingleInstance == null) {
            configuracionSingleInstance = new Configuracion(
                    CANCION_PARTIDA_DEFAULT, SONIDO_CORRECTO_DEFAULT,
                    SONIDO_FALLO_DEFAULT, SONIDO_GIRO_DEFAULT, ANCHURA_TABLERO_DEFAULT,
                    LARGURA_TABLERO_DEFAULT, TIEMPO_TURNO_DEFAULT, TIEMPO_PARTIDA_DEFAULT,
                    TIEMPO_VER_ERROR_DEFAULT, TIEMPO_CARTAS_INICIO_DEFAULT,
                    LIMITE_PARTIDA_DEFAULT, CARTA_PARTIDA_DEFAULT, SIN_MUSICA_DEFAULT,
                    MOSTRAR_CARTAS_INICIO_DEFAULT);
        }

        return configuracionSingleInstance;
    }

    /**
     * Sets the default values for all parameters
     */
    public void setDefaults() {
        cancionPartida = CANCION_PARTIDA_DEFAULT;
        sonidoCorrecto = SONIDO_CORRECTO_DEFAULT;
        sonidoFallo = SONIDO_FALLO_DEFAULT;
        sonidoGiro = SONIDO_GIRO_DEFAULT;
        anchuraTablero = ANCHURA_TABLERO_DEFAULT;
        larguraTablero = LARGURA_TABLERO_DEFAULT;
        tiempoTurno = TIEMPO_TURNO_DEFAULT;
        tiempoPartida = TIEMPO_PARTIDA_DEFAULT;
        tiempoVerError = TIEMPO_VER_ERROR_DEFAULT;
        tiempoCartasInicio = TIEMPO_CARTAS_INICIO_DEFAULT;
        limitePartida = LIMITE_PARTIDA_DEFAULT;
        cartaPartida = CARTA_PARTIDA_DEFAULT;
        sinMusica = SIN_MUSICA_DEFAULT;
        mostrarCartasInicio = MOSTRAR_CARTAS_INICIO_DEFAULT;
        this.barajaNormal = generarBaraja(larguraTablero * anchuraTablero, CARTA_PARTIDA_DEFAULT,new Categoria("FRUTAS"), "Baraja Default");
        this.barajaCategoria = generarBarajaCategoria(larguraTablero * anchuraTablero, getCartaPartida(), "Baraja Default Categoria");
    }

    /**
     * Sets new values for the configuration
     *
     * @param song song to play when the game is running
     * @param correctSound sound to play when a correct pair is found
     * @param failSound sound to play when an incorrect pair is found
     * @param flipSound sound to play when flipping a card
     * @param anchura width of the board
     * @param largura length of the board
     * @param turno duration of each turn
     * @param partida duration of a game
     * @param cartaError time in seconds an incorrect pair can be seen before
     * turning it back around
     * @param cartasPrincipio time in seconds cards are shown at the beginning
     * of a game
     * @param limite whether game time limit is active
     * @param carta type of deck
     * @param noMusic whether music should be playing
     * @param cartasInicio whether cards should be shown at the beginning of the
     * game
     */
    public void setValues(String song, String correctSound, String failSound,
            String flipSound, int anchura, int largura, int turno, int partida,
            int cartaError, int cartasPrincipio, boolean limite, String carta,
            boolean noMusic, boolean cartasInicio) {
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
        tiempoCartasInicio = cartasPrincipio;
        cartaPartida = carta;
        sinMusica = noMusic;
        mostrarCartasInicio = cartasInicio;
    }

    public void setValues(String song, String correctSound, String failSound,
            String flipSound, int anchura, int largura, int turno, int partida,
            int cartaError, int cartasPrincipio, boolean limite, String carta,
            boolean noMusic, boolean cartasInicio, Baraja barajaNormal,
            Baraja barajaCategoria) {
        this.setValues(song, correctSound, failSound, flipSound, anchura,
                largura, turno, partida, cartaError, cartasPrincipio, limite,
                carta, noMusic, cartasInicio);
        this.barajaNormal = barajaNormal;
        this.barajaCategoria = barajaCategoria;
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

    public final String getCartaPartida() {
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

    public double getTiempoVerError() {
        return tiempoVerError;
    }

    public void setTiempoVerError(double tiempo) {
        this.tiempoVerError = tiempo;
    }
    
    public Baraja getBarajaNormal() {
        return barajaNormal;
    }

    public void setBarajaNormal(Baraja barajaNormal) {
        this.barajaNormal = barajaNormal;
    }

    public Baraja getBarajaCategoria() {
        return barajaCategoria;
    }

    public void setBarajaCategoria(Baraja barajaCategoria) {
        this.barajaCategoria = barajaCategoria;
    }

    
    // DON'T FORGET TO CHANGE THIS WHEN DECK GENERATION WORKS PROPERLY
    public final Baraja generarBaraja(int numCartas, String cartaModelo, Categoria categoria, String nombreBaraja) {
        Baraja barajaCartas = null;
        if (numCartas % 2 != 0) {
            System.out.println("*****************************************");
            System.out.println("Uneven number of cards");
            System.out.println("*****************************************");
            return null;
        }
        try {

            List<Carta> baraja = new ArrayList<>();
//        File deckCard = new File("." + File.separator + "images" + File.separator + "card.png");
//        String cardImages = "." + File.separator + "images" + File.separator + cartaModelo;
            //System.out.println(this.getClass().getResource("/images/card.png"));
            Image deckCardImage = new Image(this.getClass().getResource("/images/card.png").toURI().toString(), 50, 50, false, false);

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < numCartas / 2; j++) {
                    //System.out.println(this.getClass().getResource("/images/" + cartaModelo + (j + 1) + ".png"));
                    Image currentCardImage = new Image(this.getClass().getResource("/images/" + cartaModelo + (j + 1) + ".png").toURI().toString(), 50, 50, false, false);

                    Carta carta = new Carta(j, currentCardImage, deckCardImage, new Categoria(categoria.getName()));

                    // Add event to detect when a Carta is clicked
                    baraja.add(carta);
                }
            }
            barajaCartas = new Baraja(nombreBaraja, baraja, deckCardImage);
            barajaCartas.setCategorias(Arrays.asList(categoria));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (barajaCartas == null) {
            System.out.println("BARAJA IS NULL!!!!");
        }
        return barajaCartas;
    }
    
    // CAMBIO CATEGORIA
    public Baraja generarBarajaCategoria(int numCartas, String cartaModelo, String nombreBaraja) {
        if (numCartas % 2 != 0) {
            return null;
        }
        Baraja laBaraja = null;
        try{
        List<Carta> baraja = new ArrayList<Carta>();
        String cardImages;
        Image deckCardImage = new Image(this.getClass().getResource("/images/card.png").toURI().toString(), 50, 50, false, false);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < numCartas / 2; j++) {
                if (j % 2 == 0) {
                    cardImages = "/images/fruit";
                } else {
                    cardImages = "/images/card";
                }
                Image currentCardImage = new Image(this.getClass().getResource(cardImages + (j + 1) + ".png").toURI().toString(), 50, 50, false, false);
                Carta carta = new Carta(j, currentCardImage, deckCardImage);
                if (j % 2 == 0) {
                    carta.setCategoria(new Categoria("FRUTAS"));
                } else {
                    carta.setCategoria(new Categoria("PAJAROS"));
                }
                baraja.add(carta);
            }

        }
        laBaraja = new Baraja(nombreBaraja,baraja,deckCardImage);
        laBaraja.setCategorias(Arrays.asList(new Categoria("FRUTAS"), new Categoria("PAJAROS")));
        
        }catch(URISyntaxException e){e.printStackTrace();}
        return laBaraja;
    }
}
