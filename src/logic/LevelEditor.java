/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author Dani
 */
public class LevelEditor {
    public static void main(String[] args){
        Nivel nivel1 = new Nivel(1, 3, 2, 20, 10);
        serialize(nivel1, "nivel1.ser");
        //("nivel1.ser");
    }
    
    
    public static void serialize(Object o, String fileName){
        FileOutputStream fos;
        ObjectOutputStream oos;
        
        try{
            fos = new FileOutputStream("." + File.separator + "src" + File.separator + "levels" + File.separator + fileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.close();
            fos.close();
            System.out.println("Level saved!");
        } catch(IOException ioe){
            System.out.println("Cannot create byte stream from object");
            ioe.printStackTrace();
        }
    }
    
    public static Object unserialize(String fileName){
        Nivel nivel = null;
         try {
         FileInputStream fileIn = new FileInputStream("." + File.separator + "src" + File.separator + "levels" + File.separator + fileName);
         ObjectInputStream in = new ObjectInputStream(fileIn);
         nivel = (Nivel) in.readObject();
         in.close();
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
