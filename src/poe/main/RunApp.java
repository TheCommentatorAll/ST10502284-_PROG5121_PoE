/**
 * Student Name: Nicholas Morris
 * Student Number: ST10502284
 * Assignment: PROG5121 PoE Part 1 - Registration and Login
 * Description: This class handles user input and method calling
 */
package poe.main;

import java.util.Scanner;
import poe.part1.logic.Login;

public class RunApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //create a login object called auth
        Login auth = new Login();
        //while loop condition
        boolean registrationStatus = false;

        do {//start of do-while
            //--- REGISTRATION PHASE ---
            System.out.println("-- REGISTER NEW ACCOUNT --");
            System.out.print("Enter your Name: ");
            String registerName = input.nextLine();

            System.out.print(">" + "\nEnter your surname: ");
            String registerSurname = input.nextLine();

            System.out.print(">" + "\nEnter your Username: ");
            String registerUsername = input.nextLine();

            System.out.print(">" + "\nEnter your Password: ");
            String registerPassword = input.nextLine();

            System.out.print(">" + "\nEnter your Cellphone Number: ");
            String registerNumber = input.nextLine();
            System.out.println("-----------------------------------");

            String regStatus = auth.registerUser(registerUsername, registerPassword, registerName, registerSurname, registerNumber);

            //Only carry onto User login if registration process was a success
            if (regStatus.contains("registered successfully")) {

                System.out.println(regStatus);
                System.out.println("-----------------------------------");
                System.out.println("-- LOGIN DETAILS --");
                System.out.println("Your username is: " + registerUsername + "\nYour password is: " + registerPassword);
                System.out.println("-----------------------------------");

                //--- USER LOGIN PHASE ---
                System.out.println("-- LOGIN TO ACCOUNT --");
                System.out.print("Please enter your Username: ");
                String username = input.nextLine();

                System.out.print(">" + "\nPlease enter your Password: ");
                String password = input.nextLine();
                System.out.println("-----------------------------------");

                boolean isSuccess = auth.loginUser(username, password);

                String finalMessage = auth.returnLoginStatus(isSuccess);
                System.out.println("-- STATUS --");
                System.out.println(finalMessage);

                registrationStatus = true;

            }else {
                System.out.println("\n[ERROR] \n" + regStatus);
                System.out.println("Please try registering again.");
                
            }

        } while (!registrationStatus);//end of do-while
        
        input.close();

    }

}
