package com.meepoffaith.hextra.util;

public class HextraUtils{
    /** Simulates the accumulation process of Numerical Reflection */
    public static double numericalReflection(String s){
        double accumulator = 0.0;
        for(char ch : s.toCharArray()){
            switch(ch){
                case 'w' -> accumulator++;
                case 'q' -> accumulator += 5;
                case 'e' -> accumulator += 10;
                case 'a' -> accumulator *= 2;
                case 'd' -> accumulator /= 2;
                case 's' -> {}
                default -> throw new IllegalStateException();
            }
        }
        return accumulator;
    }
}
