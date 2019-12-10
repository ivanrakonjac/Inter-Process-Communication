/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vesti;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author lab
 */
public class Urednik {
    
    public static int brojac=0;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public static ConnectionFactory connectionFactorty;
    
    @Resource(lookup = "myTopic")
    public static Topic topic;
    
    public static void main(String[] args){
        JMSContext jmsContext = connectionFactorty.createContext();
        JMSProducer jmsProducer = jmsContext.createProducer();
        
        while(true){
            
            String tip = "sokantno";
            
            try {
                
                int id = brojac%2;
                
                switch(id){
                    case 0:
                        tip="sokantno";
                        break;
                    case 1:
                        tip = "fotoVideo";
                        break;  
                }
                
                TextMessage txtMsg = jmsContext.createTextMessage();
                txtMsg.setText("Vesti " + ++brojac);
                txtMsg.setStringProperty("tipVesti", tip);
                
                jmsProducer.send(topic, txtMsg);
                
                System.out.println("Poslata poruka: " + txtMsg.getText() + " tip = " + txtMsg.getStringProperty("tipVesti"));
                
                Thread.sleep(1000);
                
            } catch (JMSException | InterruptedException ex) {
                Logger.getLogger(Urednik.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
