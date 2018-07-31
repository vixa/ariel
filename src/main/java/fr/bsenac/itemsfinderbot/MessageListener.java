/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.bsenac.itemsfinderbot;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 *
 * @author vixa
 */
public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            System.out.println("Message received on " + event.getChannel().getName());
            TextChannel c = event.getTextChannel();
            if (ItemsDatabase.getDatabase().containsChannel(c)) {
                System.out.println("Channel contains one or more items");
                Rarity r = Rarity.getRandomRarity();
                if (!r.equals(Rarity.DEFAULT)) {
                    if (ItemsDatabase.getDatabase().canGetItem(c, r)) {
                        System.out.println("The player will get a " + r + " item");
                        String item = ItemsDatabase.getDatabase().getItem(c, r);
                        String msg = event.getMember().getEffectiveName()
                                + " vient de trouver: " + item;
                        c.sendMessage("```" + msg + "```").queue();
                    }
                }
            }
        }
    }

}
