/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import logic.Baraja;
import logic.Coleccion;
import logic.JAXBResolver;

/**
 *
 * @author David
 */
public class JAXBReader {
    
    public static void main(String[] args) throws JAXBException {
        JAXBResolver dr = new JAXBResolver();
        JAXBContext context = dr.getCtx();
       
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Coleccion coleccion = (Coleccion) unmarshaller.unmarshal(new File(System.getProperty("user.home") + File.separator + "coleccion.xml"));
        
//        System.out.println(coleccion.getBarajas());
    }
    
}
