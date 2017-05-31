/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecttwitter;

import javax.swing.JOptionPane;
import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


/**
 *
 * @author ricky_000
 */
public class AppTwitter {
    
    public Twitter twitter;
/**
 * Usamos ConfigurationBuilder en el constructor con los token para que al iniciar la aplicacion 
 * cargue los datos de la cuenta.
 */
    public AppTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("***********")
                .setOAuthConsumerSecret("*********")
                .setOAuthAccessToken("***********")
                .setOAuthAccessTokenSecret("*************");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = (Twitter) tf.getInstance();
    }

    /**
     * Método para ver la pagina de noticias en tiempo real.
     */
    public void verTimeline() {
        try {
            Paging pagina = new Paging();
            pagina.setCount(40);
            ResponseList listado = twitter.getHomeTimeline(pagina);
            for (int i = 0; i < listado.size(); i++) {
                System.out.println(listado.get(i).toString());
            }
        } catch (TwitterException ex) {
            System.out.println("Error al ver la linea de tiempo");
        }
    }

    /**
     * Método para añadir twitts al muro.
     */
    public void postearTweet() {
        try {
            twitter.updateStatus(JOptionPane.showInputDialog("Mensaje para twittear"));
        } catch (TwitterException ex) {
            System.out.println("Error post");
        }
    }

    /**
     * Método que busca twetts.
     * @param text 
     */
    public void buscar(String text) {
        try {
            Query query = new Query("#" + text);
            QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {
                System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
            }
        } catch (TwitterException ex) {
            System.out.println("Error al buscar");
        }
    }

    /**
     * Método para twittear mensajes.
     * @param user
     * @param msg 
     */
    public void mensajeDirecto(String user, String msg) {
        DirectMessage message;
        try {
            message = twitter.sendDirectMessage("@" + user, msg);
            System.out.println("Sent: " + message.getText() + " to @" + message.getRecipientScreenName());
        } catch (TwitterException ex) {
            System.out.println("Error mensaje directo");
        }
    }

}