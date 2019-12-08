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
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;

/**
 *
 * @author ivanrakonjac
 */
public class TopicProducer {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "myTopic")
    private static Topic topic;
    
    public static void main(String[] args){
       JMSContext jmsContext = connectionFactory.createContext();
       JMSProducer jmsProducer = jmsContext.createProducer();
        
        for(int i=0;i<100;i++){
            String message = "Message " + i;
            
            jmsProducer.send(topic, message);
            
            System.out.println("Poslata poruka: " + message);
            
           try {
               Thread.sleep(1000);
           } catch (InterruptedException ex) {
               Logger.getLogger(TopicProducer.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }
}

