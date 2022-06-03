package com.company;

import javax.swing.*;

public class OptionPanel {
    JFrame f;

    OptionPanel(){
        f = new JFrame();
        JOptionPane.showMessageDialog(f, """
                        Hello!!! Welcome to BattleShip game!!!
                        
                        Your goal is to sink nine different size Ships.
                        Try to sink them all in the fewest number of guesses.
                        
                        I M P O R T A N T: Your guess should be letter and number, like 'k2', 's5' etc.
                        
                        Are You ready to play? 
                        """);
    }
}
