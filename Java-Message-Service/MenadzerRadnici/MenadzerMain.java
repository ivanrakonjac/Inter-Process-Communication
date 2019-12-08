/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menadzerradnici;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 *
 * @author Radovan
 */
public class MenadzerMain {
    
    @Resource(lookup = "menadzerQueue")
    static Queue queue;
    @Resource(lookup = "menadzerTopic")
    static Topic topic;
    @Resource(lookup = "jms/__defaultConnectionFactory")
    static ConnectionFactory connectionFactory;
    
    public static void main(String[] args){
        PosiljalacMenadzer posMen = new PosiljalacMenadzer();
        posMen.start();
        PrimalacMenadzer primMen = new PrimalacMenadzer();
        primMen.start();
    }
}
