/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menadzerradnici;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;

/**
 *
 * @author Radovan
 */
public class PosiljalacMenadzer extends Thread{

public void run(){
    JMSContext jmsContext = MenadzerMain.connectionFactory.createContext();
    JMSProducer jMSProducer = jmsContext.createProducer();
    
    int brojac=0;
    int id=0;
    
    while (true){
        ObjectMessage objectMessage = jmsContext.createObjectMessage();
        
        try {
            
            objectMessage.setObject(new Zadatak(++brojac));
            objectMessage.setStringProperty("imeZadatka", "test");
            if(brojac%2==0){ 
                id=2;
                objectMessage.setIntProperty("idR", id);
                //objectMessage.setStringProperty("ïmeZadatka", "sabiranje");
                System.out.println("Poslata poruka id = " + id + " imeZadatka = " + objectMessage.getStringProperty("imeZadatka"));
            }
            else{
                id=1;
                objectMessage.setIntProperty("idR", id);
                //objectMessage.setStringProperty("ïmeZadatka", "mnozenje");
                System.out.println("Poslata poruka id = " + id + " imeZadatka = " + objectMessage.getStringProperty("imeZadatka"));
            }
            
            jMSProducer.send(MenadzerMain.topic,objectMessage);
                  
            Thread.sleep(1000);
            
        } catch (JMSException | InterruptedException ex) {
            Logger.getLogger(PosiljalacMenadzer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
    
}
