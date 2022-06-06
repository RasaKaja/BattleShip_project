package com.company;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class GameHelper {
    private static final String oceanName = "respublika";
    private int oceanLength = 10;
    private int oceanSize = oceanLength*oceanLength;
    private int[] ocean = new int[oceanSize];
    private int shipCount = 0;
    private int[] updatedOcean = new int[oceanSize];

    public ArrayList<String> placeShip(int shipSize){
        ArrayList<String> nameCells = new ArrayList<>(); // holds 's3' type coordinates

        String temp = null; // temporary String for concatenate
        int[] coords = new int[shipSize];
        int attempts = 0;
        boolean success = false; // flag = found a good location
        int location = 0; // current starting location

        shipCount++; //nth Ship to place
        int incr = 1; //set horizontal increment
        if ((shipCount % 2) == 1) { // if odd ship (place vertically)
            incr = oceanLength; // set vertical increment
        }

        int incrR = oceanLength;
        if (incr == oceanLength){
            incrR = 1;
        }

        while (!success & attempts++ < 200){
            location = (int) (Math.random()*oceanSize); // get random starting point

            int n = 0; //nth position is in ship to place
            success = true; // assume success

            while (success && n < shipSize) { // look for next unused spots
                if (ocean[location] == 0) { // location not used
                    //System.out.println("GH, 42 line, location first position: " + location);
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

        // booking "safe" locations around the ship
        int lastPos = location-incr;
        int afterLast = location;
        int firsPos = lastPos - (shipSize*incr) + incr;
        int beforeFirst = firsPos - incr;

        // in front and at the end of ship
        if ((afterLast < oceanSize) & (beforeFirst > 0)) {
            ocean[afterLast] = 1;
            ocean[beforeFirst] = 1;
        } else if ((beforeFirst > 0) && (afterLast > oceanSize)){
                ocean[beforeFirst] = 1;
            } else {
            ocean[afterLast] = 1;
        }

        // from one side and another side of the ship
        int currFirst = firsPos;
        for (int i = 0; i< shipSize; i++){
            if (((currFirst + incrR) < oceanSize) && ((currFirst + incrR) > 0) && ((currFirst + incrR) % oceanLength !=0)){
                ocean[currFirst+incrR] = 1;
            }
            currFirst = currFirst + incr;
        }

        for (int i = 0; i < shipSize; i++){
            if (((firsPos - incrR) > 0) && ((firsPos - incrR) < oceanSize)) {
                ocean[firsPos-incrR] = 1;
            }
            firsPos = firsPos+incr;
        }

        //turn location into alpha coords
        int n = 0;
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
        System.out.print(shot + ""); //println
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            input = is.readLine();
            if (input.length() == 0 ) {
                return null;
            }
        } catch (IOException e) {
            //System.out.println("IOExeption: " + e);
            //System.out.println("Please put valid guess.");
        }
        return input.toLowerCase();
    }

    public void changeOcean(String userGuess, String result){
        result = result;

        // getting and converting coordinates to proper format
        String col = String.valueOf(userGuess.charAt(0)); //getting column
        String colNo = String.valueOf(oceanName.indexOf(col)); //convert column letter to number "r" -> 0, "e" -> 1, etc.
        String row = String.valueOf(userGuess.charAt(1)); //getting row
        String coord = row.concat(colNo); //concatenate
        int n = Integer.parseInt(coord); //position in Ocean

        // updating n value in map, depending on 'result'
        if (result == "hit"){
            updatedOcean[n] = Integer.parseInt("1");
        } else if (result == "kill") {
            updatedOcean[n] = Integer.parseInt("1");

        } else {
            updatedOcean[n] = Integer.parseInt("9");
        }

        // printing updated OceanMap
        //first line
        System.out.print(" ");
        for (int i = 0; i < oceanLength; i++){
            System.out.print(" " + oceanName.charAt(i));
        }
        System.out.println();

        // middle section of the oceanMap
        int x = 0;
        for (int i = 0; i < oceanLength; i++){
            System.out.print(i + "|");
            for (int j = 0; j < oceanLength; j++) {
                if (updatedOcean[x] == 1) {
                    System.out.print("X" + "|");
                } else if (updatedOcean[x] == 9) {
                    System.out.print("~" + "|");
                } else {
                    System.out.print(" " + "|");
                }
                x++;
            }
            System.out.println();
        }

        // last line
        System.out.print(" ");
        for (int i = 0; i < oceanLength; i++){
            System.out.print(" " + oceanName.charAt(i));
        }
        System.out.println();
   }

    public void clearScreenAfter() {
        for (int i = 0; i < 7; ++i) System.out.println();
    }

}

