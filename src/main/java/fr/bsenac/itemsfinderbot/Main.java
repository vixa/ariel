/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.bsenac.itemsfinderbot;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

/**
 *
 * @author vixa
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws BotTokenException {
        if(args.length < 2){
            throw new BotTokenException("No token provided !");
        }
        System.out.println("Starting bot");
        String token = args[0], itemsFile = args[1];
        ItemsDatabase.getDatabase().fillDatabase(itemsFile);
        
        try{
            JDA jda = new JDABuilder(AccountType.BOT).setToken(token)
                    .addEventListener(new MessageListener()).buildBlocking();
        } catch (LoginException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
