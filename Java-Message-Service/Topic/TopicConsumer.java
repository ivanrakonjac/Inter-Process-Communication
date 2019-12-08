/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topic;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author Radovan
 */
public class TopicConsumer {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "myTopic")
    private static Topic topic;
    
    public static void main(String[] args){
        JMSContext jmsContext = connectionFactory.createContext();
        JMSConsumer jmsConsumer = jmsContext.createConsumer(topic); //NoDurable & NoShared
        
        while(true){
            System.out.println("Receiving Messages...");
            
            Message message = jmsConsumer.receive();
            
             if(message instanceof TextMessage){
                 
                try {
                    System.out.println("Primljena je poruka: " + ((TextMessage)message).getText());
                } catch (JMSException ex) {
                    Logger.getLogger(TopicConsumer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            else{
                System.out.println("Primljena je prazna poruka.");
                break;
            }
        }
    }
}
