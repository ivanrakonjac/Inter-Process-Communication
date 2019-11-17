/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmsclient;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.TextMessage;

/**
 *
 * @author IvanRakonajc
 */
public class MessageBrowser {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "myQueue")
    private static Queue queue;

    
    public static void main(String[] args) {
        
        JMSContext jmsContext = connectionFactory.createContext();
        
        QueueBrowser queueBrowser = jmsContext.createBrowser(queue);
        
        Enumeration jmsBrowser;
        
        try {
            
            jmsBrowser = queueBrowser.getEnumeration();
        
            while(jmsBrowser.hasMoreElements()){
                TextMessage txtMsg = (TextMessage)jmsBrowser.nextElement();
                System.out.println("Pronadjena poruka - " + txtMsg.getText());
            }
            
        } catch (JMSException ex) {
            Logger.getLogger(MessageBrowser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
