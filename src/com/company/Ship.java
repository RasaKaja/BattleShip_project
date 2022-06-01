package com.company;

import java.util.ArrayList;

public class Ship {
    private ArrayList<String> locationCells;

    public void setLocationCells(ArrayList<String> loc){
        locationCells = loc;
    }

    public String checkYourself (String input){
        String result = "miss"; //
        int index = locationCells.indexOf(input); // check if the guess(input) is in the ArrayList, by asking for its index. If it's not in the list, then indexOf() return a -1
        //System.out.println("Ship, 15l LocationCells: " + locationCells);
        //System.out.println("Ship, 16l index = " + index);

        if (index >= 0) { //if index is greater than or equal to zero, the user guess is definitely in the list, so remove it.
            locationCells.remove(index);
            //System.out.println("Ship, 20l LocationCells: " + locationCells);

            if (locationCells.isEmpty()){ // checking if all the locations have been guessed
                result = "kill";
                System.out.println("You sunk the Ship!");
            } else {
                result = "hit";
            }
        }
        return result;
    }
}
