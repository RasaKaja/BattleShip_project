package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        new OptionPanel();
        Main game = new Main();
        GameHelper helper = new GameHelper();
        game.setUpGame();
        game.startPlaying();
    }

    private ArrayList<Ship> shipsList = new ArrayList<>();
    private GameHelper helper = new GameHelper();
    private int numOfGuesses = 0;
    //private int numOfShips = 9;
    private int countKill = 0;

    private void setUpGame() {
        // make some Ships and give them locations
        int[] shSize = {5, 4, 4, 3, 3, 3, 2, 2, 2}; //new


//        for (int i = 0; i < shSize.length; i++){
//            Ship i = new Ship();
//            //Ship no[i] = new Ship();
//            shipsList.add(i);
//        }

        Ship no0 = new Ship();
        Ship no1 = new Ship();
        Ship no2 = new Ship();
        Ship no3 = new Ship();
        Ship no4 = new Ship();
        Ship no5 = new Ship();
        Ship no6 = new Ship();
        Ship no7 = new Ship();
        Ship no8 = new Ship();

        // add Ship objects in the ArrayList
        shipsList.add(no0);
        shipsList.add(no1);
        shipsList.add(no2);
        shipsList.add(no3);
        shipsList.add(no4);
        shipsList.add(no5);
        shipsList.add(no6);
        shipsList.add(no7);
        shipsList.add(no8);

        //instruction for user
//        System.out.println("Your goal is to sink nine different size Ships.");
//        System.out.println("Try to sink them all in the fewest number of guesses.");
//        System.out.println("Your guess should be letter and number, like 'k2', 's5' etc.");

        int x = 0;
            for (Ship shipToSet : shipsList) { // repeat with each ship in the list
                ArrayList<String> newLocation = helper.placeShip(shSize[x]); // ask helper for a Ship location
                shipToSet.setLocationCells(newLocation); // call the setter method on this Ship to give it the location which just got from the helper
                //System.out.println(x + "th ship, size is " + shSize[x] + " cells, location is " + newLocation); //if need to see locations
                x++;
            }
    }

    // keep asking for user input and checking the guess
    private void startPlaying(){
        while (!shipsList.isEmpty()) { // while ship list is NOT empty
            String userGuess = helper.getUserInput("Enter a guess: ");
            checkUserGuess(userGuess);
        }
        finishGame();
    }

    private void checkUserGuess(String userGuess) {
        int numOfShips = 9;
        numOfGuesses++;
        String result = "miss"; // assume guess as a "miss", unless told otherwise

        for (Ship shipToCheck : shipsList) {
            result = shipToCheck.checkYourself(userGuess);
            if (result.equals("hit")){
                break;
            }
            if (result.equals("kill")){
                countKill++;
                numOfShips = numOfShips - countKill;
                System.out.println("Ships left: " + numOfShips);
                shipsList.remove(shipToCheck); //this ship has been sunk, need to delete of Ships list
                break;
            }
        }
        //helper.clearScreen();
        System.out.println(result);
        helper.changeOcean(userGuess, result);
    }

    private void finishGame(){
        System.out.println("All Ships are dead!");
        if (numOfGuesses <= 40) {
            System.out.println("it only took you " + numOfGuesses + " guesses.");
        } else {
            System.out.println("Took you long enough. " + numOfGuesses + " guesses.");
        }
    }
}
