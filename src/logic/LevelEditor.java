/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author Dani
 */
public class LevelEditor {

    public static void main(String[] args) {
        Nivel nivel11 = new Nivel(10, 6, 4, 30, 80);
        //serialize(nivel11, "nivel11.ser");
        unserialize("nivel11.ser");

    }

    public static void serialize(Object o, String fileName) {
        FileOutputStream fos;
        ObjectOutputStream oos;
        BufferedOutputStream bos;

        try {
            fos = new FileOutputStream("." + File.separator + "src" + File.separator + "levels" + File.separator + fileName);
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);

            oos.writeObject(o);
            oos.close();
            bos.close();
            fos.close();
            System.out.println("Level saved!");
        } catch (IOException ioe) {
            System.out.println("Cannot create byte stream from object");
            ioe.printStackTrace();
        }
    }

    public static Object unserialize(String fileName) {
        Nivel nivel = null;
        try {
            FileInputStream fileIn = new FileInputStream("." + File.separator + "src" + File.separator + "levels" + File.separator + fileName);
            BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
            ObjectInputStream in = new ObjectInputStream(bufferedIn);
            nivel = (Nivel) in.readObject();
            in.close();
            bufferedIn.close();
            fileIn.close();

            System.out.println("Loaded level:\n" + nivel);
        } catch (IOException i) {
            System.out.println("Unable to read level data");
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }

        return nivel;
    }
}

class BarajaContainer {

    Baraja baraja;
    Image image;

    public BarajaContainer() throws URISyntaxException {
        image = new Image(getClass().getResource("/images/card.png").toURI().toString(), 50, 50, false, false);
        List<Categoria> cats = new ArrayList<>();
        cats.add(new Categoria("FRUTAS"));
        List<Carta> cartas = new ArrayList<Carta>();
        cartas.add(new Carta(0, image, image));
        baraja = new Baraja("baraja1", cartas, image, cats);
    }

    public Baraja getBaraja() {
        return this.baraja;
    }

    public Image getImage() {
        return this.image;
    }

}
