package com.company;

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

        int lastPos = location-incr;
        int afterLast = location;
        int firsPos = lastPos - (shipSize*incr) + incr;
        int beforeFirst = firsPos - incr;
//        System.out.println("gh, before FIRS: " + beforeFirst);
//        System.out.println("GH, first poss: " + firsPos);
//        System.out.println("GH, lastPosition: " + lastPos);
//        System.out.println("GH, AFTER last: " + afterLast);

        if ((afterLast < oceanSize) & (beforeFirst > 0)) {
            ocean[afterLast] = 1;
            ocean[beforeFirst] = 1;
            //System.out.println("78 line: After last["+ afterLast + "] value is " + ocean[afterLast]);
            //System.out.println("79 line: Before First["+ beforeFirst + "] value is " + ocean[beforeFirst]);
        } else if ((beforeFirst > 0) && (afterLast > oceanSize)){
                ocean[beforeFirst] = 1;
                //System.out.println("82 line: Before First["+ beforeFirst + "] value is "+ ocean[beforeFirst]);
            } else {
            ocean[afterLast] = 1;
            //System.out.println("85 line: After last["+ afterLast + "] value is " + ocean[afterLast]);
        }

        int currFirst = firsPos;
        for (int i = 0; i< shipSize; i++){
            if (((currFirst + incrR) < oceanSize) && ((currFirst + incrR) % oceanLength !=0)){
//                System.out.println("First pos= " + firsPos);
//                System.out.println("firs+incR = " + (firsPos+incrR));
                ocean[currFirst+incrR] = 1;
                //System.out.println("Value [" + (currFirst+incrR) + "] = " + ocean[currFirst+incrR]);
            }
            currFirst = currFirst + incr;
        }

        for (int i = 0; i < shipSize; i++){
            if (((firsPos - incrR) > 0) && ((firsPos - incrR) < oceanSize)) {
                ocean[firsPos-incrR] = 1;
                //System.out.println("Value [" + (firsPos-incrR) + "] = " + ocean[firsPos-incrR]);
            }
            firsPos = firsPos+incr;
        }


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
        System.out.print(shot + ""); //println
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            input = is.readLine();
            if (input.length() == 0 ) return null;
        } catch (IOException e) {
            //System.out.println("IOExeption: " + e);
            System.out.println("Please put valid guess.");
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

//    public void updateOceanMap(String userGuess, String result){
//        //convert use guess to coord
//        userGuess = userGuess;
//        String col = String.valueOf(userGuess.charAt(0)); //getting column
//        String colNo = String.valueOf(oceanName.indexOf(col)); //convert column letter to number
//
//        String row = String.valueOf(userGuess.charAt(1)); //getting row
//
//        String coord = row.concat(colNo); //user Input
//        //System.out.println("GH, 120 line, coord= " + coord);
//
//        result = result;
//        //System.out.println("GH 123 line result = " + result);
//
//        // if userGuess == 0 -> hit "X"
//        // if userGuess < 0 -> miss "o"
//        char water = '~';
//        char hit = 'X';
//        char miss = '.';
//
//        //first section of the oceanMap (letters)
//        System.out.print(" ");
//        for (int i = 0; i < oceanLength; i++){
//            System.out.print(" " + oceanName.charAt(i));
//        }
//        System.out.println();
//
//        // middle section of the oceanMap
//        int n = 0; //nth value of the ocean
//        for (int i = 0; i < oceanLength; i++){
//            System.out.print(i + "|");
//            for (int j = 0; j < oceanLength; j++) {
//                if (n == Integer.parseInt(coord)) { //if n = user guess
//                    if (result == String.valueOf(hit)) {
//                        //System.out.print(hit + "|");
//                        //updatedOcean[n] = hit;
//                    } else {
//                        //System.out.print(miss + "|");
//                        //updatedOcean[n] = miss;
//                    }
//                } else {
//                    //System.out.print(water + "|");
//                    //System.out.print(updatedOcean[n] + "|");
//                    //updatedOcean[n] = water;
//                }
//                n++;
//            }
//            //System.out.println();
//        }
//    }

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

}

