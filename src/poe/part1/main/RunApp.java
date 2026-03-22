/**
 * Student Name: Nicholas Morris
 * Student Number: ST10502284
 * Assignment: PROG5121 PoE Part 1 - Registration and Login
 * Description: This class handles user input and method calling
 */
package poe.part1.main;

import javax.swing.JOptionPane;
import poe.part1.logic.Login;

public class RunApp {
    public static void main(String[] args) {
        
        Login lgn = new Login();
        
        userName = JOptionPane.showInputDialog("Please enter your username>> ");    
        password = JOptionPane.showInputDialog("Please enter your password>> ");    
        password = JOptionPane.showInputDialog("Please enter your password>> ");    
        password = JOptionPane.showInputDialog("Please enter your password>> ");    
        cellphone = JOptionPane.showInputDialog("Please enter your cellphone>> ");    
        
        Login worker = new Login();
        
        String result = worker.registerUsername(userName, password);
        
        System.out.println(result);
               
    }
}
