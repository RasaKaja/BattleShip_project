package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

public class GameHelper {
    private static final String oceanName = "respublika";
    private int oceanLength = 10;
    private int oceanSize = oceanLength*oceanLength;
    private int[] ocean = new int[oceanSize];
    private int shipCount = 0;

    public ArrayList<String> placeShip(int shipSize){
        ArrayList<String> nameCells = new ArrayList<>(); // holds 's3' type coordinates

        String temp = null; // temporary String for concatenate
        int[] coords = new int[shipSize];
        int attempts = 0;
        boolean success = false; // flag = found a good location
        int location = 0; // current starting location

        shipCount++; //nth Ship to place
        int incr = 1; //set horizontal increment
        if ((shipCount % 2) == 1) { // if odd dot com (place vertically)
            incr = oceanLength; // set vertical increment
        }

        while (!success & attempts++ < 200){
            location = (int) (Math.random()*oceanSize); // get random starting point

            int n = 0; //nth position is in ship to place
            success = true; // assume success

            while (success && n < shipSize) { // look for next unused spots
                if (ocean[location] == 0) { // location not used
                    //System.out.println("GH, 40 line, location first position: " + location);
                    coords[n++] = location; // save the location
                    location += incr; //try next spot

                    if (location >= oceanSize) { //out of bounds 'bottom'
                        success = false;
                    }

                    if (n > 0 && (location % oceanLength == 0)) { //out of bounds "right edge"
                        success = false;
                    }
                } else { // found already used location
                    success = false;
                }
            }

        }

        //System.out.println("GameHelper, lastPosition: " + (location-incr));
        int n = 0; //turn location into alpha coords
        int row = 0;
        int column = 0;

        while (n < shipSize){
            ocean[coords[n]] = 1; // mark in ocean place as 'used'
            row = (int) (coords[n] / oceanLength); // get row value
            column = (coords[n]) % oceanLength; // get column value
            temp = String.valueOf(oceanName.charAt(column)); //convert to letter

            nameCells.add(temp.concat(Integer.toString(row))); // concatenate column name + row
            n++;
        }
        return nameCells;
    }

    public String getUserInput(String shot){
        String input = null;
        System.out.println(shot + "");
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            input = is.readLine();
            if (input.length() == 0 ) return null;
        } catch (IOException e) {
            System.out.println("IOExeption: " + e);
        }
        return input.toLowerCase();
    }

    public void oceanMap(){
        char water = '~';

        //first section of the oceanMap (letters)
        System.out.print(" ");
        for (int i = 0; i < oceanLength; i++){
            System.out.print(" " + oceanName.charAt(i));
        }
        System.out.println();

        // middle section of the oceanMap
        int n = 0;
        for (int i = 0; i < oceanLength; i++){
            System.out.print(i + "|");
            for (int j = 0; j < oceanLength; j++){
                if (ocean[n] == 0){
                    System.out.print(water + "|");
                }
            }
            n++;
            System.out.println();
        }
    }

    public void updateOceanMap(){
        char water = '~';
        char hit = 'X';
        char miss = '0';

        //first section of the oceanMap (letters)
        System.out.print(" ");
        for (int i = 0; i < oceanLength; i++){
            System.out.print(" " + oceanName.charAt(i));
        }
        System.out.println();

        // middle section of the oceanMap
        for (int i = 0; i < oceanLength; i++){
            System.out.print(i + "|");
            for (int j = 0; j < oceanLength; j++){

                if (ocean[j] == 0) {
                    System.out.print(water + "|");
                }
                else if (ocean[j] > 0){
                    System.out.print(hit + "|");
                } else {
                    System.out.print(ocean[j]);
                }
            }
            System.out.println();
        }
    }
}
