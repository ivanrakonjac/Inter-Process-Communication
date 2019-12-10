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
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import static vesti.Urednik.connectionFactorty;

/**
 *
 * @author lab
 */
public class Citaoc {
     @Resource(lookup = "jms/__defaultConnectionFactory")
    public static ConnectionFactory connectionFactorty;
    
    @Resource(lookup = "myTopic")
    public static Topic topic;
    
    public static void main(String[] args){
        JMSContext jmsContext = connectionFactorty.createContext();
        JMSConsumer jmsConsumer = jmsContext.createConsumer(topic, "tipVesti = 'sokantno'");
    
        while(true){
            System.out.println("Cekam poruku...");
            
            TextMessage txtMsg =(TextMessage) jmsConsumer.receive();
            
            try {
                System.out.println("primljena poruka " + txtMsg.getText() + " tip: " + txtMsg.getStringProperty("tipVesti"));
                Thread.sleep(3000);
            } catch (JMSException | InterruptedException ex) {
                Logger.getLogger(Citaoc.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
        
}
