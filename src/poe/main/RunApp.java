/**
 * Student Name: Nicholas Morris
 * Student Number: ST10502284
 * Assignment: PROG5121 PoE Part 1 - Registration and Login
 * Description: This class handles user input and method calling
 */
package poe.main;

import java.util.Scanner;
import poe.part1.logic.Login;
import poe.part2.logic.Messages;

public class RunApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //create a login object called auth
        Login auth = new Login();
        //create a messages object called inApp
        Messages inApp = new Messages();

        String finalMessage = "";

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
        System.out.println(regStatus);

        //Only carry onto User login if registration process was a success
        if (regStatus.contains("registered successfully")) {

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

            finalMessage = auth.returnLoginStatus(isSuccess);
            System.out.println("-- STATUS --");
            System.out.println(finalMessage);
        }

        boolean isLoggedIn = inApp.checkSendMessage(finalMessage);

        if (isLoggedIn) {
            System.out.println("");
            System.out.println("---Welcome to QuickChat---");
            
            inApp.displayOptions();
            System.out.print("\tSelect Option: ");
            System.out.println("-----------------------------------");
            int doInput = input.nextInt();
            
            while(doInput != 3){
                String messageOptions = inApp.sentMessage();
                System.out.println(messageOptions);
                System.out.print("\tSelect Message Action: ");
                int swInput = input.nextInt();
            switch(swInput){
                    
                case 1:
                    System.out.print("Enter the number of messages you want to send: ");
                    int numOfMessages = input.nextInt();
                    input.nextLine(); 

                    long msgID = inApp.generateMessageID();
                    boolean msgIDCheck = inApp.checkMessageID(msgID);
                    System.out.println("Generated Message ID: " + msgID);
                    System.out.println("Message ID Valid: " + msgIDCheck);
                    
                    System.out.println("Please enter your message: ");
                    String message = input.next();
                    input.nextLine();
                    
                    String msgHashString = inApp.createMessageHash(msgID, numOfMessages, message);
                    System.out.println("Message Hash: " + msgHashString);

                    String storeMessageData = inApp.storeMessageAsJSON(msgID, msgHashString, registerName, message);
                    String displayMessage = inApp.printJSONMessages();
                    System.out.println(displayMessage);

                    
                    
                case 2:


            }
      
            
        }
        
        
        input.close();
        
        

    }
}
