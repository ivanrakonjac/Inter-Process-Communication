/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmsclient;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 *
 * @author IvanRakonjac
 */
public class MessageReceiver {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "myQueue")
    private static Queue queue;
    
    
    public static void main(String[] args) {
        JMSContext jmsContext = connectionFactory.createContext();
        
        JMSConsumer jmsConsumer = jmsContext.createConsumer(queue);
        
        while(true){
        
            System.out.println("Receiving Messages...");

            String message = jmsConsumer.receiveBody(String.class);

            System.out.println("Primljena poruka:" + message);
           
        }
    }
}
