package application;

import java.util.Random;

public class Blik {
    private int blikNumber;

    public Blik(){
        this.blikNumber = getRandomNumberInRange(100000, 999999);
    }

    // method picking random number
    private static int getRandomNumberInRange(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    // getter
    public int getBlikNumber(){
        return this.blikNumber;
    }
}
