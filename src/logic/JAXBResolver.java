/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author David
 */
public class JAXBResolver {
    private JAXBContext context; 
    public JAXBResolver() {
        try { 
            this.context = JAXBContext.newInstance(

            Coleccion.class 

     ); 
        } catch (JAXBException ex) { 
            throw new RuntimeException(ex); 
        } 
    }

    public JAXBContext getCtx() {
        return context;
    }
    
    public Unmarshaller createUnmarshaller() throws JAXBException{
        return context.createUnmarshaller();
    }
    
    public Marshaller createMarshaller() throws JAXBException{
        return context.createMarshaller();
    }
    
    public Coleccion getColeccionFromXML() throws JAXBException{
        return (Coleccion) createUnmarshaller().unmarshal(new File("coleccion.xml"));
    }
    
    public void setColeccionToXML(Coleccion coleccion) throws JAXBException, IOException{
        createMarshaller().setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        createMarshaller().marshal(coleccion, new FileWriter("coleccion.xml"));
    }
}
