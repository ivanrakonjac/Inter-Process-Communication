/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menadzerradnici;

import java.io.Serializable;

/**
 *
 * @author Radovan
 */
public class Zadatak implements Serializable{
    int id;

    public Zadatak() {
        this.id = -1;
    }
    
    public Zadatak(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    
}
