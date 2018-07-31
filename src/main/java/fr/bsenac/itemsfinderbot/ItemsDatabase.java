/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.bsenac.itemsfinderbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 *
 * @author vixa
 */
public class ItemsDatabase {

    private final static ItemsDatabase DATABASE = new ItemsDatabase();

    public static ItemsDatabase getDatabase() {
        return DATABASE;
    }

    /**
     * <Channel name, <Rarity, Items>>
     */
    private final Map<String, Map<Rarity, List<String>>> itemsToFound;

    public ItemsDatabase() {
        itemsToFound = new HashMap<>();
    }

    /**
     * Get a random item on a channel, based on rarity Suppose than the channel
     * contains items
     *
     * @param c the channel
     * @param r the rarity of the item to get
     * @return a random item
     */
    public String getItem(TextChannel c, Rarity r) {
        assert (containsChannel(c));
        List<String> items = itemsToFound.get(c.getName()).get(r);
        int random = new Random().nextInt(items.size());
        return items.get(random);
    }

    /**
     * Check if we can found items in a channel
     *
     * @param c the channel to check
     * @return true if we can found items, false else
     */
    public boolean containsChannel(TextChannel c) {
        return itemsToFound.containsKey(c.getName());
    }

    public boolean canGetItem(TextChannel c, Rarity r) {
        return containsChannel(c) ? 
                itemsToFound.get(c.getName()).containsKey(r) : false;
    }

    /**
     * Fill the database with a textFile with a special format
     *
     * @param path the path of the file
     */
    public void fillDatabase(String path) {
        File f = new File(path);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            fillDatabase(br);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ItemsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ItemsDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ItemsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void fillDatabase(BufferedReader br) throws IOException {
        System.out.println("Starting filling database");
        String currentChannel = "";
        while (br.ready()) {
            String s = br.readLine();
            if (s.startsWith("#") && s.length() > 2) { //If it is a channel
                String name = s.substring(1);
                itemsToFound.put(name, new HashMap<>());
                currentChannel = name;
                System.out.println("Channel selected: " + currentChannel);
            } else {
                String[] itemAndRarity = s.split(":");
                String item = itemAndRarity[0];
                Rarity rarity = Rarity.getRarity(itemAndRarity[1]);

                if (!itemsToFound.get(currentChannel).containsKey(rarity)) {
                    itemsToFound.get(currentChannel).put(rarity, new ArrayList<>());
                }
                itemsToFound.get(currentChannel).get(rarity).add(item);
                System.out.println("Item " + item + " added to " + currentChannel);
            }
        }
    }
}
