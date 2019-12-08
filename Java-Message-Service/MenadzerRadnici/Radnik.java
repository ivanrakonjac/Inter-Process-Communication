/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menadzerradnici;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author Radovan
 */
public class Radnik {
        
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "menadzerTopic")
    private static Topic topic;
    
    @Resource(lookup = "menadzerQueue")
    private static Queue queue;

    public static void main(String[] args){
        
        int id=2;
        
        JMSContext jmsContext = connectionFactory.createContext();
        jmsContext.setClientID("Radnik" + id);
        JMSConsumer jmsConsumer = jmsContext.createDurableConsumer(topic, "Prijava"+id, "idR = " +id, false);
        JMSProducer jMSProducer = jmsContext.createProducer();
        
        while(true){
            System.out.println("Cekam poruku...");
            Message msg = jmsConsumer.receive();

            if(msg instanceof ObjectMessage){
                ObjectMessage objectMessage = (ObjectMessage)msg;

                try {
                    Zadatak zadatak = (Zadatak)objectMessage.getObject();
                    String imeZad = objectMessage.getStringProperty("imeZadatka");
                    
                    System.out.println("Primljena poruka: " + imeZad + " zadatak.id = " + zadatak.getId());
                    
                    TextMessage txtMsg = jmsContext.createTextMessage();
                    txtMsg.setText("Odradio sam " + imeZad);
                    txtMsg.setIntProperty("idR", id);
                    jMSProducer.send(queue, txtMsg);
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Radnik.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
}
