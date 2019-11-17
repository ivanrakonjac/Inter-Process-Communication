/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmsclient;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

/**
 *
 * @author IvanRakonjac
 */
public class Client {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "myQueue")
    private static Queue queue;
    
    public static void main(String[] args) {
        JMSContext jmsContext = connectionFactory.createContext();
        
        JMSProducer jmsProducer = jmsContext.createProducer();
        
        int i=0;
        
        while(true){
            String message = "Hello JMS - " + i;

            System.out.println("Poruka: " + message);

            jmsProducer.send(queue, message);

            System.out.println("Poslata poruka uspesno - " + i);

            i++;

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
