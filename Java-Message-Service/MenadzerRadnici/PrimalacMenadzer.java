/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menadzerradnici;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 *
 * @author Radovan
 */
public class PrimalacMenadzer extends Thread{
    public void run(){
        JMSContext jmsContext = MenadzerMain.connectionFactory.createContext();
        JMSConsumer jmsConsumer = jmsContext.createConsumer(MenadzerMain.queue);
        
        while(true){
            Message msg = jmsConsumer.receive();
            
            if(msg instanceof TextMessage){
                try {
                    TextMessage txtMsg = (TextMessage)msg;
                    String sadrzaj = txtMsg.getText();
                    int id = txtMsg.getIntProperty("idR");
                    System.out.println("Primljena poruka od " + id + " sadrzine: " + sadrzaj);
                } catch (JMSException ex) {
                    Logger.getLogger(PrimalacMenadzer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
