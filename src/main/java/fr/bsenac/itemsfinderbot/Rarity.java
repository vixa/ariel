/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.bsenac.itemsfinderbot;

import java.util.Random;

/**
 *
 * @author vixa
 */
public enum Rarity {

    EXTREMLY_RARE(1),
    VERY_RARE(10 + EXTREMLY_RARE.rarity),
    RARE(50 + VERY_RARE.rarity),
    COMMON(170 + RARE.rarity),
    DEFAULT(0);

    /**
     * The maximum of the interval [0, max], define the rarity
     */
    private static final int max = 2500;
    /**
     * The rarity of the objects
     */
    private final int rarity;

    private Rarity(int i) {
        rarity = i;
    }

    public static Rarity getRarity(String r) {
        return valueOf(r.toUpperCase());
    }

    /**
     * Get a rarity based on a random number.
     *
     * @return a rarity. Default is a special value, no rarity associated.
     */
    public static Rarity getRandomRarity() {
        int value = new Random().nextInt(max);
        System.out.println("Roll the dices! " + value + " / " + max);
        if (value <= EXTREMLY_RARE.rarity) {
            return EXTREMLY_RARE;
        } else if (value <= VERY_RARE.rarity) {
            return VERY_RARE;
        } else if (value <= RARE.rarity) {
            return RARE;
        } else if (value <= COMMON.rarity) {
            return COMMON;
        } else {
            return DEFAULT;
        }
    }
}
