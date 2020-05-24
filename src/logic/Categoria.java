/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Jesús Yoel
 */

//@XmlRootElement(name="categoria")
@XmlType(propOrder={"name"})
public class Categoria{
    
    private String name;
    
    public Categoria(){
        
    }
    
    public Categoria(String name){
        this.name = name;
    }
    
    @XmlElement
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
   
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Categoria other = (Categoria) obj;
        if (!Objects.equals(this.name.toLowerCase(), other.name.toLowerCase())) {
            return false;
        }
        return true;
    }
    
}



