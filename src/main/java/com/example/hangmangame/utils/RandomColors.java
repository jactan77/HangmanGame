package com.example.hangmangame.utils;

import java.util.Random;

public class RandomColors {
    public static String setRandomColor(){

        Random random = new Random();
        int red = random.nextInt(256);   // 0-255
        int green = random.nextInt(256); // 0-255
        int blue = random.nextInt(256);  // 0-255
        return String.format("#%02X%02X%02X", red, green, blue);
    }
}
